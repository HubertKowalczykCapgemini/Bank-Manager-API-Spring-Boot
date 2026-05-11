package com.capgemni.bank_manager_api.controller;


import com.capgemni.bank_manager_api.request.TransactionRequest;
import com.capgemni.bank_manager_api.response.TransactionResponse;
import com.capgemni.bank_manager_api.response.UserTransactionResponse;
import com.capgemni.bank_manager_api.service.TransactionService;
import com.capgemni.bank_manager_api.util.OperationType;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    TransactionService  transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/deposit")
    public TransactionResponse createDeposit(@Valid @RequestBody TransactionRequest transactionRequest) throws BadRequestException {
        return transactionService.createTransaction(transactionRequest, OperationType.DEPOSIT);
    }

    @Operation
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/withdrawal")
    public TransactionResponse createWithdrawal(@Valid @RequestBody TransactionRequest transactionRequest) throws BadRequestException {
        return transactionService.createTransaction(transactionRequest,OperationType.WITHDRAWAL);
    }

    @Operation
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserTransactionResponse> getUserTransactions(){
        return transactionService.userTransactions();
    }

}
