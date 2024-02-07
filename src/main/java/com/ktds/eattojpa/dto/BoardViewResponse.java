package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardViewResponse {
    private String id;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private Integer minNum;
    private Integer maxNum;
    private LocalDate meetDate;
    private String memberId;
    private String restaurantName;
    private String restaurantKey;
    private String meetName;
    private String meetKey;

    public BoardViewResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.regDate = board.getRegDate();
        this.updateDate = board.getUpdateDate();
        this.minNum = board.getMinNum();;
        this.maxNum = board.getMaxNum();
        this.meetDate = board.getMeetDate();
        this.memberId = board.getMemberId();
        this.restaurantName = board.getRestaurantName();
        this.restaurantKey = board.getRestaurantKey();
        this.meetName = board.getMeetName();
        this.meetKey = board.getMeetKey();
    }
}
