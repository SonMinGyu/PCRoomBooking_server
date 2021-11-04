package com.example.pcroombooking.service;

//import com.example.pcroombooking.domain.Authority.Authority;
import com.example.pcroombooking.domain.User;
import com.example.pcroombooking.dto.UserLoginRequest;
import com.example.pcroombooking.dto.UserLoginResponse;
import com.example.pcroombooking.dto.UserRegisterRequest;
import com.example.pcroombooking.dto.UserRegisterResponse;
import com.example.pcroombooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // client에서 UserRegisterRequest를 server로 POST 하면
    // server에서 UserRegisterRequest를 User로 변환하고 db에 저장
    // 그리고 User를 UserRegisterResponse로 변환해서 response
    public UserRegisterResponse registUser(UserRegisterRequest userRegisterRequest) {
        return userRepository.save(userRegisterRequest.toEntity()).toUserRegisterResponse();
    }

    public UserLoginResponse loginUserInfo(UserLoginRequest userLoginRequest) {

        Optional<User> loginUser = userRepository.findByEmail(userLoginRequest.getEmail());

        if(loginUser.isPresent()) {
            if(userLoginRequest.getPassword().equals(loginUser.get().getPassword())) {
                return loginUser.get().toUserLoginResponse();
            } else {
                return UserLoginResponse.builder()
                        .email("fail")
                        .build();
            }
        } else {
            return UserLoginResponse.builder()
                    .email("null")
                    .build();
        }
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
