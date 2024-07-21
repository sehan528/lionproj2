package org.example.lionproj2.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.lionproj2.entity.User;
import org.example.lionproj2.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/signup", "/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/vlog.io/setting/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/setting", "/write").authenticated()
                        .requestMatchers(HttpMethod.POST, "/editAboutme", "/uploadProfile", "/editPost").authenticated()
                        .requestMatchers("/api/v1/user/about").authenticated()
                        .requestMatchers("/api/v1/posts/*/like").authenticated()
                        .requestMatchers("/api/v1/posts/*/edit").authenticated()
                        .requestMatchers("/vlog.io/@**/**").authenticated()  // 추가된 부분
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", false)
                        .successHandler(authenticationSuccessHandler())
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe  // 이 부분 추가
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/v1/user/about", "/api/v1/posts/*/like", "/api/v1/posts/*/edit")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)  // 추가된 부분
                )
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {
                HttpSession session = request.getSession();
                String userId = authentication.getName();
                User user = userRepository.findByUserId(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
                session.setAttribute("userId", user.getId());

                SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
                if (savedRequest != null) {
                    response.sendRedirect(savedRequest.getRedirectUrl());
                } else {
                    response.sendRedirect("/");
                }
            }
        };
    }
}