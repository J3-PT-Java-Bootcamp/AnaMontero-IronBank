package com.ironhack.thestonebank.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Address {
    private String road;
    private String city;
    private String postalCode;
    private String country;
}