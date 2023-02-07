package com.example.baeldunginheritance.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@EnableWebSecurity
@Component
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URLS={
            "/user",
            "/user/register/student",
            "/user/register/teacher",
            "/teacher",
            "/student",
            "/user/verifyRegistration",
            "/user/token",
            "/user/resendToken",
            "user/verifyResend",
            "/user/resetPassword",
            "/user/savePassword",
            "/user/changePassword",
            "164.90.185.151/user",
            "164.90.185.151/student",
            "https://licenta-production.up.railway.app/user",
            "https://licenta-production.up.railway.app/user/register/teacher",
            "https://licenta-production.up.railway.app/user/register/student"

    };

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(WHITE_LIST_URLS).permitAll();

        return http.build();
    }

}
