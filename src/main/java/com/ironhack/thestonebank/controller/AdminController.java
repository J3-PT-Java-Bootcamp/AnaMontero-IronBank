package com.ironhack.thestonebank.controller;

import com.ironhack.thestonebank.config.KeycloakProvider;
import com.ironhack.thestonebank.enums.AccountStatus;
import com.ironhack.thestonebank.http.requests.account.CreateCheckingRequest;
import com.ironhack.thestonebank.http.requests.user.AuthenticatorRequest;
import com.ironhack.thestonebank.http.requests.user.CreateAccountHolderRequest;
import com.ironhack.thestonebank.model.user.AccountHolder;
import com.ironhack.thestonebank.service.account.CheckingService;
import com.ironhack.thestonebank.service.user.AccountHolderService;
import com.ironhack.thestonebank.service.user.AccountHolderServiceImpl;
import com.ironhack.thestonebank.service.user.KeycloaUserClientService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final KeycloaUserClientService kcAdminClient;

    private final KeycloakProvider kcProvider;

    private final AccountHolderService accountHolderService;

    private final CheckingService checkingService;

    public AdminController(KeycloaUserClientService kcAdminClient, KeycloakProvider kcProvider,
                           AccountHolderServiceImpl accountHolderService, CheckingService checkingService) {
        this.kcProvider = kcProvider;
        this.kcAdminClient = kcAdminClient;
        this.accountHolderService = accountHolderService;
        this.checkingService = checkingService;
    }

    @GetMapping("/accountHolders")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> findAccountHolders() {
        return accountHolderService.findAll();
    }

    @PostMapping(value = "/create/AccountHolder")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createUser(@RequestBody CreateAccountHolderRequest user) {
        Response createdResponse = kcAdminClient.createAccountHolderInKeycloak(user);
        return ResponseEntity.status(createdResponse.getStatus()).build();
    }

    @PostMapping("/create/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public void createChecking(@RequestBody @Valid CreateCheckingRequest checkingAccount) throws Exception {
        checkingService.createChecking(checkingAccount);
    }

    @PatchMapping("/update/status/{id}/{status}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCheckingStatus(@PathVariable Long id, @PathVariable AccountStatus status) {
        checkingService.updateCheckingStatus(id, status);
    }

    @PatchMapping("/update/balance/{id}/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCheckingBalance (@PathVariable Long id, @PathVariable String amount) {
        checkingService.updateCheckingBalance(id, amount);
    }

    @DeleteMapping("/delete/checking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccountHolder(@PathVariable("id")String id){
        accountHolderService.deleteAccountHolder(id);
    }

    @PostMapping("/get-user-token")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AccessTokenResponse> getToken(@NotNull @RequestBody AuthenticatorRequest loginRequest) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();

        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);

        } catch (BadRequestException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }
    }
}