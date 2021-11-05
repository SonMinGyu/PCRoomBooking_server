package com.example.pcroombooking.config;

import com.example.pcroombooking.dto.UserLoginRequest;
import com.example.pcroombooking.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenProvider {

    private String secretKey;
    private long tokenValidMilisecond = 1000L * 60 * 60; // 1000 ms = 1초

    @PostConstruct
    void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private Map<String, Object> createClaims(UserLoginRequest userLoginRequest) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", userLoginRequest.getEmail())
    }

    public String createJwtToken(String )

}
