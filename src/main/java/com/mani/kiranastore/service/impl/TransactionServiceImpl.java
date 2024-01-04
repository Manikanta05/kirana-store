package com.mani.kiranastore.service.impl;

import com.mani.kiranastore.entity.Transaction;
import com.mani.kiranastore.exception.CustomException;
import com.mani.kiranastore.pojo.CurrencyConversionResult;
import com.mani.kiranastore.repository.TransactionRepository;
import com.mani.kiranastore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Value("${currency.api.url}")
    private String currencyApiUrl;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction recordTransaction(Transaction transaction) throws Exception {
        double newAmount = 0;
        if (!transaction.getCurrency().equals("INR")) {
            newAmount = convertCurrency(transaction.getAmount(), transaction.getCurrency());
            // post conversion updating the same object to save
            transaction.setAmount(newAmount);
            transaction.setCurrency("INR");
        }

        return transactionRepository.save(transaction);

    }

    @Override
    public List<Transaction> getTransactions(Date date) {
        return transactionRepository.findByDate(date);
    }

    public double convertCurrency(double amount, String fromCurrency) throws CustomException {
        // Use the external API for currency conversion
        String apiUrl = String.format("%s", currencyApiUrl);

        RestTemplate restTemplate = new RestTemplate();
        CurrencyConversionResult conversionResult = restTemplate.getForObject(apiUrl, CurrencyConversionResult.class);
        DecimalFormat df = new DecimalFormat("#.##");
        double newAmount = 0;
        if (conversionResult != null) {
            newAmount = amount / conversionResult.getRates().get(fromCurrency);
            return Double.parseDouble(df.format(newAmount));
        } else {
            throw new CustomException("Failed to retrieve currency conversion rates");
        }
    }
}
