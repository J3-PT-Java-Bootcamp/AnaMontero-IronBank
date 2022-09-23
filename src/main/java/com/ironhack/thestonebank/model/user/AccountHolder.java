package com.ironhack.thestonebank.model.user;

import com.ironhack.thestonebank.model.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountHolder extends User{
    private LocalDate dateOfBirth;
    @Embedded
    private Address primaryAddress;

    private String mailingAddress;
    @OneToOne
    private Account account;
}