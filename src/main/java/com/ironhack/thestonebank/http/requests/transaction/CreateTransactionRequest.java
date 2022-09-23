package com.ironhack.thestonebank.http.requests.transaction;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class CreateTransactionRequest {

    @NotNull(message = "Sender account can't be empty")
    private String senderAccountId;

    @NotNull(message = "Recipient account can't be empty")
    private String recipientAccountId;

    private String description;

    @NotNull(message = "Amount can't be empty")
    private BigDecimal transactionAmount;
}