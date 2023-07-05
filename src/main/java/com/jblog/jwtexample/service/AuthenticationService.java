package com.jblog.jwtexample.service;

import com.jblog.jwtexample.error.GeneralBusinessException;
import com.jblog.jwtexample.model.User;
import com.jblog.jwtexample.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public String jwtLogin(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        log.info("Successful login for email {}", email);
        return jwtService.generateToken(email);
    }

    @Transactional
    public String register(String email, String password) {
        if(userService.checkUserExists(email)) {
            throw new GeneralBusinessException("Email is in use");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        log.info("Registering user of email {}", email);
        userService.saveUser(user);
        return jwtService.generateToken(email);
    }
}
