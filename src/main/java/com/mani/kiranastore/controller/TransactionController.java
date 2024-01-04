package com.mani.kiranastore.controller;

import com.mani.kiranastore.common.AppLogger;
import com.mani.kiranastore.entity.Transaction;
import com.mani.kiranastore.exception.CustomException;
import com.mani.kiranastore.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "Kirana Store", description = "Transaction management APIs")
@RestController
@RequestMapping("/api/v1.0/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Operation(
            summary = "Record Transaction",
            description = "Save a transaction by hitting the post request with body as Transaction schema payload in JSON format, after validation if everything is good the API response is 201-created.Note only USD and INR transactions accepted and if in case USD it's converted to INR")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Transaction.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})})
    @PostMapping
    public ResponseEntity<String> recordTransaction(@Valid @RequestBody Transaction transaction) throws CustomException {
        AppLogger.setLog("POST Transactions api hit");
        try {
            if (transaction.getType().equals("CR") || transaction.getType().equals("DR")) {
            } else {
                return new ResponseEntity<>("Invalid transaction Type", HttpStatus.BAD_REQUEST);
            }
            if (transaction.getCurrency().equals("INR") || transaction.getCurrency().equals("USD")) {
                transactionService.recordTransaction(transaction);
                return new ResponseEntity<>("Successfully saved", HttpStatus.CREATED);
            } else {
                throw new CustomException("Store supports only INR and USD as currency");
            }


        } catch (Exception e) {
            AppLogger.setError(e.getMessage());
            throw new CustomException(e.getMessage());
        }
    }

    @Operation(
            summary = "Get all Transactions for date",
            description = "This API can be used to fetch all transactions for a particular Date. Note all the transactions will be having INR as currency")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")})
    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE) Date date) throws CustomException {
        AppLogger.setLog("GET Transactions api hit");
        if (date != null) {
            return new ResponseEntity<>(transactionService.getTransactions(date), HttpStatus.OK);
        } else {
            AppLogger.setError("Date cannot be null");
            throw new CustomException("Date is required");
        }
    }
}
