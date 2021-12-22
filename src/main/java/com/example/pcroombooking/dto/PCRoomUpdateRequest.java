package com.example.pcroombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PCRoomUpdateRequest {
    private String pcroomName;
    private String seatsStr;
    private String seatName;
    private String userEmail;
    private String viewTag;
    private Boolean booked;
    private Boolean inuse;
}
