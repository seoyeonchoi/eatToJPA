package com.ktds.eattojpa.domain;

import com.ktds.eattojpa.dto.BoardRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name="title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "regDate")
    private LocalDateTime regDate;
    @Column(name = "updateDate")
    private LocalDateTime updateDate;
    @Column(name = "minNum", nullable = false)
    private Integer minNum;
    @Column(name = "maxNum", nullable = false)
    private Integer maxNum;
    @Column(name = "meetDate", nullable = false)
    private LocalDate meetDate;
    @Column(name = "memberId", nullable = false)
    private String memberId;
    @Column(name = "writer", nullable = false)
    private String writer;
    @Column(name = "resName", nullable = false)
    private String restaurantName;
    @Column(name = "resKey", nullable = false)
    private String restaurantKey;
    @Column(name = "meetName", nullable = false)
    private String meetName;
    @Column(name = "currentMember", nullable = false)
    private Integer currentMember;
    @Column(name = "completed", nullable = false)
    private Integer completed;
    @Column(name = "meetKey", nullable = false)
    private String meetKey;


    @Builder
    public Board(String id, String title, String content, LocalDateTime regDate, LocalDateTime updateDate, Integer minNum, Integer maxNum, LocalDate meetDate, String memberId, String restaurantName, String restaurantKey, String meetName, String meetKey, Integer currentMember, Integer completed, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.minNum = minNum;
        this.maxNum = maxNum;
        this.meetDate = meetDate;
        this.memberId = memberId;
        this.restaurantName = restaurantName;
        this.restaurantKey = restaurantKey;
        this.meetName = meetName;
        this.meetKey = meetKey;
        this.currentMember = currentMember;
        this.completed = completed;
        this.writer = writer;
    }

    public void update(String title, String content, Integer minNum, Integer maxNum, LocalDate meetDate, String restaurantName, String restaurantKey, String meetName, String meetKey) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.title = title;
        this.content = content;
        this.updateDate = now;
        this.minNum = minNum;
        this.maxNum = maxNum;
        this.meetDate = meetDate;
        this.restaurantName = restaurantName;
        this.restaurantKey = restaurantKey;
        this.meetName = meetName;
        this.meetKey = meetKey;
    }


}
