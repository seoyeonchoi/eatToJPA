package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.domain.User;
import com.ktds.eattojpa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MyPageViewContoller {
    // private final MyPageService myPageService;
    private final UserService userService;

    @GetMapping("/mypage")
    public String getMypage(Authentication auth, Model model) {
        if(auth != null) {
            System.out.println("auth.getName: " + auth.getName());
            User loginUser = userService.findByEmail(auth.getName());
            if (loginUser != null) {
                System.out.println("userName: " + loginUser.getName());
                model.addAttribute("userName", loginUser.getName());
                model.addAttribute("userEmail", loginUser.getEmail());
            }
        }
        return "myPage";
    }
}
