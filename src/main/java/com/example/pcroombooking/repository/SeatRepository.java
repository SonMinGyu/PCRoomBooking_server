package com.example.pcroombooking.repository;

import com.example.pcroombooking.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {

//    @Query("select a from Seat a, User b where a.user.id=b.id and b.id=?1")
//    Optional<Seat> findUserByUserId(Long userId);

}
