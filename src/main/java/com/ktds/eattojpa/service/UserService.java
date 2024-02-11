package com.ktds.eattojpa.service;

import com.ktds.eattojpa.domain.User;
import com.ktds.eattojpa.dto.AddUserRequest;
import com.ktds.eattojpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .name((dto.getName()))
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword())) // 패스워드 암호화
                .build()).getId();
    }
}
