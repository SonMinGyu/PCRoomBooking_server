package com.example.pcroombooking.service;

//import com.example.pcroombooking.domain.Authority.Authority;
import com.example.pcroombooking.domain.User;
import com.example.pcroombooking.dto.UserLoginRequest;
import com.example.pcroombooking.dto.UserLoginResponse;
import com.example.pcroombooking.dto.UserRegisterRequest;
import com.example.pcroombooking.dto.UserRegisterResponse;
import com.example.pcroombooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Optional<User> findUser(Long userId) {
        return userRepository.findById(userId);
    }

    // client에서 UserRegisterRequest를 server로 POST 하면
    // server에서 UserRegisterRequest를 User로 변환하고 db에 저장
    // 그리고 User를 UserRegisterResponse로 변환해서 response
    public UserRegisterResponse registUser(UserRegisterRequest userRegisterRequest) {
        return userRepository.save(userRegisterRequest.toEntity()).toUserRegisterResponse();
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
