package com.example.pcroombooking.controller;

import com.example.pcroombooking.domain.PCRoom;
import com.example.pcroombooking.domain.Seat;
import com.example.pcroombooking.service.*;
import com.example.pcroombooking.config.JwtTokenProvider;
import com.example.pcroombooking.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MailService mailService;
    private final CryptogramService cryptogramService;
    private final PCRoomService pcRoomService;
    private final SeatService seatService;

//    만약 어떤 resource를 가져오고 싶으면 Path Variable을 사용하고,
//    정렬이나 필터링을 한다면 Query Parameter를 사용하는 것이 Best Practice이다.

    private final UserService userService;
//    private final UserRepository userRepository;


    @GetMapping("/user/test")
    public String test() {
        System.out.println("성공!!!!!!!!!!!!!!");
        return "성공";
    }


//    @GetMapping("/user/{userId}")
//    public User getUserName(@PathVariable Long userId) {
//        return userRepository.findUserById(userId);
//    }

//    @GetMapping("/user") // dto로 받는 경우는 @RequestParam 안붙임
//    public List<User> getEnableUser(@RequestParam boolean enable) {
//        return userRepository.findUserByEnabled(enable);
//    }


    @PostMapping("/user/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest userLoginRequest, HttpServletResponse httpServletResponse) {
        // jwtToken 을 만들어서 userLoginResponse에 담아서 return 하자


        UserLoginResponse userLoginResponse = userService.loginUserInfo(userLoginRequest);

        if(userLoginResponse.getResultCode() == 200) {
            String token = jwtTokenProvider.createJwtToken(userLoginResponse.getEmail(), userLoginResponse.getAuthorities());
//            httpServletResponse.setHeader("Authorization", token);
            userLoginResponse.setJwtToken(token);
        }

        return userLoginResponse;
    }

    @PostMapping("/user/register")
    public UserRegisterResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userService.registUser(userRegisterRequest);
    }

    // gmail.com 들어가서 보안 설정해야 mail 전송가능
    @PostMapping("/user/register/mail")
    private EmailSendResponse sendMailForRegister(@RequestBody EmailSendRequest emailSendRequest) {
        return mailService.gmailSend(emailSendRequest);
    }

    @PostMapping("/user/register/cryptogram")
    public CryptogramResponse verityCryptogram(@RequestBody CryptogramRequest cryptogramRequest) {
        return cryptogramService.vefiryCryptogram(cryptogramRequest.getInputCryptogram(), cryptogramRequest.getInputEmail())
                .toCryptogramResponse();
    }

    @GetMapping("/pcroom")
    public PCRoomResponse getPCRooms() {
        return pcRoomService.getPCRoomList();
    }

    @PostMapping("/pcroom/add-pcroom")
    public PCRoom addPCRoom(@RequestBody PCRoom pcRoom) {
        return pcRoomService.addPCRoom(pcRoom);
    }

    @GetMapping("/pcroom/seat/{pcRoomName}")
    public SeatResponse getSeatsInPCRoom(@PathVariable String pcRoomName) {
        return seatService.getSeatByPCRoom(pcRoomName);
    }

    @PostMapping("/pcroom/add-seat")
    public Seat addSeat(@RequestBody SeatRequest seatRequest) {
        return seatService.addSeat(seatRequest);
    }

}
