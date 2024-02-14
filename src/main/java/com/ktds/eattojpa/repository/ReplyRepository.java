package com.ktds.eattojpa.repository;

import com.ktds.eattojpa.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, String> {

    List<Reply> findByBoardIdOrderByParentId(String boardId);

    @Query("SELECT COUNT(r) FROM Reply r WHERE r.boardId = :boardId")
    Long countReplyByBoardId(String boardId);
}
