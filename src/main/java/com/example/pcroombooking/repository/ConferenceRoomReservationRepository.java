package com.example.pcroombooking.repository;

import com.example.pcroombooking.domain.ConferenceRoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConferenceRoomReservationRepository extends JpaRepository<ConferenceRoomReservation, Long> {

    Optional<ConferenceRoomReservation> findByConferenceRoomNameAndDateAndStartTimeAndReservationEmail(String conferenceRoomName
            , String date, Integer startTime, String reservationEmail);

    List<ConferenceRoomReservation> findByConferenceRoomNameAndDate(String conferenceRoomName, String date);

}
