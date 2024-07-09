package org.example.lionproj2.controller;

import lombok.RequiredArgsConstructor;
import org.example.lionproj2.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
// 회원가입 도중 내가 아이디 혹은 이메일 겹치는게 있는지 확인하기 위해 작성된 컨트롤러이다.
// 반환 값으로는 "있다 / 없다" 만 알 수 있어야 보안에 안전하기 때문에
// service 에서 return 은 반드시 참 혹은 거짓 이여야 할 것 이다.
// 만약 return 으로 받은 것이 true 라면 "DB에 있음"
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("/check-username")
    public Map<String, Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userService.isUsernameTaken(username);
        return Collections.singletonMap("exists", exists);
    }

    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userService.isEmailTaken(email);
        return Collections.singletonMap("exists", exists);
    }







}
