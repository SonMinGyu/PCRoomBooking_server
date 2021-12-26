package com.example.pcroombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatBookOrUseRequest {
    private String pcroomName;
    private String seatName;
    private String userEmail;
    private String viewTag;
    private Boolean booked;
    private Boolean inuse;
    private String seatsStr;
}
