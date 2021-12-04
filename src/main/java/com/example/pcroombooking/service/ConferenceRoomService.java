package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.ConferenceRoom;
import com.example.pcroombooking.domain.ConferenceRoomReservation;
import com.example.pcroombooking.dto.ConferenceRoomRequest;
import com.example.pcroombooking.dto.ConferenceRoomResponse;
import com.example.pcroombooking.dto.successType.SuccessType;
import com.example.pcroombooking.exception.SuperException;
import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;
import com.example.pcroombooking.repository.ConferenceRoomRepository;
import com.example.pcroombooking.repository.ConferenceRoomReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConferenceRoomService {

    private final ConferenceRoomRepository conferenceRoomRepository;
    private final ConferenceRoomReservationRepository conferenceRoomReservationRepository;

    public ConferenceRoomResponse getConferenceRoomList() {

        List<ConferenceRoom> conferenceRooms = conferenceRoomRepository.findAll();
        if(conferenceRooms.size() == 0) {
            throw new SuperException(CustomExceptionType.CONFERENCEROOM_NOT_FOUND_EXCEPTION);
        }

        for(int i = 0; i < conferenceRooms.size(); i++) {
            // 당일날에 예약이 완료되었는지 확인, 팀플실이름과 날짜만 보내기
            String conferenceRoomName = conferenceRooms.get(i).getName();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = format.format(Calendar.getInstance().getTime());

            List<ConferenceRoomReservation> conferenceRoomReservations = conferenceRoomReservationRepository
                    .findByConferenceRoomNameAndDate(conferenceRoomName, dateStr);

            if(conferenceRoomReservations.size() >= 16) {
                conferenceRooms.get(i).setAllBooked(true);
            } else {
                conferenceRooms.get(i).setAllBooked(false);
            }
        }

        return ConferenceRoomResponse.builder()
                .conferenceRooms(conferenceRooms)
                .httpStatus(SuccessType.CONFERENCEROOM_SEARCH_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.CONFERENCEROOM_SEARCH_SUCCESS.getResponseCode())
                .result(SuccessType.CONFERENCEROOM_SEARCH_SUCCESS.getResult())
                .responseMessage(SuccessType.CONFERENCEROOM_SEARCH_SUCCESS.getResponseMessage())
                .build();
    }

    public ConferenceRoom addConferenceRoom(ConferenceRoomRequest conferenceRoomRequest) {
        return conferenceRoomRepository.save(
                ConferenceRoom.builder()
                .name(conferenceRoomRequest.getName())
                .buildingNumber(conferenceRoomRequest.getBuildingNumber())
                .locationName(conferenceRoomRequest.getLocationName())
                .layer(conferenceRoomRequest.getLayer())
                .peopleLimit(conferenceRoomRequest.getLimit())
                .allBooked(conferenceRoomRequest.isAllBooked())
                .enable(conferenceRoomRequest.isEnabled())
                .build()
        );
    }

}
