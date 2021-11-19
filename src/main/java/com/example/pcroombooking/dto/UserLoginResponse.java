package com.example.pcroombooking.dto;

import com.example.pcroombooking.config.JwtTokenProvider;
import com.example.pcroombooking.domain.Authority.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    private String jwtToken;
    private int httpStatus;
    private int responseCode;
    private String result;
    private String responseMessage;
    private String email;
    private Set<Authority> authorities;
}
