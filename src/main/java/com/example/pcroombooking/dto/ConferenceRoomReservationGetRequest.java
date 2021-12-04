package com.example.pcroombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConferenceRoomReservationGetRequest {
    private String conferenceRoomName;
    private String date;
}
