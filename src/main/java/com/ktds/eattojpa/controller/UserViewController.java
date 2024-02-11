package com.ktds.eattojpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signUp";
    }
}
