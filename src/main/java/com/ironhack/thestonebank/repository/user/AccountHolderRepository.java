package com.ironhack.thestonebank.repository.user;

import com.ironhack.thestonebank.model.user.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder,String> {
    AccountHolder findByName(String name);
    AccountHolder findById(Long id);
}