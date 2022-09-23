package com.ironhack.thestonebank.service.account;

import com.ironhack.thestonebank.http.requests.account.CreateCheckingRequest;
import com.ironhack.thestonebank.model.account.Account;

import java.util.List;

public interface CheckingService {
    void createChecking(CreateCheckingRequest checkingAccount) throws Exception;

    List<Account> getAccounts(String username);
}