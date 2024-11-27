package com.devproject.dto;

import com.devproject.entities.Role;
import jakarta.persistence.*;
import java.util.List;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private Role role;
    private Long subscriptionId;
    private List<Long> coachedClasses;
    private List<Long> enrolledClasses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscription) {
        this.subscriptionId = subscription;
    }

    public List<Long> getCoachedClasses() {
        return coachedClasses;
    }

    public void setCoachedClasses(List<Long> coachedClasses) {
        this.coachedClasses = coachedClasses;
    }

    public List<Long> getEnrolledClasses() {
        return enrolledClasses;
    }

    public void setEnrolledClasses(List<Long> enrolledClasses) {
        this.enrolledClasses = enrolledClasses;
    }


}

