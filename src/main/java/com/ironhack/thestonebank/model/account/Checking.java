package com.ironhack.thestonebank.model.account;

import com.ironhack.thestonebank.enums.AccountStatus;
import com.ironhack.thestonebank.model.user.AccountHolder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.ironhack.thestonebank.enums.AccountStatus.ACTIVE;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Checking extends Account {
    private String secretKey;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))})
    private Money minimumBalance = new Money(new BigDecimal("250"));

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "amount", column =
    @Column(name = "monthly_maintenance_fee_amount")), @AttributeOverride(name = "currency", column = @Column(name =
            "monthly_maintenance_fee_currency"))})
    private Money monthlyMaintenanceFee = new Money(new BigDecimal("12"));

    @Enumerated(value = EnumType.STRING)
    private AccountStatus status;

    public Checking(Long id, Money balance, AccountHolder primaryOwner, String secretKey) {
        super(id, balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = ACTIVE;
    }
}