package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Board;
import lombok.Getter;

@Getter
public class BoardListViewResponse {
    private final String id;
    private final String title;
    private final String content;

    public BoardListViewResponse(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
