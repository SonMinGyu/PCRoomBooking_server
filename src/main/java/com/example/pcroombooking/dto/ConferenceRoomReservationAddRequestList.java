package com.example.pcroombooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConferenceRoomReservationAddRequestList {
    private List<ConferenceRoomReservationAddRequest> conferenceRoomReservationAddRequests;
}
