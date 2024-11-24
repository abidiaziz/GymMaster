package com.coderdot.controllers;

import com.coderdot.entities.Subscription;
import com.coderdot.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSubscription(@RequestParam Long userId, @RequestParam String planType, @RequestParam int months) {
        Subscription subscription = subscriptionService.createSubscription(userId, planType, months);
        return ResponseEntity.ok(subscription);
    }

    @PutMapping("/update-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSubscriptionStatus(@RequestParam Long userId, @RequestParam Subscription.SubscriptionStatus status) {
        Subscription subscription = subscriptionService.updateSubscriptionStatus(userId, status);
        return ResponseEntity.ok(subscription);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSubscription(@RequestParam Long userId) {
        subscriptionService.deleteSubscription(userId);
        return ResponseEntity.ok("Subscription deleted successfully");
    }

    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<?> getSubscriptionInfo(@RequestParam Long userId) {
        Subscription subscription = subscriptionService.getSubscription(userId);
        return ResponseEntity.ok(subscription);
    }
}


