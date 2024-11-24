package com.coderdot.services;

import com.coderdot.entities.Role;
import com.coderdot.entities.Subscription;
import com.coderdot.entities.User;
import com.coderdot.repositories.SubscriptionRepository;
import com.coderdot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Subscription createSubscription(Long userId, String planType, int months) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only customers can have subscriptions");
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(months));
        subscription.setPlanType(planType);
        subscription.setAutoRenew(true);

        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public Subscription updateSubscriptionStatus(Long userId, Subscription.SubscriptionStatus status) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if (subscription == null) {
            throw new RuntimeException("Subscription not found for user");
        }
        subscription.setStatus(status);
        return subscriptionRepository.save(subscription);
    }

    @Transactional
    public void deleteSubscription(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if (subscription == null) {
            throw new RuntimeException("Subscription not found for user");
        }
        subscriptionRepository.delete(subscription);
    }

    public Subscription getSubscription(Long userId) {
        return subscriptionRepository.findByUserId(userId);
    }
}

