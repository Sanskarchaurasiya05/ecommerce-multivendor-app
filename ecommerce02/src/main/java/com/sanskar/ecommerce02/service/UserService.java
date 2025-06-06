package com.sanskar.ecommerce02.service;

import com.sanskar.ecommerce02.model.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
}
