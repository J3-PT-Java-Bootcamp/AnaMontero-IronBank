package com.ironhack.thestonebank.model.user;

import com.ironhack.thestonebank.model.account.Checking;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AccountHolder {
    @Id
    private String id;
    private String name;
    private String dateOfBirth;
    @Embedded
    private Address primaryAddress;
//    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String mailingAddress;
//    private String keycloakId;
    @OneToOne
    private Checking account;
}