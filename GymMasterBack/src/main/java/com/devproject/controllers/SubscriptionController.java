package com.devproject.controllers;

import com.devproject.dto.CreateSubscriptionDTO;
import com.devproject.dto.SubscriptionDTO;
import com.devproject.entities.Subscription;
import com.devproject.services.SubscriptionService;
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
    public ResponseEntity<SubscriptionDTO> createSubscription(@RequestBody CreateSubscriptionDTO createSubscriptionDTO) {
        Subscription subscription = subscriptionService.createSubscription(createSubscriptionDTO);
        SubscriptionDTO subscriptionDTO = subscriptionService.toSubscriptionDTO(subscription);
        return ResponseEntity.ok(subscriptionDTO);
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


