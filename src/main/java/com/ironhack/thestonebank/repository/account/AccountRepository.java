package com.ironhack.thestonebank.repository.account;

import com.ironhack.thestonebank.model.account.Account;
import com.ironhack.thestonebank.model.user.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByPrimaryOwner(AccountHolder accountHolder);
}
