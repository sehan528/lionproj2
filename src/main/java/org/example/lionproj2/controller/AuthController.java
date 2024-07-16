package org.example.lionproj2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lionproj2.dto.LoginDTO;
import org.example.lionproj2.dto.SignupDTO;
import org.example.lionproj2.entity.User;
import org.example.lionproj2.exception.DuplicateUserException;
import org.example.lionproj2.exception.InvalidCredentialsException;
import org.example.lionproj2.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupDto", new SignupDTO());

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupDto") SignupDTO signupDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }

        try {
            authService.signup(signupDto);
            return "redirect:/login";
        } catch (DuplicateUserException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDTO loginDto, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }
        try {
            User user = authService.login(loginDto);

            session.setAttribute("userId", user.getId());
            log.info("해당 유저 로그인 성공함. UserID: {}", user.getId());

            return "redirect:/";
        } catch (InvalidCredentialsException e) {
            result.rejectValue("userId", "error.loginDto", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId");
        log.info("유저 logout.");

        return "redirect:/";
    }
}
