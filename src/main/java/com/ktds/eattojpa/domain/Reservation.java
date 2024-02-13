package com.ktds.eattojpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name="memberId", nullable = false)
    private String memberId;
    @Column(name = "boardId", nullable = false)
    private String boardId;

    @Builder
    public Reservation(String id, String memberId, String boardId) {
        this.id = id;
        this.memberId = memberId;
        this.boardId = boardId;
    }
}
