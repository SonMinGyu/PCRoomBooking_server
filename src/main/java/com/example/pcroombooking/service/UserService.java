package com.example.pcroombooking.service;

//import com.example.pcroombooking.domain.Authority.Authority;
import com.example.pcroombooking.config.JwtTokenProvider;
import com.example.pcroombooking.domain.User;
import com.example.pcroombooking.dto.UserLoginRequest;
import com.example.pcroombooking.dto.UserLoginResponse;
import com.example.pcroombooking.dto.UserRegisterRequest;
import com.example.pcroombooking.dto.UserRegisterResponse;
import com.example.pcroombooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserLoginResponse loginUserInfo(UserLoginRequest userLoginRequest) {

        User user = loadUserByUsername(userLoginRequest.getEmail());

        if(passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {

            return UserLoginResponse.builder()
                    .resultCode(200)
                    .result("Login Success")
                    .message("로그인에 성공하였습니다.")
                    .email(user.getEmail())
                    .authorities(user.getAuthorities())
                    .build();
        } else {
            // 현재 email 이 없을시 UsernameNotFoundException 발생
            // 비밀번호 오류시 Exception 발생 시켜서 처리하는 것으로 바꾸자
            return UserLoginResponse.builder()
                    .resultCode(401)
                    .result("Login Fail")
                    .message("비밀번호가 틀렸습니다.")
                    .build();
        }
    }

    // client에서 UserRegisterRequest를 server로 POST 하면
    // server에서 UserRegisterRequest를 User로 변환하고 db에 저장
    // 그리고 User를 UserRegisterResponse로 변환해서 response
    public UserRegisterResponse registUser(UserRegisterRequest userRegisterRequest) {
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
        savedUserResponse.setResultCode(200);
        savedUserResponse.setResult("Register Success");
        savedUserResponse.setMessage("회원가입에 성공하셨습니다.");
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
