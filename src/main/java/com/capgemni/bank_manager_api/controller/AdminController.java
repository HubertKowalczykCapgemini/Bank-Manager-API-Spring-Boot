package com.capgemni.bank_manager_api.controller;


import com.capgemni.bank_manager_api.entity.Transaction;
import com.capgemni.bank_manager_api.response.TransactionResponse;
import com.capgemni.bank_manager_api.response.UserResponse;
import com.capgemni.bank_manager_api.service.TransactionService;
import com.capgemni.bank_manager_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    TransactionService transactionService;
    UserService userService;

    public AdminController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @Operation(summary = "retrieve all users for admin",
        description = "gives every user of bank managment system")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @Operation(summary = "retrieve user by his id for admin",
        description = "gives a admin information about user with provided id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}")
    public UserResponse getUserById(@Valid @PathVariable Long id){
        return userService.getUserById(id);
    }

    @Operation(summary = "delete user with given id for admin",
        description = "delete user with given id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@Valid @PathVariable Long id){
        userService.deleteUserById(id);
    }

    @Operation(summary = "list all transactions",
        description = "show all transactions inside system")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/transactions")
    public List<TransactionResponse> getAllTransactions(){
        return  transactionService.getAllTransactions();
    }


}
