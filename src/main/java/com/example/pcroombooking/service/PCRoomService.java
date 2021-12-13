package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.dto.PCRoomAddRequest;
import com.example.pcroombooking.dto.PCRoomAddResponse;
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

    public PCRoomAddResponse addPCRoom(PCRoomAddRequest pcRoomAddRequest) {
        return PCRoomAddResponse.builder()
                .pcRoom(pcRoomRepository.save(pcRoomAddRequest.toPCRoom()))
                .httpStatus(SuccessType.PCROOM_ADD_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.PCROOM_ADD_SUCCESS.getResponseCode()).
                result(SuccessType.PCROOM_ADD_SUCCESS.getResult())
                .responseMessage(SuccessType.PCROOM_ADD_SUCCESS.getResponseMessage())
                .build();
    }


}
