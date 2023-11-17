package com.example.bookshop.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommonConstants {
    public static String JWT_KEY;
    public static String JWT_HEADER;
    public static Long JWT_EXP;

    @Value("${constant.security.JWT_KEY}")
    public void setJwtKey(String key) {
        CommonConstants.JWT_KEY = key;
    }
    @Value("${constant.security.JWT_HEADER}")
    public void setJwtHeader(String header) {
        CommonConstants.JWT_HEADER = header;
    }
    @Value("${constant.security.JWT_EXP}")
    public void setJwtHeader(Long expiration) {
        CommonConstants.JWT_EXP = expiration;
    }
}
