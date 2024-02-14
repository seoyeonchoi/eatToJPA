package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.domain.Reservation;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HistoryResponse {
    private String boardId;
    private LocalDate meetDate;
    private String restaurantName;
    private Integer completed;

    @Builder
    public HistoryResponse(Board board) {
        this.boardId = board.getId();
        this.meetDate = board.getMeetDate();
        this.restaurantName = board.getRestaurantName();
        this.completed = board.getCompleted();
    }
}
