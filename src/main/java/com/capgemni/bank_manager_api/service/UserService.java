package com.capgemni.bank_manager_api.service;

import com.capgemni.bank_manager_api.response.UserResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserResponse getUserInfo();
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    void deleteUserById( Long id);
}
