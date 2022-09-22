package com.ironhack.thestonebank.controller;

import com.ironhack.thestonebank.config.KeycloakProvider;
import com.ironhack.thestonebank.http.requests.CreateAccountHolderRequest;
import com.ironhack.thestonebank.http.requests.LoginRequest;
import com.ironhack.thestonebank.model.user.AccountHolder;
import com.ironhack.thestonebank.service.AccountHolderService;
import com.ironhack.thestonebank.service.AccountHolderServiceImpl;
import com.ironhack.thestonebank.service.KeycloaUserClientService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class AdminController {

    private final KeycloaUserClientService kcAdminClient;

    private final KeycloakProvider kcProvider;

    private final AccountHolderService accountHolderService;

    public AdminController(KeycloaUserClientService kcAdminClient, KeycloakProvider kcProvider,
                           AccountHolderServiceImpl accountHolderService) {
        this.kcProvider = kcProvider;
        this.kcAdminClient = kcAdminClient;
        this.accountHolderService = accountHolderService;
    }

    @GetMapping("/accountHolders")
    public List<AccountHolder> findAccountHolders() {
        return accountHolderService.findAll();
    }

    @PostMapping(value = "/createAccountHolder")
    public ResponseEntity<?> createUser(@RequestBody CreateAccountHolderRequest user) {
        Response createdResponse = kcAdminClient.createAccountHolderInKeycloak(user);
        return ResponseEntity.status(createdResponse.getStatus()).build();
    }

    @PostMapping("/get-user-token")
    public ResponseEntity<AccessTokenResponse> getToken(@NotNull @RequestBody LoginRequest loginRequest) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();

        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            var accessToken = accessTokenResponse.getToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);

        } catch (BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }
    }
}
