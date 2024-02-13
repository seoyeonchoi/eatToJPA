package com.ktds.eattojpa.controller;

import com.ktds.eattojpa.domain.Board;
import com.ktds.eattojpa.domain.User;
import com.ktds.eattojpa.dto.AddUserRequest;
import com.ktds.eattojpa.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserApiController {

    private final UserService userService;

    @PostMapping("api/signup")
    public ResponseEntity<User> signup(@RequestBody AddUserRequest request) {
        System.out.println("signup 실행 중: " + request.toString());
        String id = userService.save(request);
        if (id == null) {
            log.info("signup process failed");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null);
        }
        log.info("signup process success");
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findById(id));
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/index";
    }

    @PutMapping("api/user")
    public ResponseEntity<String> changeUserInfo(@RequestBody AddUserRequest request) {
        if (userService.update(request)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("비밀번호 수정 성공");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("비밀번호 수정 실패");
    }

    @GetMapping("api/check-email/{email}")
    public ResponseEntity<String> checkEmail(@PathVariable(required = true) String email) {
        System.out.println("check-email" + email + userService.checkEmail(email));
        if (userService.checkEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("이미 가입된 이메일입니다.");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("가입 가능한 이메일 입니다.");
    }
}
