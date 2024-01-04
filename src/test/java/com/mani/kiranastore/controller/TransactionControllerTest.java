package com.mani.kiranastore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mani.kiranastore.entity.Transaction;
import com.mani.kiranastore.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @MockBean
    TransactionService transactionService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    void recordTransaction_BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1.0/transactions")).andExpect(status().isBadRequest());
    }

    @Test
    void recordTransaction_testCase1() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setCurrency("INR");
        transaction.setAmount(1000);
        transaction.setDate(new Date());
        transaction.setName("XYZ");
        transaction.setType("CR");
        when(transactionService.recordTransaction(any(Transaction.class))).thenReturn(transaction);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1.0/transactions").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isCreated()).andReturn();
        assertEquals("Successfully saved", mvcResult.getResponse().getContentAsString());
    }
    @Test
    void recordTransaction_testCase2() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setCurrency("RUS");
        transaction.setAmount(1000);
        transaction.setDate(new Date());
        transaction.setName("XYZ");
        transaction.setType("CR");
        when(transactionService.recordTransaction(any(Transaction.class))).thenReturn(transaction);
        MvcResult mvcResult = mockMvc.perform(post("/api/v1.0/transactions").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transaction)))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals("Store supports only INR and USD as currency", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getTransactions_BadRequest() throws Exception {
        //No Param passed
        mockMvc.perform(get("/api/v1.0/transactions")).andExpect(status().isBadRequest());
    }

    @Test
    void getTransactions_TestCase1() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setCurrency("INR");
        transaction.setAmount(1000);
        transaction.setDate(new Date());
        transaction.setName("XYZ");
        transaction.setType("CR");

        List<Transaction> transactions = List.of(transaction);
        when(transactionService.getTransactions(any(Date.class))).thenReturn(transactions);

        //No Param passed
        mockMvc.perform(get("/api/v1.0/transactions").param("date", "2024-01-03"))
                .andExpect(status().isOk());
    }

    @Test
    void getTransactions_TestCase2() throws Exception {

        //No Param passed
        mockMvc.perform(get("/api/v1.0/transactions").param("date", ""))
                .andExpect(status().isBadRequest());
    }

}