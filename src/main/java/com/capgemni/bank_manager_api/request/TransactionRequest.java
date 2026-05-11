package com.capgemni.bank_manager_api.request;

import com.capgemni.bank_manager_api.util.OperationType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class TransactionRequest {
    @NotNull
    @Positive
    @Digits(integer = 17, fraction = 2)
    private BigDecimal amount;

    public TransactionRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
