package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.domain.User;
import com.ktds.eattojpa.dto.BoardRequest;
import com.ktds.eattojpa.dto.BoardResponse;
import com.ktds.eattojpa.dto.BoardResponseForCalendar;
import com.ktds.eattojpa.service.BoardService;
import com.ktds.eattojpa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardAPIController {
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/api/boards")
    public ResponseEntity<Board> addBoard(@RequestBody BoardRequest request, Authentication auth) {
        log.info(request.toString());
        Board savedBoard = boardService.save(request, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedBoard);
    }

    @GetMapping("/api/boards")
    public ResponseEntity<List<BoardResponse>> findAllBoards() {
        List<BoardResponse> boards = boardService.findAll()
                .stream()
                .map(BoardResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(boards);
    }

    @GetMapping("/api/boards-CalendarForm")
    public ResponseEntity<List<BoardResponseForCalendar>> findAllBoardsCalendarForm() {
        List<BoardResponseForCalendar> boards = boardService.findAllforCalendar().stream()
                .map(BoardResponseForCalendar::new)
                .toList();
        return ResponseEntity.ok()
                .body(boards);
    }

    @GetMapping("/api/board/{id}")
    //URL 경로에서 값 추출
    public ResponseEntity<BoardResponse> findBoard(@PathVariable String id) {
        Board board = boardService.findById(id);
        return ResponseEntity.ok()
                .body(new BoardResponse(board));
    }

    @DeleteMapping("/api/boards/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable String id) {
        boardService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/boards/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable String id, @RequestBody BoardRequest boardRequest) {
        Board updatedBoard = boardService.update(id, boardRequest);
        return ResponseEntity.ok()
                .body(updatedBoard);
    }

    @GetMapping("/api/boards/{meetDate}")
    //URL 경로에서 값 추출
    public ResponseEntity<List<BoardResponse>> findBoardsbyMeetDate(@PathVariable LocalDate meetDate) {
        List<BoardResponse> boards = boardService.findByDate(meetDate)
                .stream()
                .map(BoardResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(boards);
    }

    @GetMapping("/api/existsByEmailAndMeetDate/{meetDate}")
    public ResponseEntity<String> existsByEmailAndMeetDate(@PathVariable LocalDate meetDate, Authentication auth) {
        // 로그인 정보 확인
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("로그인 에러");
        }
        User loginUser = userService.findByEmail(auth.getName());
        String loginId = loginUser.getId();
        // 작성 가능 여부 확인
        if (boardService.existsByEmailAndMeetDate(meetDate, loginId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이미 작성한 글이 있습니다. 같은 날에 하나의 메뉴만 등록 가능합니다!");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("작성 가능");

    }

}
