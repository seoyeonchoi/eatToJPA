package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardRequest {
    private String title;
    private String content;
    private Integer minNum;
    private Integer maxNum;
    private Timestamp meetDate;
    private String memberId;
    private String restaurantName;
    private String restaurantKey;
    private String meetName;
    private String meetKey;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public Board toEntity() {
        return Board.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .content(content)
                .regDate(new Timestamp(System.currentTimeMillis()))
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .minNum(minNum)
                .maxNum(maxNum)
                .meetDate(meetDate)
                .memberId(memberId)
                .restaurantName(restaurantName)
                .restaurantKey(restaurantKey)
                .meetName(meetName)
                .meetKey(meetKey)
                .build();
    }
}
