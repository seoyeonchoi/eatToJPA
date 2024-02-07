package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.dto.BoardRequest;
import com.ktds.eattojpa.dto.BoardResponse;
import com.ktds.eattojpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardAPIController {
    private final BoardService boardService;

    @PostMapping("/api/boards")
    public ResponseEntity<Board> addBoard(@RequestBody BoardRequest request) {
        log.info(request.toString());
        Board savedBoard = boardService.save(request);
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

    @GetMapping("/api/boards/{id}")
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
}
