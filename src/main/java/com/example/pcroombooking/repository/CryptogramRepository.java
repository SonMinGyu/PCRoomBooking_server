package com.example.pcroombooking.repository;

import com.example.pcroombooking.domain.Cryptogram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CryptogramRepository extends JpaRepository<Cryptogram, Long> {

    Optional<Cryptogram> findByCryptogramAndTargetEmailOrderByCreatedAtDesc(String cryptogram, String targetEamil);

    List<Cryptogram> findByCryptogramAndTargetEmail(String cryptogram, String targetEamil);
}
