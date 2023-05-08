package com.example.baeldunginheritance.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRET_KEY="743777217A25432A462D4A614E645267556B586E3272357538782F413F442847";
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String  extractEmail(String jwtToken) {
        return extractClaim(jwtToken,Claims::getSubject);
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        final Claims claims=extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    public boolean isTokenValid(String JTWToken, UserDetails userDetails) {
        final String email=extractEmail(JTWToken);
        System.out.println("verific daca numele de uti bate cu mailu:"+email.equals(userDetails.getUsername()));
        return (email.equals(userDetails.getUsername())) && isTokenExpied(JTWToken);
    }

    private boolean isTokenExpied(String jtwToken) {
        System.out.println("expiration date: "+extractExpiration(jtwToken));
        System.out.println("New date: "+new Date().toString());
//        return extractExpiration(jtwToken).before(new Date());
        return new Date().before(extractExpiration(jtwToken));
    }

    private Date extractExpiration(String jtwToken) {
        return extractClaim(jtwToken,Claims::getExpiration);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims,userDetails,jwtExpiration);


    }
    public String generateRefreshToken( UserDetails userDetails) {
        return buildToken(new HashMap<>(),userDetails,refreshExpiration);


    }

    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        System.out.println("Claims:"+extraClaims.toString());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .claim("test","t")
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyByte= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
