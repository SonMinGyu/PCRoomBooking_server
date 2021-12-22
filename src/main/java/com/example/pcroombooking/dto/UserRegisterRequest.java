package com.example.pcroombooking.dto;

import com.example.pcroombooking.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {

    private String name;

    private String email;

    private String password;

    @JsonProperty("student_number")
    private String studentNumber; // 학번, 교수님일 경우 X

    private String major;

    private String cryptogram;

    public User toEntity() {
        return User.builder()
                .name(getName())
                .email(getEmail())
                .password(getPassword())
                .studentNumber(getStudentNumber())
                .major(getMajor())
                .seatBook(false)
                .seatUse(false)
                .enabled(true)
                .build();
    }

}
