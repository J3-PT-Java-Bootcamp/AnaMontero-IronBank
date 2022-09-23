package com.ironhack.thestonebank.http.requests.user;

import lombok.Getter;

@Getter
public class AuthenticatorRequest {

    String username;
    String password;
}