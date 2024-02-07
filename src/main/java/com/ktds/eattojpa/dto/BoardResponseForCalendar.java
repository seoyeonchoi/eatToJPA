package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Board;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class BoardResponseForCalendar {
    private final String id;
    private final String title;
    private final Timestamp start;
    private final Timestamp end;
    private final String textColor;
    private final Integer status;

    public BoardResponseForCalendar(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.start = board.getMeetDate();
        this.end = board.getMeetDate();
        this.textColor = "red";
        this.status = "red";


    }
}
