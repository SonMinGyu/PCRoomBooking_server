package com.example.pcroombooking.dto;

import com.example.pcroombooking.domain.PCRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PCRoomGetResponse {
    private PCRoom pcRoom;
    private int httpStatus;
    private int responseCode;
    private String result;
    private String responseMessage;
}
