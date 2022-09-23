package com.ironhack.thestonebank.model.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.thestonebank.model.transaction.Transaction;
import com.ironhack.thestonebank.model.user.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account {
    @Id
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "amount_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency_balance"))
    })
    private Money balance;

    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "amount_penalty_fee")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency_penalty_fee"))
    })
    private Money penaltyFee = new Money(new BigDecimal("40"));

    @OneToMany(mappedBy = "senderAccount")
    @JsonIgnore
    private List<Transaction> senderTransactions;


    @OneToMany(mappedBy = "recipientAccount")
    @JsonIgnore
    private List<Transaction> recipientTransactions;

    @CreationTimestamp
    private Instant creationDate;

    @UpdateTimestamp
    private Instant updatedDate;

    public Account(Long id, Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                   Instant creationDate) {
        this.id = id;
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.creationDate = creationDate;
    }
}