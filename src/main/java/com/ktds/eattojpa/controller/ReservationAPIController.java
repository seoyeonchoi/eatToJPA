package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.domain.Reservation;
import com.ktds.eattojpa.domain.User;
import com.ktds.eattojpa.service.ReservationService;
import com.ktds.eattojpa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReservationAPIController {
    private final ReservationService reservationService;
    private final UserService userService;

    // 참석 등록
    @GetMapping("/api/reservation/{boardId}")
    public ResponseEntity<?> reservation(Authentication auth, @PathVariable String boardId) {
        // 로그인 정보 확인
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("로그인 에러");
        }
        User loginUser = userService.findByEmail(auth.getName());
        String loginId = loginUser.getId();
        System.out.println(loginId + " " + boardId);

        // 참석 등록
        Reservation savedReservation = reservationService.save(boardId, loginId);
        if (savedReservation != null) {
            System.out.println("만들어졌다. " + savedReservation.getMemberId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedReservation);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("생성 실패");
    }

    // 참석자 전체 조회
    @GetMapping("/api/all-attention/{boardId}")
    public ResponseEntity<?> attention(@PathVariable String boardId) {
        // 참석자 조회
        List<String> MemberList = new ArrayList<>();
        List<Reservation> responseList = reservationService.findByBoardId(boardId);
        for (Reservation reservation : responseList) {
            User user = userService.findById(reservation.getMemberId());
            MemberList.add(user.getName());
        }
        return ResponseEntity.ok()
                .body(MemberList);
    }


    // 유저의 참석 여부 판단
    @GetMapping("/api/attention/{boardId}")
    public ResponseEntity<?> attentionByAuth(@PathVariable String boardId, Authentication auth) {
        // 로그인 정보 확인
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("로그인 에러");
        }
        User loginUser = userService.findByEmail(auth.getName());
        String loginId = loginUser.getId();

        // 참석등록 여부 조회
        boolean result = reservationService.findByBoardIdAndMemberId(boardId, loginId);
        if (result) {
            return ResponseEntity.ok()
                    .body(true);
        }
        return ResponseEntity.ok()
                .body(false);
    }

    @DeleteMapping("/api/attention/{boardId}")
    public ResponseEntity<?> cancelByAuth(@PathVariable String boardId, Authentication auth) {
        // 로그인 정보 확인
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("로그인 에러");
        }
        User loginUser = userService.findByEmail(auth.getName());
        String loginId = loginUser.getId();

        // 참석취소
        reservationService.deleteByBoardIdAndMemberId(boardId, loginId);
        return ResponseEntity.ok()
                .body(true);
    }
}
