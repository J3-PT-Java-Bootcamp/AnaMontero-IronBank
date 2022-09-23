package com.ironhack.thestonebank.service.account;

import com.ironhack.thestonebank.http.requests.account.CreateCheckingRequest;

public interface CheckingService {
    void createChecking(CreateCheckingRequest checkingAccount) throws Exception;
}