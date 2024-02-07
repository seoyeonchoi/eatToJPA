package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.dto.BoardListViewResponse;
import com.ktds.eattojpa.dto.BoardViewResponse;
import com.ktds.eattojpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardViewController {
    private final BoardService boardService;

    @GetMapping("/boards")
    public String getBoards(Model model) {
        List<BoardListViewResponse> boards = boardService.findAll().stream()
                .map(BoardListViewResponse::new)
                .toList();
        model.addAttribute("articles", boards); // 블로그 글 리스트 저장
        return "example/boardList";
    }

    // main 조회
    @GetMapping("/main/{meetDate}")
    public String getMain(@PathVariable(required = false) Timestamp meetDate, Model model) {
        // 현재 날짜 설정
        if (meetDate == null) {
            meetDate = new Timestamp(System.currentTimeMillis());
        }

        model.addAttribute("meetDate", meetDate);
        // 저장된 모든 리스트 저장
        List<BoardListViewResponse> boards = boardService.findAll().stream()
                .map(BoardListViewResponse::new)
                .toList();
        model.addAttribute("boards", boards); // 블로그 글 리스트 저장

        return "main";
    }


    // 새 메뉴 등록
    @GetMapping("/new-board/{meedDate}/{memberId}")
    public String newBoard(@RequestParam(required = true) Timestamp meetDate, Model model) {
        // 1. 유저가 작성한 적 있는지 확인

        // 2. 작성한 적 없으면 생성
        model.addAttribute("meedDate", meetDate);
        model.addAttribute("board", new BoardViewResponse());

        return "main";
    }
}
