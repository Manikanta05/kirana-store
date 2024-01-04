package com.mani.kiranastore.service;

import com.mani.kiranastore.entity.Transaction;
import com.mani.kiranastore.repository.TransactionRepository;
import com.mani.kiranastore.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @InjectMocks
    TransactionServiceImpl service;
    @Mock
    TransactionRepository transactionRepository;

    @Test
    void recordTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setCurrency("INR");
        transaction.setAmount(1000);
        transaction.setDate(new Date());
        transaction.setName("XYZ");
        transaction.setType("CR");
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = service.recordTransaction(transaction);

        assertEquals(1000, result.getAmount());
        assertEquals("INR", result.getCurrency());
    }

    @Test
    void getTransactions() {
        Transaction transaction = new Transaction();
        transaction.setCurrency("INR");
        transaction.setAmount(1000);
        transaction.setDate(new Date());
        transaction.setName("XYZ");
        transaction.setType("CR");

        List<Transaction> transactions= List.of(transaction);

        when(transactionRepository.findByDate(any(Date.class))).thenReturn(transactions);
        List<Transaction> result = service.getTransactions(new Date());

        assertEquals(1,result.size());
        assertInstanceOf(Transaction.class,result.get(0));
        assertEquals("XYZ",result.get(0).getName());
        assertEquals("CR",result.get(0).getType());
       }
}