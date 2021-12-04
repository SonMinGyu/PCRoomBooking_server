package com.example.pcroombooking.repository;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

//    @Query("select a from Seat a, User b where a.user.id=b.id and b.id=?1")
//    Optional<Seat> findUserByUserId(Long userId);

    List<Seat> findByPcRoom_Name(String pcRoomName);

    Optional<Seat> findByPcRoom_NameAndAndSeatName(String pcRoomName, String seatName);

}
