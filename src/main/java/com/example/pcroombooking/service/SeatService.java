package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.domain.Seat;
import com.example.pcroombooking.domain.User;
import com.example.pcroombooking.dto.*;
import com.example.pcroombooking.dto.successType.SuccessType;
import com.example.pcroombooking.exception.SuperException;
import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;
import com.example.pcroombooking.repository.PCRoomRepository;
import com.example.pcroombooking.repository.SeatRepository;
import com.example.pcroombooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final PCRoomRepository pcRoomRepository;
    private final UserRepository userRepository;

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

    public SeatAddResponse addSeat(SeatAddRequest seatAddRequest) {
        // pcRoomRepository 를 save 했는데 seatRepository에 newSeat가 알아서 save 됨.
        // 이유 알아보기. 아마 영속성에 관련된것 같은데 seatRepository에서 불러온적도 없는데 왜 영속성 발생?
        // 20211217 현재 pcroom 만들면서 seat Set을 같이 저장해서 위와같은 문제는 발생하지 않음
        PCRoom pcRoom = pcRoomRepository.findByName(seatAddRequest.getPcroomName()).orElseThrow(() -> new SuperException(CustomExceptionType.PCROOM_NOT_EXIST_EXCEPTION));

        List<Seat> newSeats = seatAddRequest.getSeats();
        newSeats.forEach(seat -> seat.setPcRoom(pcRoom));

        return SeatAddResponse.builder()
                .seats(seatRepository.saveAll(newSeats))
                .httpStatus(SuccessType.SEAT_ADD_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.SEAT_ADD_SUCCESS.getResponseCode())
                .result(SuccessType.SEAT_ADD_SUCCESS.getResult())
                .responseMessage(SuccessType.SEAT_ADD_SUCCESS.getResponseMessage())
                .build();
    }

    public SeatUpdateResponse updateSeat(SeatUpdateRequest seatUpdateRequest) {

        Seat seat = seatRepository.findByPcRoom_NameAndAndSeatName(seatUpdateRequest.getPcroomName()
                , seatUpdateRequest.getSeatName()).orElseThrow(() -> new SuperException(CustomExceptionType.SEAT_NOT_FOUND_EXCEPTION));


        // client에서 예약하려는 와중에 예약되었을 때를 방지
        // updateSeat가 불린 경우는 좌석 예약하는경우, 즉 booked는 무조건 true인 경우
        // db에서 불러온 seat 가 예약되어있나 확인
        if(seat.isBooked()) {
            throw new SuperException(CustomExceptionType.SEAT_ALREADY_RESERVED_EXCEPTION);
        }

        seat.setUserEmail(seatUpdateRequest.getUserEmail());
        seat.setBooked(seatUpdateRequest.getBooked());
        seat.setUsing(seatUpdateRequest.getInuse());
        seat.setSeatType(seatUpdateRequest.getViewTag());
        seatRepository.save(seat);

        return SeatUpdateResponse.builder()
                .seats(seatRepository.findByPcRoom_Name(seatUpdateRequest.getPcroomName()))
                .httpStatus(SuccessType.SEAT_UPDATE_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.SEAT_UPDATE_SUCCESS.getResponseCode())
                .result(SuccessType.SEAT_UPDATE_SUCCESS.getResult())
                .responseMessage(SuccessType.SEAT_UPDATE_SUCCESS.getResponseMessage())
                .build();
    }

    @Transactional
    public SeatBookOrUseResponse updateSelectSeat(SeatBookOrUseRequest seatBookOrUseRequest) {
        System.out.println("pcroomName: " + seatBookOrUseRequest.getPcroomName());
        System.out.println("seatName: " + seatBookOrUseRequest.getSeatName());

        Seat seat = seatRepository.findByPcRoom_NameAndAndSeatName(
                seatBookOrUseRequest.getPcroomName(),
                seatBookOrUseRequest.getSeatName())
                .orElseThrow(() -> new SuperException(CustomExceptionType.SEAT_NOT_FOUND_EXCEPTION)
                );

        // client에서 예약하려는 와중에 예약되었을 때를 방지
        // updateSeat가 불린 경우는 좌석 예약하는경우, 즉 booked는 무조건 true인 경우
        // db에서 불러온 seat 가 예약되어있나 확인
        if(seat.isBooked()) {
            throw new SuperException(CustomExceptionType.SEAT_ALREADY_RESERVED_EXCEPTION);
        }


        String pcroomName = seatBookOrUseRequest.getPcroomName();
        String userEmail = seatBookOrUseRequest.getUserEmail();
        Boolean booked = seatBookOrUseRequest.getBooked();
        Boolean inuse = seatBookOrUseRequest.getInuse();

        // seatRepository 업데이트

        seat.setUserEmail(userEmail);
        seat.setBooked(booked);
        seat.setUsing(inuse);
        seat.setSeatType(seatBookOrUseRequest.getViewTag());
        seatRepository.save(seat);



        // userRepository 업데이트
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new SuperException(CustomExceptionType.USER_NOT_FOUND_EXCEPTION));
        user.setSeatBook(booked);
        user.setSeatUse(inuse);
        user.setSeat(seat);
        userRepository.save(user);

        // seatRepository 업데이트
        List<Seat> getSeats = seatRepository.findByPcRoom_Name(pcroomName);
        System.out.println("getSeats: " + getSeats);

        PCRoom pcRoom = pcRoomRepository.findByName(pcroomName)
                .orElseThrow(() -> new SuperException(CustomExceptionType.PCROOM_NOT_FOUND_EXCEPTION));

        pcRoom.setSeatsStr(seatBookOrUseRequest.getSeatsStr());
        pcRoom.setSeats(getSeats);

        pcRoomRepository.save(pcRoom);


        return SeatBookOrUseResponse.builder()
                .seat(seat)
                .httpStatus(SuccessType.SEAT_UPDATE_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.SEAT_UPDATE_SUCCESS.getResponseCode())
                .result(SuccessType.SEAT_UPDATE_SUCCESS.getResult())
                .responseMessage(SuccessType.SEAT_UPDATE_SUCCESS.getResponseMessage())
                .build();
    }

}
