package com.jblog.jwtexample.security.jwt;

public interface JwtService {

    String extractUserName(String token);

    String generateToken(String username);

    boolean validateToken(String token);
}
