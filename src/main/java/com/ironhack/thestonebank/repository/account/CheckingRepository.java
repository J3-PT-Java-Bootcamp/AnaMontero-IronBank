package com.ironhack.thestonebank.repository.account;

import com.ironhack.thestonebank.model.account.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {
}