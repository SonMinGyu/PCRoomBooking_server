package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.domain.Seat;
import com.example.pcroombooking.dto.*;
import com.example.pcroombooking.dto.successType.SuccessType;
import com.example.pcroombooking.exception.SuperException;
import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;
import com.example.pcroombooking.repository.PCRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PCRoomService {

    private final PCRoomRepository pcRoomRepository;

    public PCRoomGetResponse getPCRoom(PCRoomGetRequest pcRoomGetRequest) {
        return PCRoomGetResponse.builder()
                .pcRoom(pcRoomRepository.findByName(pcRoomGetRequest.getPcroomName())
                        .orElseThrow(() -> new SuperException(CustomExceptionType.PCROOM_SEARCH_FAIL_EXCEPTION)))
                .httpStatus(SuccessType.PCROOM_GET_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.PCROOM_GET_SUCCESS.getResponseCode())
                .result(SuccessType.PCROOM_GET_SUCCESS.getResult())
                .responseMessage(SuccessType.PCROOM_GET_SUCCESS.getResponseMessage())
                .build();
    }

    public PCRoomsResponse getPCRoomList() {
        List<PCRoom> pcRooms = pcRoomRepository.findAll();
        if(pcRooms.size() == 0) {
            throw new SuperException(CustomExceptionType.PCROOM_NOT_FOUND_EXCEPTION);
        }

        return PCRoomsResponse.builder()
                .pcRooms(pcRooms)
                .httpStatus(SuccessType.PCROOM_SEARCH_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.PCROOM_SEARCH_SUCCESS.getResponseCode())
                .result(SuccessType.PCROOM_SEARCH_SUCCESS.getResult())
                .responseMessage(SuccessType.PCROOM_SEARCH_SUCCESS.getResponseMessage())
                .build();
    }

    public PCRoomAddResponse addPCRoom(PCRoomAddRequest pcRoomAddRequest) {
        return PCRoomAddResponse.builder()
                .pcRoom(pcRoomRepository.save(pcRoomAddRequest.toPCRoom()))
                .httpStatus(SuccessType.PCROOM_ADD_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.PCROOM_ADD_SUCCESS.getResponseCode()).
                result(SuccessType.PCROOM_ADD_SUCCESS.getResult())
                .responseMessage(SuccessType.PCROOM_ADD_SUCCESS.getResponseMessage())
                .build();
    }

    public PCRoomUpdateResponse updatePCRoom(PCRoomUpdateRequest pcRoomUpdateRequest) {
        PCRoom pcRoom = pcRoomRepository.findByName(pcRoomUpdateRequest.getPcroomName())
                .orElseThrow(() -> new SuperException(CustomExceptionType.PCROOM_NOT_FOUND_EXCEPTION));

        pcRoom.setSeatsStr(pcRoomUpdateRequest.getSeatsStr());

        List<Seat> newSeats = pcRoom.getSeats();
        newSeats.forEach(seat -> {
            if(seat.getSeatName().equals(pcRoomUpdateRequest.getSeatName())) {
                seat.setSeatType(pcRoomUpdateRequest.getViewTag());
                seat.setBooked(pcRoomUpdateRequest.getBooked());
                seat.setUsing(pcRoomUpdateRequest.getInuse());
                seat.setUserEmail(pcRoomUpdateRequest.getUserEmail());
            }
        });

        pcRoom.setSeats(newSeats);

        return PCRoomUpdateResponse.builder()
                .pcRoom(pcRoomRepository.save(pcRoom))
                .httpStatus(SuccessType.PCROOM_UPDATE_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.PCROOM_UPDATE_SUCCESS.getResponseCode())
                .result(SuccessType.PCROOM_UPDATE_SUCCESS.getResult())
                .responseMessage(SuccessType.PCROOM_UPDATE_SUCCESS.getResponseMessage())
                .build();
    }


}
