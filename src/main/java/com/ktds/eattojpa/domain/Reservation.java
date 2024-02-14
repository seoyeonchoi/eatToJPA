package com.ktds.eattojpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "boardId", nullable = false)
    private String boardId;
    @Column(name="memberId", nullable = false)
    private String memberId;

    @Builder
    public Reservation(String boardId, String memberId) {
        this.id = UUID.randomUUID().toString();
        this.memberId = memberId;
        this.boardId = boardId;
    }
}
