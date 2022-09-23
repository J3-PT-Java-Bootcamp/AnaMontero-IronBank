package com.ironhack.thestonebank.service.transaction;

import com.ironhack.thestonebank.http.requests.transaction.CreateTransactionRequest;
import com.ironhack.thestonebank.model.account.Account;
import com.ironhack.thestonebank.model.account.Money;
import com.ironhack.thestonebank.model.transaction.Transaction;
import com.ironhack.thestonebank.repository.account.CheckingRepository;
import com.ironhack.thestonebank.repository.transaction.TransactionRepository;
import com.ironhack.thestonebank.repository.user.AccountHolderRepository;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@Log
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountHolderRepository accountHolderRepository;

    private final CheckingRepository checkingRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountHolderRepository accountHolderRepository,
                                  CheckingRepository checkingRepository) {
        this.transactionRepository = transactionRepository;
        this.accountHolderRepository = accountHolderRepository;
        this.checkingRepository = checkingRepository;
    }

    private void validateTransaction(CreateTransactionRequest transaction) {

        Account senderAccount = checkingRepository.findById(Long.valueOf(transaction.getSenderAccountId())).orElseThrow();
        Account recipientAccount = checkingRepository.findById(Long.valueOf(transaction.getRecipientAccountId())).orElseThrow();

        BigDecimal senderBalance = senderAccount.getBalance().getAmount();
        BigDecimal requestedAmount = transaction.getTransactionAmount();
        BigDecimal recipientBalance = recipientAccount.getBalance().getAmount();

        if(senderBalance.compareTo(requestedAmount)==1 || senderBalance.compareTo(requestedAmount)==0){

        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }

        if(recipientBalance.compareTo(requestedAmount)==1 || senderBalance.compareTo(requestedAmount)==0){

        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }
    }

    @Override
    public Transaction makeTransaction(CreateTransactionRequest transaction) {

        var senderAccount = checkingRepository.findById(Long.valueOf(transaction.getSenderAccountId())).orElseThrow();
        var recipientAccount = checkingRepository.findById(Long.valueOf(transaction.getRecipientAccountId())).orElseThrow();

        validateTransaction(transaction);

        Transaction createdTransaction = new Transaction();
        createdTransaction.setSenderAccount(senderAccount);
        createdTransaction.setRecipientAccount(recipientAccount);
        createdTransaction.setDescription(transaction.getDescription());
        createdTransaction.setTransactionAmount(new Money(transaction.getTransactionAmount()));

        var remainingSenderBalance = senderAccount.getBalance();
        remainingSenderBalance.decreaseAmount(transaction.getTransactionAmount());
        senderAccount.setBalance(remainingSenderBalance);

        var remainingRecipientAccount = recipientAccount.getBalance();
        remainingRecipientAccount.increaseAmount(transaction.getTransactionAmount());
        recipientAccount.setBalance(remainingRecipientAccount);

        checkingRepository.save(senderAccount);
        checkingRepository.save(recipientAccount);

        log.info("Transaction has been completed");

        return transactionRepository.save(createdTransaction);
    }
}