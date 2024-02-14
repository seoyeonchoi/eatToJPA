package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class BoardResponse {
    private final String id;
    private final String title;
    private final String content;
    private final LocalDateTime regDate;
    private final LocalDateTime updateDate;
    private final Integer minNum;
    private final Integer maxNum;
    private final LocalDate meetDate;
    private final String memberId;
    private final String restaurantName;
    private final String restaurantKey;
    private final String meetName;
    private final String meetKey;
    private final Integer completed;
    private final Integer currentMember;
    private final String writer;

    public BoardResponse(Board board, String writer) {
        this.title = board.getTitle();
        this.id = board.getId();
        this.content = board.getContent();
        this.regDate = board.getRegDate();
        this.updateDate = board.getUpdateDate();
        this.minNum = board.getMinNum();
        this.maxNum = board.getMaxNum();
        this.meetDate = board.getMeetDate();
        this.memberId = board.getMemberId();
        this.restaurantName = board.getRestaurantName();
        this.restaurantKey = board.getRestaurantKey();
        this.meetName = board.getMeetName();
        this.meetKey = board.getMeetKey();
        this.completed = board.getCompleted();
        this.currentMember = board.getCurrentMember();
        this.writer = writer;
    }

}
