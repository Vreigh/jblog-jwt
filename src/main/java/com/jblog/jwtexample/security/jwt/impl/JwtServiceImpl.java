package com.jblog.jwtexample.security.jwt.impl;

import com.jblog.jwtexample.security.jwt.JwtService;
import com.jblog.jwtexample.security.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final SecurityProperties securityProperties;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String generateToken(String username) {
        return generateToken(new HashMap<>(), username);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            extractClaim(token, Claims::getSubject);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder().setClaims(extraClaims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * securityProperties.getJwtDuration()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(securityProperties.getJwtSigningKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
