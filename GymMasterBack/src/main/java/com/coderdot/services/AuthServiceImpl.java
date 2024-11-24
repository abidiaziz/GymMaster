package com.coderdot.services;

import com.coderdot.dto.SignupRequest;
import com.coderdot.entities.Role;
import com.coderdot.entities.User;
import com.coderdot.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User createUser(SignupRequest signupRequest) {
        //Check if customer already exist
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return null;
        }

        User user = new User();
        BeanUtils.copyProperties(signupRequest, user);
        user.setRole(Role.ADMIN);
        //Hash the password before saving
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(hashPassword);
        User createdUser = userRepository.save(user);
        user.setId(createdUser.getId());
        return user;
    }
}
