package com.sanskar.ecommerce02.service.impl;

import com.sanskar.ecommerce02.config.JwtProvider;
import com.sanskar.ecommerce02.model.User;
import com.sanskar.ecommerce02.repository.UserRepository;
import com.sanskar.ecommerce02.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
          String  email = jwtProvider.getEmailFromJwtToken(jwt);
          User user = this.findUserByEmail(email);
          if(user==null)
              throw new Exception("user not find with email-"+email);

          return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);
        if(user==null)
            throw new Exception("user not find with email-"+email);

        return user;
    }
}
