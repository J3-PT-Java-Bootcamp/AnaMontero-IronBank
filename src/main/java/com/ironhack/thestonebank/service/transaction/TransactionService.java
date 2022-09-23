package com.ironhack.thestonebank.service.transaction;

import com.ironhack.thestonebank.http.requests.transaction.CreateTransactionRequest;
import com.ironhack.thestonebank.model.transaction.Transaction;

public interface TransactionService {

    Transaction makeTransaction(CreateTransactionRequest transactionDTO);
}