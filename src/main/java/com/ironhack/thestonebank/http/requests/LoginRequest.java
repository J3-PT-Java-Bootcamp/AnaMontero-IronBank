package com.ironhack.thestonebank.http.requests;

import lombok.Getter;

@Getter
public class LoginRequest {

    String username;
    String password;
}