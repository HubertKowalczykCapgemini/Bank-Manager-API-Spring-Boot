package com.capgemni.bank_manager_api.response;

import com.capgemni.bank_manager_api.entity.Authority;

import java.math.BigDecimal;
import java.util.List;

public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private BigDecimal balance;
    private List<Authority> authorities;

    public UserResponse(Long id, String fullName, String email, BigDecimal balance, List<Authority> authorities) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.balance = balance;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
