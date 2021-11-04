package com.example.pcroombooking.service;

//import com.example.pcroombooking.domain.Authority.Authority;
import com.example.pcroombooking.domain.User;
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

    public User save(User user) {
        return userRepository.save(user);
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
