package com.ktds.eattojpa.dto;

import com.ktds.eattojpa.domain.Reply;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReplyRequest {
    private String reply;
}
