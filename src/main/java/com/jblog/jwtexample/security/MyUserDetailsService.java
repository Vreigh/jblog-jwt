package com.jblog.jwtexample.security;

import com.jblog.jwtexample.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return UserPrincipal.create(userService.getUserByEmail(email));
        } catch (Exception e) {
            log.info("Exception thrown during user principal creation", e);
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
    }
}
