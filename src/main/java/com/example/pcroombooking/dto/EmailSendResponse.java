package com.example.pcroombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailSendResponse {
    private int httpStatus;
    private int responseCode;
    private String result;
    private String responseMessage;
}
