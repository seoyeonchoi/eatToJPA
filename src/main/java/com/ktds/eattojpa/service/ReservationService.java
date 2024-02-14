package com.ktds.eattojpa.service;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.domain.Reservation;
import com.ktds.eattojpa.repository.BoardRepository;
import com.ktds.eattojpa.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BoardRepository boardRepository;

    // 참석 등록
    public Reservation save(String boardId, String loginId) {
        Reservation savedReservation = new Reservation(boardId, loginId);

        // 보드 참석자 갱신 로직 (+1)
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + boardId));
        Integer currentMember = board.getCurrentMember();
        board.setCurrentMember(++currentMember);
        if (board.getCurrentMember() >= board.getMaxNum()) {
            board.setCompleted(1);
        }
        boardRepository.save(board);
        return reservationRepository.save(savedReservation);
    }

    // 전체 참석자 조회
    public List<Reservation> findByBoardId(String boardId) {
        return reservationRepository.findByBoardId(boardId);
    }

    // 내 참석 여부 조회
    public boolean findByBoardIdAndMemberId(String boardId, String loginId) {
        return reservationRepository.existsByBoardIdAndMemberId(boardId, loginId);
    }

    // 참석 취소
    @Transactional
    public void deleteByBoardIdAndMemberId(String boardId, String loginId) {
        reservationRepository.deleteByBoardIdAndMemberId(boardId, loginId);
        // 보드 참석자 갱신 로직 (-1)
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + boardId));
        Integer currentMember = board.getCurrentMember();
        board.setCurrentMember(--currentMember);
        if (board.getCurrentMember() <= board.getMaxNum()) {
            board.setCompleted(0);
        }
        boardRepository.save(board);
    }

    public List<Reservation> findByMemberId(String memberId) {
        return reservationRepository.findByMemberId(memberId);
    }
}
