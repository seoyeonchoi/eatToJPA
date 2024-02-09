package com.ktds.eattojpa.service;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.dto.BoardRequest;
import com.ktds.eattojpa.dto.BoardResponse;
import com.ktds.eattojpa.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    // 게사판 글 추가 메서드
    public Board save(BoardRequest request) {
        return boardRepository.save(request.toEntity());
    }

    // 게시판 글 목록 조회 메서드
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public List<Board> findAllforCalendar() {
        return boardRepository.findAll();
    }

    // 게시판 글 하나 조회 메서드
    public Board findById(String id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 게시판 글 삭제 메서드
    public void delete(String id) {
        boardRepository.deleteById(id);
    }

    // 게시글 수정 메서드
    @Transactional
    public Board update(String id, BoardRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        board.update(request.getTitle(), request.getContent(), request.getMinNum(), request.getMaxNum(), request.getMeetDate(), request.getMemberId(),
                request.getRestaurantName(), request.getRestaurantKey(), request.getMeetName(), request.getMeetKey());

        return board;
    }

    public List<Board> findByDate(LocalDate meetDate) {
        return boardRepository.findByMeetDate(meetDate);
    }
}
