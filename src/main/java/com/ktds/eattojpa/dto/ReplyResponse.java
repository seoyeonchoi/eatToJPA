package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.domain.Reply;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReplyResponse {
    private String id;
    private String reply;
    private LocalDate replyDate;
    private String parentId;
    private String boardId;
    private String memberId;

    private String memberName;

    @Builder
    public ReplyResponse(Reply reply, String memberName) {
        this.id = reply.getId();
        this.reply = reply.getReply();
        this.replyDate = reply.getReplyDate();
        this.parentId = reply.getParentId();
        this.boardId = reply.getBoardId();
        this.memberId = reply.getMemberId();
        this.memberName = memberName;
    }
}
