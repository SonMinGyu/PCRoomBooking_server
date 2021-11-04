package com.example.pcroombooking.repository;

import com.example.pcroombooking.domain.PCRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PCRoomRepository extends JpaRepository<PCRoom, Long> {

    PCRoom findPCRoomById(Long id);

}
