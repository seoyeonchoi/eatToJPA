package com.ktds.eattojpa.domain;

import com.ktds.eattojpa.dto.BoardRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;

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
    private Timestamp regDate;
    @Column(name = "updateDate")
    private Timestamp updateDate;
    @Column(name = "minNum", nullable = false)
    private Integer minNum;
    @Column(name = "maxNum", nullable = false)
    private Integer maxNum;
    @Column(name = "meetDate", nullable = false)
    private Timestamp meetDate;
    @Column(name = "memberId", nullable = false)
    private String memberId;
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
    public Board(String id, String title, String content, Timestamp regDate, Timestamp updateDate, Integer minNum, Integer maxNum, Timestamp meetDate, String memberId, String restaurantName, String restaurantKey, String meetName, String meetKey, Integer currentMember, Integer completed) {
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
    }

    public void update(String title, String content, Integer minNum, Integer maxNum, Timestamp meetDate, String memberId, String restaurantName, String restaurantKey, String meetName, String meetKey) {
        this.title = title;
        this.content = content;
        this.updateDate = new Timestamp(System.currentTimeMillis());
        this.minNum = minNum;
        this.maxNum = maxNum;
        this.meetDate = meetDate;
        this.memberId = memberId;
        this.restaurantName = restaurantName;
        this.restaurantKey = restaurantKey;
        this.meetName = meetName;
        this.meetKey = meetKey;
    }


}
