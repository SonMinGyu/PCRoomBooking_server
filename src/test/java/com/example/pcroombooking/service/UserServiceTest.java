package com.example.pcroombooking.service;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.domain.Seat;
import com.example.pcroombooking.domain.User;
import com.example.pcroombooking.repository.PCRoomRepository;
import com.example.pcroombooking.repository.SeatRepository;
import com.example.pcroombooking.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private PCRoomRepository pcRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Test
    @DisplayName("01. user 가져오기")
    public void test1() {
//        System.out.println(userService.findUser(userId));

//        userService.addAuthority(1L, "Student");

        User user1 = userRepository.findUserById(1L);

        PCRoom sixFloorPCRoom = pcRoomRepository.findPCRoomById(1L);

        Seat newSeat = Seat.builder()
                .seatName("A1")
                .pcRoom(sixFloorPCRoom)
                .isUsing(true)
                .enabled(true)
                .user(user1)
                .build();

        Seat newSeat1 = new Seat();
        newSeat1.setSeatName("A2");
        newSeat1.setPcRoom(sixFloorPCRoom);
        newSeat1.setUsing(false);
        newSeat1.setEnabled(true);

        seatRepository.save(newSeat);
        seatRepository.save(newSeat1);

        seatRepository.findAll().forEach(seat -> {
            System.out.println(seat);
        });

    }


}