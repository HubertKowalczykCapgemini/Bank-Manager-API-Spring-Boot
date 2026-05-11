package com.capgemni.bank_manager_api.response;

import com.capgemni.bank_manager_api.util.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserTransactionResponse {
    private OperationType operationType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public UserTransactionResponse(OperationType operationType, BigDecimal amount, LocalDateTime transactionDate) {
        this.operationType = operationType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
