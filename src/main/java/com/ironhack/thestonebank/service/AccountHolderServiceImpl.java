package com.ironhack.thestonebank.service;

import com.ironhack.thestonebank.model.user.AccountHolder;
import com.ironhack.thestonebank.repository.user.AccountHolderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountHolderServiceImpl implements AccountHolderService{
    final AccountHolderRepository accountHolderRepository;

    public AccountHolderServiceImpl(AccountHolderRepository accountHolderRepository) {
        this.accountHolderRepository = accountHolderRepository;
    }

    ArrayList<AccountHolder> accountHolders = new ArrayList<>();

    public List<AccountHolder> findAll() {
        return accountHolderRepository.findAll();
    }

    @Override
    public AccountHolder create(AccountHolder accountHolder) {
        return accountHolderRepository.save(accountHolder);
    }

//    public AccountHolder get(String keycloakId) {
//        for (AccountHolder accountHolder : accountHolders) {
//            if (accountHolder.getKeycloakId().equals(keycloakId)) {
//                return accountHolder;
//            }
//
//        }
//        return null;
//    }
}