package com.jblog.jwtexample.service;

import com.jblog.jwtexample.error.GeneralBusinessException;
import com.jblog.jwtexample.model.User;
import com.jblog.jwtexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new GeneralBusinessException("No user found"));
    }

    public boolean checkUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
