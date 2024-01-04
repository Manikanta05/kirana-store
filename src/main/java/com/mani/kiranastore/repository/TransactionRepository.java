package com.mani.kiranastore.repository;

import com.mani.kiranastore.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query(value = "Select trn from Transaction trn where trn.date = :date ")
    List<Transaction> findByDate(@Param(value = "date") Date date);
}
