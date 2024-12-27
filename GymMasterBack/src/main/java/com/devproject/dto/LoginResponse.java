package com.devproject.dto;

import org.springframework.security.core.userdetails.UserDetails;

public record LoginResponse(String jwt, UserDetails userDetails) {
}
