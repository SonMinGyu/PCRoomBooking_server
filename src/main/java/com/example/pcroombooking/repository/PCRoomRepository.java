package com.example.pcroombooking.repository;

import com.example.pcroombooking.domain.PCRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PCRoomRepository extends JpaRepository<PCRoom, Long> {

    List<PCRoom> findAllByEnabledIsTrue();

    Optional<PCRoom> findByName(String name);

}
