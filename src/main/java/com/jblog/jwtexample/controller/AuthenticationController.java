package com.jblog.jwtexample.controller;

import com.jblog.jwtexample.controller.dto.JwtLoginRequest;
import com.jblog.jwtexample.controller.dto.JwtRegisterRequest;
import com.jblog.jwtexample.controller.dto.JwtTokenResponse;
import com.jblog.jwtexample.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtTokenResponse authenticate(@RequestBody JwtLoginRequest jwtLoginRequest) {
        log.info("Handle authenticate request");
        String token = authenticationService.jwtLogin(jwtLoginRequest.email(), jwtLoginRequest.password());
        return new JwtTokenResponse(token);
    }

    @PostMapping("/register")
    public JwtTokenResponse register(@RequestBody JwtRegisterRequest registerRequest) {
        log.info("Handle register request");
        String token = authenticationService.register(registerRequest.email(), registerRequest.password());
        return new JwtTokenResponse(token);
    }
}
