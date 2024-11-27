package com.devproject.services;

import com.devproject.dto.CreateSubscriptionDTO;
import com.devproject.dto.SubscriptionDTO;
import com.devproject.entities.Role;
import com.devproject.entities.Subscription;
import com.devproject.entities.User;
import com.devproject.repositories.SubscriptionRepository;
import com.devproject.repositories.UserRepository;
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
    public Subscription createSubscription(CreateSubscriptionDTO createSubscriptionDTO) {
        User user = userRepository.findById(createSubscriptionDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Subscription byUserId = subscriptionRepository.findByUserId(createSubscriptionDTO.getUserId());
        if (byUserId != null){
            throw new RuntimeException("already has subscription");
        }
        if (user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only customers can have subscriptions");
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(createSubscriptionDTO.getMonths()));
        subscription.setPlanType(createSubscriptionDTO.getPlanType());
        subscription.setAutoRenew(true);

        subscriptionRepository.save(subscription);
        return subscriptionRepository.findByUserId(createSubscriptionDTO.getUserId());

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
     public SubscriptionDTO toSubscriptionDTO(Subscription subscription){
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setId(subscription.getId());
        subscriptionDTO.setPlanType(subscription.getPlanType());
        subscriptionDTO.setUserId(subscription.getUser().getId());
        subscriptionDTO.setAutoRenew(subscription.isAutoRenew());
        subscriptionDTO.setEndDate(subscription.getEndDate());
        subscriptionDTO.setStartDate(subscription.getStartDate());
        subscriptionDTO.setStatus(subscription.getStatus());
        return subscriptionDTO;

     }

}

