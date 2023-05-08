package com.example.baeldunginheritance.controller;

import com.example.baeldunginheritance.DTO.AuthenticationRequest;
import com.example.baeldunginheritance.DTO.UserCreationDTO;
import com.example.baeldunginheritance.collection.JWTToken;
import com.example.baeldunginheritance.collection.Mapper;
import com.example.baeldunginheritance.collection.TokenType;
import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.config.JWTService;
import com.example.baeldunginheritance.repository.JWTTokenRepository;
import com.example.baeldunginheritance.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final Mapper mapper;
    private final JWTService JWTService;
    private final JWTTokenRepository jwtTokenRepository;



    private void saveTokenForUser(User userForToken, String jwtToken) {
        var token= JWTToken.builder()
                .user(userForToken)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        jwtTokenRepository.save(token);
    }


    public void revokeAllUserTokens(User user) {
        var validUserTokens = jwtTokenRepository.findAllByUserIdAndExpiredAndRevoked(user.getId(), false,false);
        System.out.println("Userii cu tokenuri ok sunt:"+validUserTokens.toString());
        System.out.println("Daca nu sunt s ar putea sa fie nevoie sa le dai valori custom cand ii creezi(ar trebui sa " +
                "fie fals) si ar trebui sa te uiti iar la video ca sa vezi ce hack/fix gasesti daca nu le dai token" +
                " cand ii inregistrezi");
        if(validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });

        jwtTokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()

                )
        );

        var userForToken = userRepository.findUserByEmail(request.getEmail());
        if (userForToken == null) {
            throw new UsernameNotFoundException("user not found");
        }
        var jwtToken = JWTService.generateToken(userForToken);
        var refreshToken = JWTService.generateRefreshToken(userForToken);
        revokeAllUserTokens(userForToken);
        saveTokenForUser(userForToken, jwtToken);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }



    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String JWTRefreshToken;
        final String email;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("auth header==nu;ll sau nu incepe cu Bearer+spatiu");
            return;
        }
        JWTRefreshToken = authHeader.substring(7);
        email = JWTService.extractEmail(JWTRefreshToken);
        System.out.println(email);
//        System.out.println(SecurityContextHolder.getContext().toString());
        if (email != null) {
            var userDetails = this.userRepository.findUserByEmail(email);
            if (JWTService.isTokenValid(JWTRefreshToken, userDetails)) {
                var accessToken=JWTService.generateToken(userDetails);
                revokeAllUserTokens(userDetails);
                saveTokenForUser(userDetails, accessToken);
                var authResponse=AuthenticationResponse.builder()
                        .token(accessToken)
                        .refreshToken(JWTRefreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
//        }
    }
}
