package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.dto.BoardListViewResponse;
import com.ktds.eattojpa.dto.BoardViewResponse;
import com.ktds.eattojpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ExampleBoardViewController {
    private final BoardService boardService;

    @GetMapping("/articles")
    public String getBoards(Model model){
        List<BoardListViewResponse> boards = boardService.findAll().stream()
                .map(BoardListViewResponse::new)
                .toList();
        model.addAttribute("articles", boards); // 블로그 글 리스트 저장
        return "example/articleList";
    }

    @GetMapping("/articles/{id}")
    public String getBoard(@PathVariable String id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("article", new BoardViewResponse(board));
        return "example/article";
    }

    @GetMapping("/new-article")
    // 1. id 키를 가진 쿼리 파라미터 값을 id 변수에 매핑(id는 없을 수도 있음)
    public String newArticle(@RequestParam(required = false) String id, Model model) {
        if (id == null) {
            // 2. id가 없으면 생성
            model.addAttribute("article", new BoardViewResponse());
        } else {
            Board board = boardService.findById(id);
            model.addAttribute("article", new BoardViewResponse(board));
        }

        return "example/newArticle";
    }
}
