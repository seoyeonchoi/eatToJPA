package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Board;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class BoardResponseForCalendar {
    private final String id;
    private final String title;
    private final LocalDate start;
    private final LocalDate end;
    private final String textColor;
    private final Integer currentMember;
    private final Integer completed;

    public BoardResponseForCalendar(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.start = board.getMeetDate();
        this.end = board.getMeetDate();
        this.textColor = "red";
        this.currentMember = board.getCurrentMember();
        this.completed = board.getCompleted();
    }
}
