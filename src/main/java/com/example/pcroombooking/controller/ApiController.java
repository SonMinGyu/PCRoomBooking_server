package com.example.pcroombooking.controller;

import com.example.pcroombooking.domain.User;
import com.example.pcroombooking.dto.UserRegisterRequest;
import com.example.pcroombooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

//    만약 어떤 resource를 식별하고 싶으면 Path Variable을 사용하고,
//    정렬이나 필터링을 한다면 Query Parameter를 사용하는 것이 Best Practice이다.

    private final UserRepository userRepository;

    @PostMapping("/user/login")
    public String login(@RequestBody String email) {

        return email;
    }

    @GetMapping("/getMessage")
    public String getMessage() {

        return "성공";
    }

    @GetMapping("/user/{userId}")
    public User getUserName(@PathVariable Long userId) {
        return userRepository.findUserById(userId);
    }

    @GetMapping("/user") // dto로 받는 경우는 @RequestParam 안붙임
    public List<User> getEnableUser(@RequestParam boolean enable) {
        return userRepository.findUserByEnabled(enable);
    }


    @PostMapping("/user/register")
    public void register(@RequestBody UserRegisterRequest userRegisterRequest) {
        System.out.println(userRegisterRequest);
    }

}
