package com.ktds.eattojpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "reply")
    private String reply;

    @Column(name = "replyDate")
    private LocalDate replyDate;

    @Column(name = "parentId")
    private String parentId;

    @Column(name = "boardId")
    private String boardId;

    @Column(name = "memberId")
    private String memberId;

    @Builder
    public Reply(String reply, String boardId, String memberId) {
        this.id = UUID.randomUUID().toString();
        this.reply = reply;
        this.replyDate = LocalDate.now();
        this.boardId = boardId;
        this.memberId = memberId;
        this.parentId = this.id;
    }
}
