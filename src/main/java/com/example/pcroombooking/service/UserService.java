package com.example.pcroombooking.service;

//import com.example.pcroombooking.domain.Authority.Authority;
import com.example.pcroombooking.domain.Cryptogram;
import com.example.pcroombooking.domain.User;
import com.example.pcroombooking.dto.UserLoginRequest;
import com.example.pcroombooking.dto.UserLoginResponse;
import com.example.pcroombooking.dto.UserRegisterRequest;
import com.example.pcroombooking.dto.UserRegisterResponse;
import com.example.pcroombooking.dto.successType.SuccessType;
import com.example.pcroombooking.exception.EmailNotFoundException;
import com.example.pcroombooking.exception.SuperException;
import com.example.pcroombooking.exception.exceptionType.CustomExceptionType;
import com.example.pcroombooking.repository.CryptogramRepository;
import com.example.pcroombooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CryptogramRepository cryptogramRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws EmailNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new EmailNotFoundException(CustomExceptionType.EMAIL_NOT_FOUND_EXCEPTION));
    }

    public UserLoginResponse loginUserInfo(UserLoginRequest userLoginRequest) {

        User user = loadUserByUsername(userLoginRequest.getEmail());

        if(passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            return UserLoginResponse.builder()
                    .httpStatus(SuccessType.LOGIN_SUCCESS.getHttpStatus())
                    .responseCode(SuccessType.LOGIN_SUCCESS.getResponseCode())
                    .result(SuccessType.LOGIN_SUCCESS.getResult())
                    .responseMessage(SuccessType.LOGIN_SUCCESS.getResponseMessage())
                    .email(user.getEmail())
                    .authorities(user.getAuthorities())
                    .build();
        } else {
            throw new SuperException(CustomExceptionType.WRONG_PASSWORD_EXCEPTION);
        }
    }

    // client?????? UserRegisterRequest??? server??? POST ??????
    // server?????? UserRegisterRequest??? User??? ???????????? db??? ??????
    // ????????? User??? UserRegisterResponse??? ???????????? response
    public UserRegisterResponse registUser(UserRegisterRequest userRegisterRequest) {
        // ????????? cryptogram ???????????? isVerified??? true??? ?????? ????????? ????????? ??????????????????.
        // frontEnd ?????? cryptogram ?????? ????????? ??????(isVerified)??? true??? ???????????? ???????????? ?????? ?????? ?????????????????? ????????? ??????.
        Cryptogram cryptogram = cryptogramRepository.findByCryptogramAndTargetEmailOrderByCreatedAtDesc(userRegisterRequest.getCryptogram(), userRegisterRequest.getEmail())
                .orElseThrow(() -> new SuperException(CustomExceptionType.CRYPTOGRAM_NOT_FOUNT_EXCEPTION));

        if(!cryptogram.isVerified()) {
            throw new SuperException(CustomExceptionType.CRYPTOGRAM_NOT_VERIFIED_EXCEPTION);
        }

        String userPassword = userRegisterRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(userPassword);

        UserRegisterRequest newUserRegisterRequest = UserRegisterRequest.builder()
                .name(userRegisterRequest.getName())
                .email(userRegisterRequest.getEmail())
                .password(encodedPassword)
                .studentNumber(userRegisterRequest.getStudentNumber())
                .major(userRegisterRequest.getMajor())
                .build();

        UserRegisterResponse savedUserResponse = userRepository.save(newUserRegisterRequest.toEntity()).toUserRegisterResponse();
        savedUserResponse.setHttpStatus(SuccessType.REGISTER_SUCCESS.getHttpStatus());
        savedUserResponse.setResponseCode(SuccessType.REGISTER_SUCCESS.getResponseCode());
        savedUserResponse.setResult(SuccessType.REGISTER_SUCCESS.getResult());
        savedUserResponse.setResponseMessage(SuccessType.REGISTER_SUCCESS.getResponseMessage());

        // ???????????? ????????? cryptogram ??????
        List<Cryptogram> deleteCryptogramList = cryptogramRepository.findByCryptogramAndTargetEmail(userRegisterRequest.getCryptogram(), userRegisterRequest.getEmail());
        List<Long> deleteIdList = deleteCryptogramList.stream().map(s -> s.getId()).collect(Collectors.toList());

        cryptogramRepository.deleteAllById(deleteIdList);

        return savedUserResponse;
    }

    public Optional<User> findUser(Long userId) {
        return userRepository.findById(userId);
    }





//    public void addAuthority(Long userId, String authority) {
//        userRepository.findById(userId).ifPresent(user -> {
//            Authority newRole = new Authority(user.getId(), authority);
//            if(user.getAuthorities() == null) {
//                HashSet<Authority> authorityHashSet = new HashSet<>();
//                authorityHashSet.add(newRole);
//                user.setAuthorities(authorityHashSet);
//                userRepository.save(user);
//            } else if(!user.getAuthorities().contains(newRole)) {
//                HashSet<Authority> authorityHashSet = new HashSet<>();
//                authorityHashSet.addAll(user.getAuthorities());
//                authorityHashSet.add(newRole);
//                user.setAuthorities(authorityHashSet);
//                userRepository.save(user);
//            }
//        });
//    }

}
