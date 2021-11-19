package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.dto.PCRoomResponse;
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

    public PCRoomResponse getPCRoomList() {

        return PCRoomResponse.builder()
                .pcRooms(pcRoomRepository.findAllByEnabledIsTrue().orElseThrow(() -> new SuperException(CustomExceptionType.PCROOM_NOT_FOUND_EXCEPTION)))
                .httpStatus(SuccessType.PCROOM_SEARCH_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.PCROOM_SEARCH_SUCCESS.getResponseCode())
                .result(SuccessType.PCROOM_SEARCH_SUCCESS.getResult())
                .responseMessage(SuccessType.PCROOM_SEARCH_SUCCESS.getResponseMessage())
                .build();
    }

    public PCRoom addPCRoom(PCRoom pcRoom) {
        return pcRoomRepository.save(
                PCRoom.builder()
                        .name(pcRoom.getName())
                        .buildingNumber(pcRoom.getBuildingNumber())
                        .layer(pcRoom.getLayer())
                        .allSeatNumber(pcRoom.getAllSeatNumber())
                        .brokenSeatNumber(pcRoom.getBrokenSeatNumber())
                        .useableSeatNumber(pcRoom.getUseableSeatNumber())
                        .inUseSeatNumber(pcRoom.getInUseSeatNumber())
                        .enabled(pcRoom.isEnabled())
                        .build()
        );
    }


}
