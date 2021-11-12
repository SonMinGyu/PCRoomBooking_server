package com.example.pcroombooking.controller;

import com.example.pcroombooking.domain.Cryptogram;
import com.example.pcroombooking.repository.CryptogramRepository;
import com.example.pcroombooking.service.CryptogramService;
import com.example.pcroombooking.service.MailService;
import com.example.pcroombooking.config.JwtTokenProvider;
import com.example.pcroombooking.dto.*;
import com.example.pcroombooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MailService mailService;
    private final CryptogramService cryptogramService;
    private final CryptogramRepository cryptogramRepository;

//    만약 어떤 resource를 식별하고 싶으면 Path Variable을 사용하고,
//    정렬이나 필터링을 한다면 Query Parameter를 사용하는 것이 Best Practice이다.

    private final UserService userService;
//    private final UserRepository userRepository;


    @GetMapping("/user/test")
    public String test() {

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

//        httpServletResponse.setHeader("Authorization", token);


        UserLoginResponse userLoginResponse = userService.loginUserInfo(userLoginRequest);

        if(userLoginResponse.getResultCode() == 200) {
            String token = jwtTokenProvider.createJwtToken(userLoginResponse.getEmail(), userLoginResponse.getAuthorities());
            userLoginResponse.setJwtToken(token);
        }

        return userLoginResponse;
    }

    @PostMapping("/user/register")
    public UserRegisterResponse register(@RequestBody UserRegisterRequest userRegisterRequest) {
        Optional<Cryptogram> getCrt = cryptogramService.getCryptogram(userRegisterRequest.getCryptogram(), userRegisterRequest.getEmail());
        Optional<Cryptogram> getCrt2 = cryptogramRepository.findByCryptogramAndTargetEmail(userRegisterRequest.getCryptogram(), userRegisterRequest.getEmail());

        System.out.println("!!!!!!!!!!!!!!!!!" + getCrt.isPresent());
        System.out.println("222222222222222222" + getCrt2.isPresent());

        if(getCrt.isPresent() && getCrt.get().isVerified()) {
            return userService.registUser(userRegisterRequest);
        } else {
            return UserRegisterResponse.builder()
                    .resultCode(401)
                    .result("Register fail")
                    .message("문자열 인증이 완료되지 않았습니다.")
                    .build();
        }
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

}
