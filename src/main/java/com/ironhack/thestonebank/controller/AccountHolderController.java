package com.ironhack.thestonebank.controller;

import com.ironhack.thestonebank.http.requests.transaction.CreateTransactionRequest;
import com.ironhack.thestonebank.model.account.Account;
import com.ironhack.thestonebank.service.account.CheckingService;
import com.ironhack.thestonebank.service.transaction.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AccountHolderController {

    final TransactionService transactionService;

    final CheckingService checkingService;

    public AccountHolderController(TransactionService transactionService, CheckingService checkingService) {
        this.transactionService = transactionService;
        this.checkingService = checkingService;
    }

    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> checkAccounts(@AuthenticationPrincipal UserDetails userDetails) {
        return checkingService.getAccounts(userDetails.getUsername());
    }

    @PostMapping("/create/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public void createChecking(@RequestBody @Valid CreateTransactionRequest transaction) {
        transactionService.makeTransaction(transaction);
    }
}
