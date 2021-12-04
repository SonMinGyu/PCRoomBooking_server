package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.domain.Seat;
import com.example.pcroombooking.dto.SeatRequest;
import com.example.pcroombooking.dto.SeatResponse;
import com.example.pcroombooking.dto.successType.SuccessType;
import com.example.pcroombooking.exception.SuperException;
import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;
import com.example.pcroombooking.repository.PCRoomRepository;
import com.example.pcroombooking.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final PCRoomRepository pcRoomRepository;

    // pc room 만들고 seat 추가하는 페이지에 들어가면 한번 호출해서 front로 불러오기
    public SeatResponse getSeatByPCRoom(String pcRoomName) {

        List<Seat> seats = seatRepository.findByPcRoom_Name(pcRoomName);

        if(seats.size() == 0) {
            throw new SuperException(CustomExceptionType.SEAT_NOT_IN_PCROOM_EXCEPTION);
        }

        return SeatResponse.builder()
                .seats(seats)
                .httpStatus(SuccessType.SEAT_SEARCH_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.SEAT_SEARCH_SUCCESS.getResponseCode())
                .result(SuccessType.SEAT_SEARCH_SUCCESS.getResult())
                .responseMessage(pcRoomName + SuccessType.SEAT_SEARCH_SUCCESS.getResponseMessage())
                .build();
    }

    public Seat addSeat(SeatRequest seatRequest) {
        // pcRoomRepository 를 save 했는데 seatRepository에 newSeat가 알아서 save 됨.
        // 이유 알아보기. 아마 영속성에 관련된것 같은데 seatRepository에서 불러온적도 없는데 왜 영속성 발생?
        PCRoom pcRoom = pcRoomRepository.findByName(seatRequest.getPcRoomName()).orElseThrow(() -> new SuperException(CustomExceptionType.PCROOM_NOT_EXIST_EXCEPTION));

        Seat newSeat = Seat.builder()
                .pcRoomName(seatRequest.getPcRoomName())
                .pcRoom(pcRoom)
                .seatName(seatRequest.getSeatName())
                .isUsing(false)
                .enabled(seatRequest.isEnabled())
                .build();

        Set<Seat> seats = pcRoom.getSeats();
        seats.add(newSeat);
        pcRoom.setSeats(seats);

        pcRoomRepository.save(pcRoom);

        return seatRepository
                .findByPcRoom_NameAndAndSeatName(seatRequest.getPcRoomName(), seatRequest.getSeatName())
                .orElseThrow(() -> new SuperException(CustomExceptionType.SEAT_SAVE_FAIL_EXCEPTION));
    }

}
