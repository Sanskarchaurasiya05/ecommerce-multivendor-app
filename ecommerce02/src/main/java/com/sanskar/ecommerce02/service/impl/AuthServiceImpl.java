package com.sanskar.ecommerce02.service.impl;

import com.sanskar.ecommerce02.config.JwtProvider;
import com.sanskar.ecommerce02.domain.USER_ROLE;
import com.sanskar.ecommerce02.model.Cart;
import com.sanskar.ecommerce02.model.Seller;
import com.sanskar.ecommerce02.model.User;
import com.sanskar.ecommerce02.model.VerificationCode;
import com.sanskar.ecommerce02.repository.CartRepository;
import com.sanskar.ecommerce02.repository.SellerRepository;
import com.sanskar.ecommerce02.repository.UserRepository;
import com.sanskar.ecommerce02.repository.VarificationCodeRepository;
import com.sanskar.ecommerce02.request.LoginRequest;
import com.sanskar.ecommerce02.response.AuthResponse;
import com.sanskar.ecommerce02.response.SignupRequest;
import com.sanskar.ecommerce02.service.AuthService;
import com.sanskar.ecommerce02.service.EmailService;
import com.sanskar.ecommerce02.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CartRepository cartRepository;


    private final PasswordEncoder passwordEncoder;

     private final JwtProvider jwtProvider;

     @Autowired
    private final VarificationCodeRepository varificationCodeRepository;

     @Autowired
    private final EmailService emailService;

     private final SellerRepository sellerRepository;

     @Autowired
     private final CustomUserServiceImpl customUserService;

    @Override
    public void sentLoginOtp(String email , USER_ROLE role) throws Exception {



        String SIGNING_PREFIX="signing_";



        if(email.startsWith(SIGNING_PREFIX)){
            email=email.substring(SIGNING_PREFIX.length());
            if(role.equals(USER_ROLE.ROLE_SELLER)){
                Seller seller = sellerRepository.findByEmail(email);
                if(seller==null)
                    throw new Exception("sellere not found");
            }
            else{
                User user = userRepository.findByEmail(email);
                if(user==null){
                    throw new Exception("user not exist with provided email");
                }
            }
        }

        VerificationCode isExist = varificationCodeRepository.findByEmail(email);
        if(isExist!=null){
            varificationCodeRepository.delete(isExist);
        }
        //logic for generating new otp
               String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        varificationCodeRepository.save(verificationCode);

//        storing the otp in our database now we sent it to the user

        String subject="Hatta login/signup otp";
        String text="you login.signup otp- "+ otp;

        emailService.sendVerificationOtpEmail(email , otp , subject , text);

    }

    @Override
    public String createUser(SignupRequest req) throws Exception {

//       find the verification code
        VerificationCode verificationCode= varificationCodeRepository.findByEmail(req.getEmail());

        if(verificationCode==null || !verificationCode.getOtp().equals(req.getOtp()))
            throw new Exception("Wrong Otp...");


//   when sent otp enter in your website then u save user in database
//         for this we implement service for sending email ---> for this we add dependency mail-starter im pom.xml
        User user = userRepository.findByEmail(req.getEmail());

        if(user==null){
            User createdUser = new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobile("83182977434");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createdUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

//        now we generate JWT Token

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(),null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse sigin(LoginRequest req) throws Exception {
     String username=req.getEmail();
     String otp=req.getOtp();
        System.out.println(username + " ----- " + otp);
//     it is authenticate method inside that we verify the otp and return the authentication
        System.out.println("run-3");
     Authentication authentication=authenticate(username,otp);
        System.out.println("run-4");
//     using these authentication we generate a token
     SecurityContextHolder.getContext().setAuthentication(authentication);

     String token = jwtProvider.generateToken(authentication);

     AuthResponse authResponse = new AuthResponse();
     authResponse.setJwt(token);
     authResponse.setMessage("Login success");
// for setting the role first we acess the role
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        now we setting the role

        String roleName=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));
System.out.println("success check pt-1");
        return authResponse;
    }

    private Authentication authenticate(String username, String otp) throws Exception {
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        String SELLER_PREFIX="seller_";
        if(username.startsWith(SELLER_PREFIX)) {
            username = username.substring(SELLER_PREFIX.length());
        }

        System.out.println("sign in userDetails - " + userDetails);
        System.out.println("run2");

        if(userDetails==null)
            throw new BadCredentialsException("invalid username ");

        VerificationCode verificationCode = varificationCodeRepository.findByEmail(username);

        if(verificationCode==null || !verificationCode.getOtp().equals(otp)){
            throw new Exception("wring otp");
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
    }
}
