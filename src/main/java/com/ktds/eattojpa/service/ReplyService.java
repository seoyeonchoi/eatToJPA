package com.ktds.eattojpa.service;

import com.ktds.eattojpa.domain.Reply;
import com.ktds.eattojpa.dto.ReplyRequest;
import com.ktds.eattojpa.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReplyService {
    private final ReplyRepository replyRepository;

    public List<Reply> findByBoardId(String boardId) {
        return replyRepository.findByBoardIdOrderByParentId(boardId);
    }

    public Reply saveReply(ReplyRequest request, String boardId, String loginId) {
        Reply reply = new Reply(request.getReply(), boardId, loginId);
        return replyRepository.save(reply);
    }

    public Reply saveReReply(ReplyRequest request, String boardId, String loginId, String replyId) {
        Reply reply = new Reply(request.getReply(), boardId, loginId);
        reply.setParentId(replyId);
        return replyRepository.save(reply);
    }

    public void deleteReply(String replyId, String loginId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply 객체가 존재하지 않습니다."));
        if (reply.getMemberId() == loginId) {
            replyRepository.deleteById(replyId);
        }

    }

    public Long countReplyByBoardId(String boardId) {
        return replyRepository.countReplyByBoardId(boardId);
    }
}
