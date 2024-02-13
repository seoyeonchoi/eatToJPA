package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReservationAPIController {
    private final ReservationService reservationService;

    // 참석 등록
//    @GetMapping("/api/reservation/{boardId}")
//    public ResponseEntity<?> reservation(Authentication auth) {
//
//    }
}
