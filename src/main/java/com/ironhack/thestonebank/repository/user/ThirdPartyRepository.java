package com.ironhack.thestonebank.repository.user;

import com.ironhack.thestonebank.model.user.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
    public Optional<ThirdParty> findByHashedKey(Integer hashedKey);

}