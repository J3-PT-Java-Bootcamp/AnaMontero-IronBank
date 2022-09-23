package com.ironhack.thestonebank.model.account;

import com.ironhack.thestonebank.enums.AccountStatus;
import com.ironhack.thestonebank.model.user.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import static com.ironhack.thestonebank.enums.AccountStatus.ACTIVE;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentChecking extends Account{
    private String secretKey;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus status;

    public StudentChecking(Long id, Money balance, AccountHolder primaryOwner, String secretKey) {
        super(id, balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = ACTIVE;
    }
}