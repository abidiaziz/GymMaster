package com.devproject.dto;

import com.devproject.entities.Subscription;
import com.devproject.entities.User;
import jakarta.persistence.*;

import java.time.LocalDate;

public class SubscriptionDTO {
    private Long id;
    private Long userId;
    private Subscription.SubscriptionStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String planType;
    private boolean autoRenew;

    public enum SubscriptionStatus {
        ACTIVE, CANCELLED, EXPIRED
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Subscription.SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(Subscription.SubscriptionStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public boolean isAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(boolean autoRenew) {
        this.autoRenew = autoRenew;
    }
}
