package com.capgemni.bank_manager_api.repository;

import com.capgemni.bank_manager_api.entity.Transaction;
import com.capgemni.bank_manager_api.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Long> {
    void saveAndFlush(Transaction transaction);

    List<Transaction> findByOwner(User owner);
}
