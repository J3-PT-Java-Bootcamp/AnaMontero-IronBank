package com.ironhack.thestonebank.http.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountHolderRequest {
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    String dateOfBirth;
    String road;
    String city;
    String postalCode;
    String country;
    String email;
}
