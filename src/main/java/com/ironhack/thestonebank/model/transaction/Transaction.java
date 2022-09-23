package com.ironhack.thestonebank.model.transaction;

import com.ironhack.thestonebank.model.account.Money;
import com.ironhack.thestonebank.model.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Account senderAccount;

    @NotNull
    @ManyToOne
    private Account recipientAccount;

    private String description;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "transaction_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "transaction_currency"))
    })
    private Money transactionAmount;

    @CreationTimestamp
    private Instant creationDate;

    public Transaction(Account senderAccount, Account recipientAccount, String description, Money transactionAmount,
                       Instant creationDate) {
        this.senderAccount = senderAccount;
        this.recipientAccount = recipientAccount;
        this.description = description;
        this.transactionAmount = transactionAmount;
        this.creationDate = creationDate;
    }
}