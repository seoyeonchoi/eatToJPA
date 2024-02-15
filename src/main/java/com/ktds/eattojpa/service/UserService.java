package com.ktds.eattojpa.service;

import com.ktds.eattojpa.domain.Reply;
import com.ktds.eattojpa.domain.User;
import com.ktds.eattojpa.dto.AddUserRequest;
import com.ktds.eattojpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public String save(AddUserRequest dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .name((dto.getName()))
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword())) // 패스워드 암호화
                .build()).getId();
    }

    public User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email) {
        System.out.println("findByEmail 중이다" + email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("unexpected user"));
    }

    public boolean update(AddUserRequest dto) {
        Optional<User> userOptional = userRepository.findByEmail(dto.getEmail());
        User updateUser = userOptional.orElseThrow(() -> new IllegalArgumentException("User not found"));
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        updateUser.setPassword(encoder.encode(dto.getPassword()));

        try {
            userRepository.save(updateUser);
            System.out.println("비밀번호 업데이트 성공");
            return true;
        } catch (Exception e) {
            System.out.println("비밀번호 업데이트 실패: " + e.getMessage());
            // 실패 시 필요한 추가 작업 수행
            return false;
        }
    }

    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
