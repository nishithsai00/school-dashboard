package com.school.dashboard.config;

import com.school.dashboard.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    private final CustomSuccessHandler successHandler;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomSuccessHandler successHandler,
                          CustomUserDetailsService userDetailsService) {
        this.successHandler = successHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/login",
                            "/manifest.json",
                            "/sw.js",
                            "/offline.html",
                            "/css/**",
                            "/js/**",
                            "/img/**"
                    ).permitAll()

                    .requestMatchers("/teacher/**").hasRole("TEACHER")
                    .requestMatchers("/student/**").hasRole("STUDENT")
                    .anyRequest().authenticated()
            )

            .formLogin(login -> login
                    .loginPage("/login")
                    .successHandler(successHandler)
                    .permitAll()
            )

            /* ⭐ THE REAL FIX ⭐ */
            .rememberMe(remember -> remember
                    .key("school-portal-remember-me-key-2026")
                    .tokenValiditySeconds(60 * 60 * 24 * 30) // 30 days
                    .userDetailsService(userDetailsService)
                    .rememberMeCookieName("remember-me")
                    .alwaysRemember(true)
            )

            .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout")
                    .deleteCookies("JSESSIONID", "remember-me")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

