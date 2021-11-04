package com.example.pcroombooking.repository;

import com.example.pcroombooking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findUserByEnabled(boolean enabled);

    User findUserById(Long id);


}
