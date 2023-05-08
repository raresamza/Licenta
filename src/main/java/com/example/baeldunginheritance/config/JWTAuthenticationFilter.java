package com.example.baeldunginheritance.config;

import com.example.baeldunginheritance.repository.JWTTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService JWTService;
    private final UserDetailsService userDetailsService;
    private final JWTTokenRepository jwtTokenRepository;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader= request.getHeader("Authorization");
        final String JWTToken;
        final String email;
        if(authHeader==null || !authHeader.startsWith("Bearer ")) {
            System.out.println("auth header==nu;ll sau nu incepe cu Bearer+spatiu");
            filterChain.doFilter(request,response);
            return;
        }
        JWTToken=authHeader.substring(7);
        email= JWTService.extractEmail(JWTToken);
        System.out.println(email);
//        System.out.println(SecurityContextHolder.getContext().toString());
//        if(email != null && !(SecurityContextHolder.getContext().getAuthentication() ==null)) {
            UserDetails userDetails=this.userDetailsService.loadUserByUsername(email);
            var isTokenValid=jwtTokenRepository.findByToken(JWTToken).map(t->!t.isExpired() && !t.isRevoked()).orElse(false);
            System.out.println("should be true if not go digging: "+isTokenValid);
            System.out.println("email nu e null si functia lunga nu e null"+userDetails.toString());
            System.out.println(JWTService.isTokenValid(JWTToken,userDetails));
            if(JWTService.isTokenValid(JWTToken,userDetails) && isTokenValid) {
                System.out.println("token valid");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                System.out.println("Auth token: "+ authToken);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("Auth token dupa set: "+ authToken);


                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
//        }
        System.out.println("Henlo");
        filterChain.doFilter(request,response);
    }
}
