package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.ConferenceRoom;
import com.example.pcroombooking.domain.ConferenceRoomReservation;
import com.example.pcroombooking.dto.*;
import com.example.pcroombooking.dto.successType.SuccessType;
import com.example.pcroombooking.exception.SuperException;
import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;
import com.example.pcroombooking.repository.ConferenceRoomRepository;
import com.example.pcroombooking.repository.ConferenceRoomReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConferenceRoomReservationService {

    private final ConferenceRoomRepository conferenceRoomRepository;
    private final ConferenceRoomReservationRepository conferenceRoomReservationRepository;

    public ConferenceRoomReservationGetResponse getConferenceRoomReservations(ConferenceRoomReservationGetRequest conferenceRoomReservationGetRequest) {

        String conferenceRoomName = conferenceRoomReservationGetRequest.getConferenceRoomName();
        String date = conferenceRoomReservationGetRequest.getDate();

        List<ConferenceRoomReservation> conferenceRoomReservations = conferenceRoomReservationRepository
                .findByConferenceRoomNameAndDate(conferenceRoomName, date);

        if(conferenceRoomReservations.size() == 0) {
            throw new SuperException(CustomExceptionType.CONFERENCEROOM_RESERVATION_NOT_FOUND_EXCEPTION);
        }

        return ConferenceRoomReservationGetResponse.builder()
                .conferenceRoomReservations(conferenceRoomReservations)
                .httpStatus(SuccessType.CONFERENCEROOM_RESERVATION_SEARCH_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.CONFERENCEROOM_RESERVATION_SEARCH_SUCCESS.getResponseCode())
                .result(SuccessType.CONFERENCEROOM_RESERVATION_SEARCH_SUCCESS.getResult())
                .responseMessage(SuccessType.CONFERENCEROOM_RESERVATION_SEARCH_SUCCESS.getResponseMessage())
                .build();
    }

    public ConferenceRoomReservationAddResponse addConferenceRoomReservation(ConferenceRoomReservationAddRequestList conferenceRoomReservationAddRequestList) {

        List<ConferenceRoomReservationAddRequest> conferenceRoomReservationAddRequests = conferenceRoomReservationAddRequestList.getConferenceRoomReservationAddRequests();
        List<ConferenceRoomReservation> currentSavedReservation = new ArrayList<>();

        for(int i = 0; i < conferenceRoomReservationAddRequests.size(); i++) {
            ConferenceRoom conferenceRoom = conferenceRoomRepository
                    .findByName(conferenceRoomReservationAddRequests.get(i).getConferenceRoomName())
                    .orElseThrow(() -> new SuperException(CustomExceptionType.CONFERENCEROOM_NOT_EXIST_EXCEPTION));

            ConferenceRoomReservation newConferenceRoomReservation = conferenceRoomReservationAddRequests.get(i).toConferenceRoomReservation(conferenceRoom);
            List<ConferenceRoomReservation> conferenceRoomReservations = conferenceRoom.getConferenceRoomReservations();
            conferenceRoomReservations.add(newConferenceRoomReservation);
            conferenceRoom.setConferenceRoomReservations(conferenceRoomReservations);

            conferenceRoomRepository.save(conferenceRoom);

            currentSavedReservation.add(conferenceRoomReservationRepository
                    .findByConferenceRoomNameAndDateAndStartTimeAndReservationEmail(conferenceRoomReservationAddRequests.get(i).getConferenceRoomName()
                            , conferenceRoomReservationAddRequests.get(i).getDate()
                            , conferenceRoomReservationAddRequests.get(i).getStartTime(),
                            conferenceRoomReservationAddRequests.get(i).getReservationEmail())
                    .orElseThrow(() -> new SuperException(CustomExceptionType.CONFERENCEROOM_RESERVATION_SAVE_FAIL_EXCEPTION)));
        }
        
        return ConferenceRoomReservationAddResponse.builder()
                .conferenceRoomReservations(currentSavedReservation)
                .httpStatus(SuccessType.CONFERENCEROOM_RESERVE_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.CONFERENCEROOM_RESERVE_SUCCESS.getResponseCode())
                .result(SuccessType.CONFERENCEROOM_RESERVE_SUCCESS.getResult())
                .responseMessage(SuccessType.CONFERENCEROOM_RESERVE_SUCCESS.getResponseMessage())
                .build();
    }

}
