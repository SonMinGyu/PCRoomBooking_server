package com.example.pcroombooking.repository;

import com.example.pcroombooking.domain.Cryptogram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptogramRepository extends JpaRepository<Cryptogram, Long> {

    Optional<Cryptogram> findByCryptogramAndTargetEmail(String cryptogram, String targetEamil);
}
