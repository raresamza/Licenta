package com.example.baeldunginheritance.config;

import com.example.baeldunginheritance.repository.JWTTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {


    private final JWTTokenRepository jwtTokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader= request.getHeader("Authorization");
        final String JWTToken;
        final String email;
        if(authHeader==null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        JWTToken=authHeader.substring(7);
        var storedToken=jwtTokenRepository.findByToken(JWTToken).orElse(null);
        if(storedToken!=null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            jwtTokenRepository.save(storedToken);
        }
    }
}
