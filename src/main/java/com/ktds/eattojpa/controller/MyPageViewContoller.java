package com.ktds.eattojpa.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyPageViewContoller {
    // private final MyPageService myPageService;

    @GetMapping("/mypage")
    public String getMypage() {
        return "myPage";
    }
}
