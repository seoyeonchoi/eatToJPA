package com.ktds.eattojpa.repository;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.dto.BoardResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, String> {
    List<Board> findByMeetDate(LocalDate meetDate);
    boolean existsByMeetDateAndMemberId(LocalDate meetDate, String loginId);
}
