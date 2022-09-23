package com.ironhack.thestonebank.http.requests.user;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class CreateAccountHolderRequest {
    String username;

    @NotNull(message = "Password can't be empty")
    String password;

    @NotNull(message = "First name can't be empty")
    String firstName;

    @NotNull(message = "Lsat name can't be empty")
    String lastName;

    //Format: YYYY-MM-DD
    @NotNull(message = "Date of birth can't be empty")
    String dateOfBirth;

    String road;

    String city;

    String postalCode;

    String country;

    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Incorrect email format. Try again")
    String email;
}