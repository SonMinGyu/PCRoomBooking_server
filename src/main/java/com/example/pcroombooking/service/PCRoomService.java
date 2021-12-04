package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.dto.PCRoomResponse;
import com.example.pcroombooking.dto.successType.SuccessType;
import com.example.pcroombooking.exception.SuperException;
import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;
import com.example.pcroombooking.repository.PCRoomRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PCRoomService {

    private final PCRoomRepository pcRoomRepository;

    public PCRoomResponse getPCRoomList() {

        List<PCRoom> pcRooms = pcRoomRepository.findAll();
        if(pcRooms.size() == 0) {
            throw new SuperException(CustomExceptionType.PCROOM_NOT_FOUND_EXCEPTION);
        }

        return PCRoomResponse.builder()
                .pcRooms(pcRooms)
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
                        .pcSeatNumber(pcRoom.getPcSeatNumber())
                        .pcSeatBrokenNumber(pcRoom.getPcSeatBrokenNumber())
                        .pcSeatInUseNumber(pcRoom.getPcSeatInUseNumber())
                        .pcSeatUseableNumber(pcRoom.getPcSeatUseableNumber())
                        .notebookSeatNumber(pcRoom.getNotebookSeatNumber())
                        .notebookSeatBrokenNumber(pcRoom.getNotebookSeatBrokenNumber())
                        .notebookSeatInUseNumber(pcRoom.getNotebookSeatInUseNumber())
                        .notebookSeatUseableNumber(pcRoom.getNotebookSeatUseableNumber())
                        .enabled(pcRoom.isEnabled())
                        .build()
        );
    }


}
