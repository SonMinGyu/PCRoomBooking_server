package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.dto.PCRoomResponse;
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
                .resultCode(200)
                .result("PC room search success")
                .message("사용 가능한 PC실을 검색 완료하였습니다.")
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
