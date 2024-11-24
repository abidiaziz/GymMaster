package com.coderdot.controllers;

import com.coderdot.dto.SignupRequest;
import com.coderdot.entities.User;
import com.coderdot.services.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private final AuthServiceImpl authService;

    @Autowired
    public SignupController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
        User createdUser = authService.createUser(signupRequest);
        if (createdUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create customer");
        }
    }

}
