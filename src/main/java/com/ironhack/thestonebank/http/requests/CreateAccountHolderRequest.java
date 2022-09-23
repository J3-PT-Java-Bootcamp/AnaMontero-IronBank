package com.ironhack.thestonebank.http.requests;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CreateAccountHolderRequest {
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    //Format: YYYY-MM-DD
    String dateOfBirth;
    String road;
    String city;
    String postalCode;
    String country;
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Incorrect email format. Try again")
    String email;
}
