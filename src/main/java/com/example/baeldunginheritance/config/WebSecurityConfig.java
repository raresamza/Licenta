package com.example.baeldunginheritance.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@EnableWebSecurity
@Component
@RequiredArgsConstructor
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URLS = {
            "/user",
            "/user/add/bio",
            "/user/{email}",
            "/course/get-by-code/{courseCode}",
            "/course/get-by-code/**",
            "/course/get-by-code/**/",
            "/user/updateEmail",
            "/course",
            "/course/{email}",
            "/course/add",
            "/user/add/course",
            "/course/add/lecture",
            /*"/auth/authenticate",*/
            "/user/student",
            "/user/register/student",
            "/user/register/studentP",
            "/user/register/teacher",
            "/user/photourl/{email}",
            "/user/register/teacherP",
            "/quiz/add",
            "/quiz/get/{quizCode}",
            "/quiz/get",
            "/quiz/get/problem/{quizCode}/{quizProblemCode}",
            "/quiz/add/students",
            "/quiz/add/problem",
            "/teacher",
            "/student",
            "/user/verifyRegistration",
            "/user/token",
            "/user/resendToken",
            "user/verifyResend",
            "/user/resetPassword",
            "/user/savePassword",
            "/user/changePassword",
            "/user/active/{email}",
            "/user/quiz-code/{email}",
            "164.90.185.151/user",
            "164.90.185.151/student",
//            "/auth/**",
            "course/add/comment",
            "course/add/solution",
            "course/add/tests",
            "course/upvote",
            "course/downvote",
            "course/get/downvotes/{coruseCode}/{lectureHeader}",
            "course/get/upvotes/{coruseCode}/{lectureHeader}",
            "course/add/inputs",
            "course/get/inputs/{courseCode}/{lectureHeader}",
            "course/get/tests/{courseCode}/{lectureHeader}",
            "course/comments/{coruseCode}/{lectureHeader}",
            "course/solutions/{coruseCode}/{lectureHeader}",
            "course/deleteall",
//            "user/add/bio",
            "https://licenta-production.up.railway.app/user",
//            "http://localhost:8080/auth/authenticate",
//            "https://licenta-production.up.railway.app/auth/authenticate",
            "https://licenta-production.up.railway.app/user/student",
            "https://licenta-production.up.railway.app/user/register/teacher",
            "https://licenta-production.up.railway.app/user/register/teacherP",
            "https://licenta-production.up.railway.app/user/register/student",
            "https://licenta-production.up.railway.app/user/register/studentP",
            "https://licenta-production.up.railway.app/*"

    };
    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/auth/**","/auth/authenticate","/user/register/studentP","/user/register/teacherP","/user/savePassword").permitAll()
                .requestMatchers(WHITE_LIST_URLS).authenticated()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/auth/logout") // means everytime u get this request just use thislogiut handler.
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext());

        return http.build();
    }

}
