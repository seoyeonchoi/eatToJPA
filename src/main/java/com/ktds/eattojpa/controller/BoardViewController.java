package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.dto.BoardListViewResponse;
import com.ktds.eattojpa.dto.BoardResponseForCalendar;
import com.ktds.eattojpa.dto.BoardViewResponse;
import com.ktds.eattojpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardViewController {
    private final BoardService boardService;

    // 임시 - 전체 boards 조회
    @GetMapping("/boards")
    public String getBoards(Model model) {
        List<BoardListViewResponse> boards = boardService.findAll().stream()
                .map(BoardListViewResponse::new)
                .toList();
        model.addAttribute("articles", boards); // 블로그 글 리스트 저장
        return "example/articleList";
    }

    // 임시 - 바로 main 반환
    @GetMapping("/mainSample")
    public String getMainSample() {
        log.info("요청 들어옴");
        return "main";
    }
    // 임시 - 바로 메뉴등록 반환
    @GetMapping("/new-menu")
    public String getCreateBoardForm() {
        return "createBoardForm";
    }

    // 1. main 조회
    @GetMapping("/main/{meetDate}")
    public String getMain(@PathVariable(required = false) String meetDate, Model model) {
        LocalDate parsedDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        if (meetDate != null) {
            parsedDate = LocalDate.parse(meetDate, DateTimeFormatter.ISO_DATE);
        }
        model.addAttribute("meetDate", parsedDate);
        // 저장된 모든 리스트 저장
        List<BoardResponseForCalendar> boards = boardService.findAllforCalendar().stream()
                .map(BoardResponseForCalendar::new)
                .toList();
        model.addAttribute("boardsforCalendar", boards); // 블로그 글 리스트 저장

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
