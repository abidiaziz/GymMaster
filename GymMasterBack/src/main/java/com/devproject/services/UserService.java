package com.devproject.services;

import com.devproject.dto.UserDTO;
import com.devproject.entities.Class;
import com.devproject.entities.Role;
import com.devproject.entities.User;
import com.devproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    public List<User> getUsersByRole(Role role) {
        return new ArrayList<>(userRepository.findByRole(role));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress(user.getAddress());
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        if (!user.getEnrolledClasses().isEmpty()){
            userDTO.setEnrolledClasses(user.getEnrolledClasses().stream().map(Class::getId).toList());
        }
        if (!user.getCoachedClasses().isEmpty()){
            userDTO.setCoachedClasses(user.getCoachedClasses().stream().map(Class::getId).toList());
        }
        userDTO.setName(user.getName());
        userDTO.setGender(user.getGender());
        if (user.getSubscription()!=null) {
            userDTO.setSubscriptionId(user.getSubscription().getId());
        }
        userDTO.setAddress(user.getAddress());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;

    }
}
