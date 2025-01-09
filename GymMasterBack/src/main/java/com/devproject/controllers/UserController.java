package com.devproject.controllers;

import com.devproject.dto.UserDTO;
import com.devproject.entities.Role;
import com.devproject.entities.User;
import com.devproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers().stream().map(userService::convertToDTO).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@RequestParam Role role) {
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users.stream().map(userService::convertToDTO).toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userService.convertToDTO(user);
        return ResponseEntity.ok(userDTO);
    }
}