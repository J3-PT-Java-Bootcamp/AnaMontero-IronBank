package com.ironhack.thestonebank.model.account;

import com.ironhack.thestonebank.enums.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StudentChecking extends Account{
    private String secretKey;

    @Enumerated(value = EnumType.STRING)
    private AccountStatus status;
}