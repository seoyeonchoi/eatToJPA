package com.ktds.eattojpa.service;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.domain.User;
import com.ktds.eattojpa.dto.BoardRequest;
import com.ktds.eattojpa.dto.BoardResponse;
import com.ktds.eattojpa.repository.BoardRepository;
import com.ktds.eattojpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // 게사판 글 추가 메서드
    public Board save(BoardRequest request, String memberId) {
        return boardRepository.save(request.toEntity(memberId));
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

        board.update(request.getTitle(), request.getContent(), request.getMinNum(), request.getMaxNum(), request.getMeetDate(),
                request.getRestaurantName(), request.getRestaurantKey(), request.getMeetName(), request.getMeetKey());

        return board;
    }

    public List<Board> findByDate(LocalDate meetDate) {
        return boardRepository.findByMeetDate(meetDate);
    }

    public boolean existsByEmailAndMeetDate(LocalDate meetDate, String loginId) {
        return boardRepository.existsByMeetDateAndMemberId(meetDate, loginId);
    }

    public Board close(String id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        board.setCompleted(1);
        boardRepository.save(board);
        return board;
    }

    public List<Board> findByMemberId(String memberId) {
        return boardRepository.findByMemberId(memberId);
    }
}
