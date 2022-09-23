package com.ironhack.thestonebank.controller;

import com.ironhack.thestonebank.http.requests.transaction.CreateTransactionRequest;
import com.ironhack.thestonebank.service.transaction.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class AccountHolderController {

    final TransactionService transactionService;

    public AccountHolderController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //TODO: implement check account
//    @GetMapping("/account")
//    @ResponseStatus(HttpStatus.OK)
//    public Account checkAccount() {
//        return null;
//    }
    @PostMapping("/create/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public void createChecking(@RequestBody @Valid CreateTransactionRequest transaction) {
        transactionService.makeTransaction(transaction);
    }
}
