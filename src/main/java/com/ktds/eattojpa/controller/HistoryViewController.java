package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.domain.Reservation;
import com.ktds.eattojpa.domain.User;
import com.ktds.eattojpa.dto.HistoryResponse;
import com.ktds.eattojpa.service.BoardService;
import com.ktds.eattojpa.service.ReservationService;
import com.ktds.eattojpa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HistoryViewController {
//    private final HistoryService historyService;
    private final UserService userService;
    private final BoardService boardService;
    private final ReservationService reservationService;

    @GetMapping("/history")
    public String getHistory(Authentication auth, Model model) {
        if (auth == null) {
            return "history";
        }
        System.out.println("auth.getName: " + auth.getName());
        User loginUser = userService.findByEmail(auth.getName());
        if (loginUser == null) {
            return "history";
        }
        System.out.println("userName: " + loginUser.getName());
        model.addAttribute("userName", loginUser.getName());
        model.addAttribute("userId", loginUser.getId());

        LocalDate today = LocalDate.now();
        // 참여
        List<Reservation> reservations = reservationService.findByMemberId(loginUser.getId());
        // 1. 마감 전
        List<HistoryResponse> attentionBeforeCompleted = new ArrayList<>();
        // 2. 마감 완료
        List<HistoryResponse> attentionCompleted = new ArrayList<>();
        // 3. 오늘 일정
        List<HistoryResponse> attentionToday = new ArrayList<>();
        // 3. 지난 참여
        List<HistoryResponse> attentionClosed = new ArrayList<>();

        for (Reservation reservation : reservations) {
            Board board = boardService.findById(reservation.getBoardId());
            HistoryResponse reservationResponse = new HistoryResponse(board);
            LocalDate meetDate = reservationResponse.getMeetDate();
            if (meetDate.isAfter(today)) {
                if (reservationResponse.getCompleted() == 0) {
                    // 마감 전
                    attentionBeforeCompleted.add(reservationResponse);
                } else {
                    // 마감 완료
                    attentionCompleted.add(reservationResponse);
                }
            } else if (meetDate.isEqual(today)) {
                // 오늘 참여
                attentionToday.add(reservationResponse);
            } else if (meetDate.isBefore(today)) {
                // 이미 참여
                attentionClosed.add(reservationResponse);
            }
        }

        model.addAttribute("attentionBeforeCompleted", attentionBeforeCompleted);
        model.addAttribute("attentionCompleted", attentionCompleted);
        model.addAttribute("attentionToday", attentionToday);
        model.addAttribute("attentionClosed", attentionClosed);


        System.out.println("attentionBeforeCompleted " + attentionBeforeCompleted);
        System.out.println("attentionCompleted" + attentionCompleted);
        System.out.println("attentionToday" + attentionToday);
        System.out.println("attentionClosed" + attentionClosed);


        // 모집
        // 참여
        List<Board> boards = boardService.findByMemberId(loginUser.getId());
        // 1. 마감 전
        List<HistoryResponse> BeforeCompleted = new ArrayList<>();
        // 2. 마감 완료
        List<HistoryResponse> Completed = new ArrayList<>();
        // 3. 오늘 일정
        List<HistoryResponse> Today = new ArrayList<>();
        // 3. 지난 참여
        List<HistoryResponse> Closed = new ArrayList<>();

        for (Board board : boards) {
            HistoryResponse response = new HistoryResponse(board);
            LocalDate meetDate = response.getMeetDate();
            if (meetDate.isAfter(today)) {
                if (response.getCompleted() == 0) {
                    // 마감 전
                    BeforeCompleted.add(response);
                } else {
                    // 마감 완료
                    Completed.add(response);
                }
            } else if (meetDate.isEqual(today)) {
                // 오늘 참여
                Today.add(response);
            } else if (meetDate.isBefore(today)) {
                // 이미 참여
                Closed.add(response);
            }
        }
        model.addAttribute("BeforeCompleted", BeforeCompleted);
        model.addAttribute("Completed", Completed);
        model.addAttribute("Today", Today);
        model.addAttribute("Closed", Closed);

        System.out.println("BeforeCompleted" + BeforeCompleted);
        System.out.println("Completed" + Completed);
        System.out.println("Today" + Today);
        System.out.println("Closed" + Closed);

        return "history";
    }
}
