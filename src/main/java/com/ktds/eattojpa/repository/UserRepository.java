package com.ktds.eattojpa.repository;

import com.ktds.eattojpa.domain.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);  // email로 사용자 정보 가져오기

    boolean existsByEmail(String email);

}
