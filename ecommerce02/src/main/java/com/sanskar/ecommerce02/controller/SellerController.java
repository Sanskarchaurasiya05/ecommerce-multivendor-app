package com.sanskar.ecommerce02.controller;

import com.sanskar.ecommerce02.config.JwtProvider;
import com.sanskar.ecommerce02.domain.AccountStatus;
import com.sanskar.ecommerce02.exception.SellerException;
import com.sanskar.ecommerce02.model.Seller;
import com.sanskar.ecommerce02.model.SellerReport;
import com.sanskar.ecommerce02.model.VerificationCode;
import com.sanskar.ecommerce02.repository.VarificationCodeRepository;
import com.sanskar.ecommerce02.request.LoginRequest;
import com.sanskar.ecommerce02.response.ApiResponse;
import com.sanskar.ecommerce02.response.AuthResponse;
import com.sanskar.ecommerce02.service.AuthService;
import com.sanskar.ecommerce02.service.EmailService;
import com.sanskar.ecommerce02.service.SellerReportService;
import com.sanskar.ecommerce02.service.SellerService;
import com.sanskar.ecommerce02.util.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
     private final AuthService authService;
    private final VarificationCodeRepository varificationCodeRepository;
private final EmailService emailService;
     private final JwtProvider jwtProvider;
     private final SellerReportService sellerReportService;

//-------------------------------------------------------------------------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequest req) throws Exception {
        String otp = req.getOtp();
        String email = req.getEmail();

        req.setEmail("seller_"+email);
        AuthResponse authResponse = authService.sigin(req);

        return ResponseEntity.ok(authResponse);
    }

//-----------------------------------------------------------------------------------------------------------------

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(@PathVariable String otp) throws Exception {
        VerificationCode verificationCode = varificationCodeRepository.findByOtp(otp);

        if(verificationCode==null || !verificationCode.getOtp().equals(otp)){
    throw new Exception("wrong otp....");
        }

        Seller seller=sellerService.verifyEmail(verificationCode.getEmail(),otp);

        return new ResponseEntity<>(seller, HttpStatus.OK);
    }


// --------------------------------------------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception {
//first we create seller by calling the method createseller in sellerService
        Seller savedSeller = sellerService.createSeller(seller);

//       here we create a verification code
        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        varificationCodeRepository.save(verificationCode);


        String subject = "Hatta shop Email Verification Code";
        String text = "Welcome to Hatta shop, Verify your account using this link";
        String fronted_url = "http://localhost:3000/verify-seller/";

//        here we sent a verfication code on given email
        emailService.sendVerificationOtpEmail(seller.getEmail(),verificationCode.getOtp(),subject,text+ fronted_url);

        return new ResponseEntity<>(savedSeller , HttpStatus.CREATED);
    }

//-------------------------------------------------------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

//    --------------------------------------------------------------------------------------------------------------

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt) throws Exception{

        Seller seller  = sellerService.getSellerProfile(jwt);

        return new ResponseEntity<>(seller,HttpStatus.OK);
    }


    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwt);
        SellerReport report = sellerReportService.getSellerReport(seller);
        return new ResponseEntity<>(report,HttpStatus.OK);
    }

//    -----------------------------------------------------------------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<Seller>>getAllSellers(@RequestParam(required = false)AccountStatus status){
        List<Seller> sellers = sellerService.getAllSellers(status);
        return ResponseEntity.ok(sellers);
    }

//    --------------------------------------------------------------------------------------------------------------

    @PatchMapping()
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt , @RequestBody Seller seller) throws Exception {
        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(),seller);
        return ResponseEntity.ok(updatedSeller);
    }

//    ---------------------------------------------------------------------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws  Exception {

        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();

    }

}
