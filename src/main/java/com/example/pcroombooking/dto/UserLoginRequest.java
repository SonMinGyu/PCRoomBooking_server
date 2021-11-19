package com.example.pcroombooking.dto;

import com.example.pcroombooking.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {

    private String email;

    private String password;

    public User toEntity() {
        return User.builder()
                .email(getEmail())
                .password(getPassword())
                .build();
    }
}
