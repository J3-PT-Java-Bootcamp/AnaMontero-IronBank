package com.ironhack.thestonebank.repository.account;

import com.ironhack.thestonebank.model.account.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<Credit, Long> {
}
