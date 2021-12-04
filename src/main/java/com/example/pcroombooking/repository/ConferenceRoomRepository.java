package com.example.pcroombooking.repository;

import com.example.pcroombooking.domain.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {
    Optional<ConferenceRoom> findByName(String name);
}
