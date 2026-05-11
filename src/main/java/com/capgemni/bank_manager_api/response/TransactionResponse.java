package com.capgemni.bank_manager_api.response;


import com.capgemni.bank_manager_api.util.OperationType;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {

    private Long ownerId;
    private OperationType operationType;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public TransactionResponse(Long ownerId, OperationType operationType, BigDecimal amount, LocalDateTime transactionDate) {
        this.ownerId = ownerId;
        this.operationType = operationType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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
