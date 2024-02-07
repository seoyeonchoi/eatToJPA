package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BoardRequest {
    private String title;
    private String content;
    private Integer minNum;
    private Integer maxNum;
    private LocalDate meetDate;
    private String memberId;
    private String restaurantName;
    private String restaurantKey;
    private String meetName;
    private String meetKey;

    public Board toEntity() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        return Board.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .content(content)
                .regDate(now)
                .updateDate(now)
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
