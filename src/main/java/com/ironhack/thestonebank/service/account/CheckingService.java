package com.ironhack.thestonebank.service.account;

import com.ironhack.thestonebank.http.requests.CreateCheckingRequest;

import java.security.NoSuchAlgorithmException;

public interface CheckingService {
    void createChecking(CreateCheckingRequest checkingAccount) throws NoSuchAlgorithmException;
}
