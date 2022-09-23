package com.ironhack.thestonebank.service.user;

import com.ironhack.thestonebank.model.user.AccountHolder;

import java.util.List;

public interface AccountHolderService {
    List<AccountHolder> findAll();

    AccountHolder create(AccountHolder accountHolder);

    AccountHolder findByUsername(String username);

    void deleteAccountHolder(String id);
}