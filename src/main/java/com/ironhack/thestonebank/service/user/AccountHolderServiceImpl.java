package com.ironhack.thestonebank.service.user;

import com.ironhack.thestonebank.model.user.AccountHolder;
import com.ironhack.thestonebank.repository.user.AccountHolderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountHolderServiceImpl implements AccountHolderService{
    final AccountHolderRepository accountHolderRepository;

    public AccountHolderServiceImpl(AccountHolderRepository accountHolderRepository) {
        this.accountHolderRepository = accountHolderRepository;
    }

    public List<AccountHolder> findAll() {
        return accountHolderRepository.findAll();
    }

    @Override
    public AccountHolder create(AccountHolder accountHolder) {
        return accountHolderRepository.save(accountHolder);
    }

    @Override
    public AccountHolder findByUsername(String username) {
        return accountHolderRepository.findByUsername(username);
    }

    @Override
    public void deleteAccountHolder(String id) {
        AccountHolder foundAccountHolder = accountHolderRepository.findById(id).orElseThrow();
        accountHolderRepository.delete(foundAccountHolder);
    }
}