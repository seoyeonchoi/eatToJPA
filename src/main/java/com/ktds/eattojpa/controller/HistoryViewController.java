package com.ktds.eattojpa.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HistoryViewController {
//    private final HistoryService historyService;

    @GetMapping("/history")
    public String getHistory() {
        return "history";
    }
}
