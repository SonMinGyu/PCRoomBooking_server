package com.example.pcroombooking.dto;

import com.example.pcroombooking.domain.ConferenceRoom;
import com.example.pcroombooking.domain.ConferenceRoomReservation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConferenceRoomReservationAddRequest {
    private String conferenceRoomName;
    private Integer buildingNumber;
    private String locationName;
    private String date;
    private Integer startTime;
    private Integer endTime;
    private String reservationEmail;

    public ConferenceRoomReservation toConferenceRoomReservation(ConferenceRoom conferenceRoom) {
        return ConferenceRoomReservation.builder()
                .conferenceRoom(conferenceRoom)
                .conferenceRoomName(conferenceRoomName)
                .buildingNumber(buildingNumber)
                .locationName(locationName)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .reservationEmail(reservationEmail)
                .enabled(true)
                .reserved(true)
                .build();
    }
}
