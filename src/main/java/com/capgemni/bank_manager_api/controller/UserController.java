package com.capgemni.bank_manager_api.controller;

import com.capgemni.bank_manager_api.response.UserResponse;
import com.capgemni.bank_manager_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User REST API Endpoints", description = "Operations related to info about current user")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User information", description = "Get current user information")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public UserResponse getUserInfo() {
        return userService.getUserInfo();
    }

    }


