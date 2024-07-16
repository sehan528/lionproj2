package org.example.lionproj2.util;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    public static void setUserId(HttpSession session, Long userId) {
        session.setAttribute("userId", userId);
    }

    public static Long getUserId(HttpSession session) {
        return (Long) session.getAttribute("userId");
    }

    public static void setUsername(HttpSession session, String username) {
        session.setAttribute("username", username);
    }

    public static String getUsername(HttpSession session) {
        return (String) session.getAttribute("username");
    }

    public static void clearSession(HttpSession session) {
        session.invalidate();
    }
}