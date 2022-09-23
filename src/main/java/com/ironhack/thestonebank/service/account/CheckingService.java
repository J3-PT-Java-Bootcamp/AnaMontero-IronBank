package com.ironhack.thestonebank.service.account;

import com.ironhack.thestonebank.enums.AccountStatus;
import com.ironhack.thestonebank.http.requests.account.CreateCheckingRequest;
import com.ironhack.thestonebank.model.account.Account;

import java.util.List;

public interface CheckingService {
    void createChecking(CreateCheckingRequest checkingAccount) throws Exception;

    List<Account> getAccounts(String username);

    void updateCheckingStatus(Long id, AccountStatus accountStatus);

    void updateCheckingBalance(Long id, String amount);
}