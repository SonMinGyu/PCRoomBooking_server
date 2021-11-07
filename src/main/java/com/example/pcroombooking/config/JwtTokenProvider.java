package com.example.pcroombooking.config;

import com.example.pcroombooking.domain.Authority.Authority;
import com.example.pcroombooking.domain.User;
import com.example.pcroombooking.dto.UserLoginRequest;
import com.example.pcroombooking.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserService userService;

    private String secretKey = "SKforPCBoOkiNGBYSonMIngYu";
    private long tokenValidMilisecond = 1000L * 60 * 60; // 1000 ms = 1초, 1시간

    @PostConstruct
    void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createJwtToken(String email, Set<Authority> authorities) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("Authorities", authorities);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // 토큰 만료시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화
                .compact();
    }

    public String getTokenInHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validationToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        User user = userService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }



}
