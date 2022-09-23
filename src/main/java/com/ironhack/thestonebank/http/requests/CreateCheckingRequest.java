package com.ironhack.thestonebank.http.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCheckingRequest {
    @NotNull(message = "Account's balance can't be empty")
    private BigDecimal balance;

    @NotNull(message = "Account's currency can't be empty")
    private Currency currency;

    @NotNull(message = "Account's owner can't be empty")
    private String primaryOwner;

    private String secondaryOwner;
}
