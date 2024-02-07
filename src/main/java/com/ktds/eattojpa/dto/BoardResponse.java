package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Board;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Getter
public class BoardResponse {
    private final String id;
    private final String title;
    private final String content;
    private final Timestamp regDate;
    private final Timestamp updateDate;
    private final Integer minNum;
    private final Integer maxNum;
    private final Timestamp meetDate;
    private final String memberId;
    private final String restaurantName;
    private final String restaurantKey;
    private final String meetName;
    private final String meetKey;

    public BoardResponse(Board board) {
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

    }

}
