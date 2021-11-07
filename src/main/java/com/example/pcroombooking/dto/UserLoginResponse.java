package com.example.pcroombooking.dto;

import com.example.pcroombooking.config.JwtTokenProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {
    private String jwtToken;
    private int resultCode;
    private String result;
    private String message;
}
