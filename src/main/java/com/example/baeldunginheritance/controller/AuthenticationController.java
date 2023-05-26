package com.example.baeldunginheritance.controller;


import com.example.baeldunginheritance.DTO.AuthenticationRequest;
import com.example.baeldunginheritance.DTO.UserCreationDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "https://frotnend.vercel.app/",
        "https://frotnend.vercel.app/auth",
        "https://frotnend.vercel.app/auth/authenticate",
        "https://frotnend.vercel.app/auth/refresh-token",
        "http://localhost:3000/"})
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/refresh-token")
    public void refreshToken (HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request,response);
    }
}
