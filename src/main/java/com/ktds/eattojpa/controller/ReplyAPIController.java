package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.domain.Reply;
import com.ktds.eattojpa.domain.User;
import com.ktds.eattojpa.dto.BoardRequest;
import com.ktds.eattojpa.dto.BoardResponse;
import com.ktds.eattojpa.dto.ReplyRequest;
import com.ktds.eattojpa.dto.ReplyResponse;
import com.ktds.eattojpa.service.ReplyService;
import com.ktds.eattojpa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReplyAPIController {
    private final ReplyService replyService;
    private final UserService userService;

    @GetMapping("/api/reply/{boardId}")
    public ResponseEntity<?> findByBoardId(@PathVariable String boardId, Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("auth 없음");
        }

        List<Reply> replyList = replyService.findByBoardId(boardId);
        List<ReplyResponse> responseList = new ArrayList<>();

        for (Reply reply : replyList) {
            User user = userService.findById(reply.getMemberId());
            responseList.add(new ReplyResponse(reply, user.getName()));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(responseList);
    }

    @PostMapping("/api/reply/{boardId}")
    public ResponseEntity<?> wrtieReply(@PathVariable String boardId, @RequestBody ReplyRequest request, Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("auth 없음");
        }
        User loginUser = userService.findByEmail(auth.getName());
        String loginId = loginUser.getId();

        Reply savedReply = replyService.saveReply(request, boardId, loginId);
        if (savedReply == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(savedReply);
    }

    @PostMapping("/api/re-reply/{boardId}/{replyId}")
    public ResponseEntity<?> wrtieReReply(@PathVariable String boardId, @PathVariable String replyId, @RequestBody ReplyRequest request, Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("auth 없음");
        }
        User loginUser = userService.findByEmail(auth.getName());
        String loginId = loginUser.getId();

        Reply savedReply = replyService.saveReReply(request, boardId, loginId, replyId);
        if (savedReply == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Error");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(savedReply);
    }

    @DeleteMapping("/api/reply/{replyId}")
    public ResponseEntity<?> deleteReply(@PathVariable String replyId, Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("auth 없음");
        }
        User loginUser = userService.findByEmail(auth.getName());
        String loginId = loginUser.getId();

        try {
            replyService.deleteReply(replyId, loginId);
            // 삭제 성공
            return ResponseEntity.status(HttpStatus.OK)
                    .body(replyId);
        } catch (Exception e) {
            // 삭제 실패
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e);
        }
    }

    @GetMapping("/api/reply/count/{boardId}")
    public ResponseEntity<?> countReplyByBoardId(@PathVariable String boardId) {
        try {
            Long count = replyService.countReplyByBoardId(boardId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e);
        }
    }
}
