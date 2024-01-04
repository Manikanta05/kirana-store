package com.mani.kiranastore.service;

import com.mani.kiranastore.entity.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    Transaction recordTransaction(Transaction transaction) throws Exception;

    List<Transaction> getTransactions(Date date);
}
