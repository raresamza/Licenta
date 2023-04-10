package com.example.baeldunginheritance.controller;

import com.example.baeldunginheritance.DTO.AuthenticationRequest;
import com.example.baeldunginheritance.DTO.UserCreationDTO;
import com.example.baeldunginheritance.collection.Mapper;
import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.config.JWTService;
import com.example.baeldunginheritance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final Mapper mapper;
    private final JWTService jwtService;
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()

                )
        );

        var userForToken=userRepository.findUserByEmail(request.getEmail());
        if(userForToken==null) {
            throw new UsernameNotFoundException("user not found");
        }
        var jwtToken=jwtService.generateToken(userForToken);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
