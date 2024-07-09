package org.example.lionproj2.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.lionproj2.domain.User;
import org.example.lionproj2.model.LoginRequestDTO;
import org.example.lionproj2.model.UserDTO;
import org.example.lionproj2.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/slog")
public class UserController {
    private final UserService userService;


    @PostMapping("/userreg")
    public ModelAndView registerUser(@ModelAttribute UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return new ModelAndView("redirect:/welcome");
        } catch (IllegalArgumentException e) {
            ModelAndView mav = new ModelAndView("user/userreg-error");
            mav.addObject("errorMessage", e.getMessage());
            return mav;
        }
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginRequestDTO loginRequestDTO, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        try {
            User user = userService.authenticate(loginRequestDTO);

            if (user != null) {
                Cookie cookie = new Cookie("auth", user.getUsername());
                response.addCookie(cookie);
                mav.setViewName("redirect:/@" + user.getUsername());
            } else {
                mav.setViewName("loginform");
                mav.addObject("errorMessage", "Invalid username or password");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            mav.setViewName("loginform");
            mav.addObject("errorMessage", "An error occurred during login");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return mav;
    }



    @GetMapping("")
    public String main() {
        return "home/main";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "user/userreg-success";
    }

    @GetMapping("/loginform")
    public String loginForm() {
        return "user/loginform";
    }

    @GetMapping("/userregform")
    public String userRegForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "user/userregform";
    }

    @GetMapping("/@{username}")
    public ModelAndView userProfile(@PathVariable String username) {
        ModelAndView mav = new ModelAndView();
        try {
            User user = userService.matchUsername(username);
            if (user != null) {
                mav.addObject("user", user);
                mav.setViewName("user/blog");
            } else {
                mav.setViewName("error/404");
            }
        } catch (Exception e) {
            mav.setViewName("error/500");
            mav.addObject("errorMessage", e.getMessage());
            // 로그에 예외 정보 출력
            e.printStackTrace();
        }
        return mav;
    }


}