package com.capgemni.bank_manager_api.service;

import com.capgemni.bank_manager_api.entity.Transaction;
import com.capgemni.bank_manager_api.entity.User;
import com.capgemni.bank_manager_api.repository.TransactionRepository;

import com.capgemni.bank_manager_api.repository.UserRepository;
import com.capgemni.bank_manager_api.request.TransactionRequest;
import com.capgemni.bank_manager_api.response.TransactionResponse;
import com.capgemni.bank_manager_api.response.UserTransactionResponse;
import com.capgemni.bank_manager_api.util.FindAuthenticateUser;
import com.capgemni.bank_manager_api.util.OperationType;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class TransactionsServiceImpl implements TransactionService{
    private static final Logger log = LoggerFactory.getLogger(TransactionsServiceImpl.class);
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final FindAuthenticateUser findAuthenticateUser;


    public TransactionsServiceImpl( UserRepository userRepository,
                                    TransactionRepository transactionRepository,
                                    FindAuthenticateUser findAuthenticateUser) {

        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.findAuthenticateUser = findAuthenticateUser;
    }


    @Transactional
    public TransactionResponse createTransaction(TransactionRequest request, OperationType operationType) throws BadRequestException {
        User loggedUser = getLoggedUser();

        Transaction transaction = buildTransaction(loggedUser, request, operationType);

        checkActualBalanceIsPositive(loggedUser);
        checkTranasactionAmountIsPositive(transaction);
        updateUserBalance(loggedUser,transaction,operationType);

        transaction.setOwner(loggedUser);
        transactionRepository.saveAndFlush(transaction);

        return new TransactionResponse(loggedUser.getId(),operationType,transaction.getAmount(), transaction.getCreatedAt());
    }

    @Override
    public List<UserTransactionResponse> userTransactions() {
        User loggedUser = getLoggedUser();

        return transactionRepository.findByOwner(loggedUser)
            .stream()
            .map(this ::convertToUserTransactionResponse)
            .toList();
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return StreamSupport.stream(transactionRepository.findAll()
            .spliterator(),false)
            .map(this::convertToTransactionResponse)
            .toList();
    }


    private void updateUserBalance(User loggedUser, Transaction request, OperationType operationType) throws BadRequestException {
        BigDecimal actualBalance = loggedUser.getBalance();
        BigDecimal transactionAmount = request.getAmount();

        if(operationType == OperationType.DEPOSIT){
            loggedUser.setBalance(actualBalance.add(transactionAmount));
        }
        if (operationType == OperationType.WITHDRAWAL){
            checkActualBalanceIsOverWithdrawalAmount(loggedUser,request);
            loggedUser.setBalance(actualBalance.subtract(transactionAmount));
        }

    }



    private void checkActualBalanceIsOverWithdrawalAmount(User loggedUser, Transaction transaction) throws BadRequestException {
        BigDecimal currentBalance = loggedUser.getBalance();
        BigDecimal transactionAmount = transaction.getAmount();

        if (currentBalance.compareTo(transactionAmount) <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Provided amount is above actual user balance");
        }
    }


    private Transaction buildTransaction(User loggedUser, TransactionRequest request, OperationType operationType) {
        Transaction transaction = new Transaction();
        transaction.setId(null);
        transaction.setOperationType(operationType);
        transaction.setAmount(request.getAmount());
        transaction.setOwner(loggedUser);
        return transaction;
    }

    private User getLoggedUser() {
        User authUser = findAuthenticateUser.getAuthenticatedUser();
        return userRepository.findById(authUser.getId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found"));
    }

    private void checkActualBalanceIsPositive(User loggedUser){
        BigDecimal actualBalance = loggedUser.getBalance();
        boolean isActualBalanceOver0 = actualBalance.compareTo(BigDecimal.ZERO) < 0;

        if (isActualBalanceOver0){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Current Balance if under 0 | Illegal State");
        }

    }

    private void checkTranasactionAmountIsPositive(Transaction request) throws BadRequestException {
        BigDecimal actualBalance = request.getAmount();
        boolean isActualBalanceOver0 = actualBalance.compareTo(BigDecimal.ZERO) < 0;

        if (isActualBalanceOver0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Amount Have to positive Number");
        }
    }

    private TransactionResponse convertToTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
            transaction.getOwner().getId(),
            transaction.getOperationType(),
            transaction.getAmount(),
            transaction.getCreatedAt()
        );
    }

    private UserTransactionResponse convertToUserTransactionResponse(Transaction transaction) {
        return new UserTransactionResponse(
            transaction.getOperationType(),
            transaction.getAmount(),
            transaction.getCreatedAt()
        );
    }



}
