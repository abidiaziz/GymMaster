package com.coderdot.controllers;

import com.coderdot.dto.HelloResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/admin/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public HelloResponse adminHello() {
        return new HelloResponse("Hello from Admin API request.");
    }

    @GetMapping("/customer/hello")
    @PreAuthorize("hasRole('CUSTOMER')")
    public HelloResponse customerHello() {
        return new HelloResponse("Hello from Customer API request.");
    }

    @GetMapping("/coach/hello")
    @PreAuthorize("hasRole('COACH')")
    public HelloResponse coachHello() {
        return new HelloResponse("Hello from Coach API request.");
    }

}
