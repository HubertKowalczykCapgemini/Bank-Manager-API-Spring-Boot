package com.capgemni.bank_manager_api.service;

import com.capgemni.bank_manager_api.request.TransactionRequest;
import com.capgemni.bank_manager_api.response.TransactionResponse;
import com.capgemni.bank_manager_api.response.UserTransactionResponse;
import com.capgemni.bank_manager_api.util.OperationType;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface TransactionService {


    TransactionResponse createTransaction(TransactionRequest request, OperationType operationType) throws BadRequestException;

    List<UserTransactionResponse> userTransactions();

    public List<TransactionResponse> getAllTransactions();
}
