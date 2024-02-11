package com.ktds.eattojpa.service;

import com.ktds.eattojpa.domain.User;
import com.ktds.eattojpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    // 사용자 이메일로 사용자 정보를 가져오는 메서드
    @Override
    public User loadUserByUsername(String email){
        System.out.println("loadUserByUsername 실행 중" + email);
        return userRepository.findByEmail(email)
                .orElseThrow(() ->  new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));
    }
}
