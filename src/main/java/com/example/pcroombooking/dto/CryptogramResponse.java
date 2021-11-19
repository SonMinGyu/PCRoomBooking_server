package com.example.pcroombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CryptogramResponse {

    private boolean verified;
    private int httpStatus;
    private int responseCode;
    private String result;
    private String responseMessage;

}
