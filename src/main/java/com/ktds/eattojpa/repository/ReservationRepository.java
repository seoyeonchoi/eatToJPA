package com.ktds.eattojpa.repository;

import com.ktds.eattojpa.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByBoardId(String boardId);

    boolean existsByBoardIdAndMemberId(String boardId, String loginId);

    void deleteByBoardIdAndMemberId(String boardId, String loginId);

    List<Reservation> findByMemberId(String memberId);
}
