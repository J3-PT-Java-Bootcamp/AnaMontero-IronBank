package com.ironhack.thestonebank.service;


import com.ironhack.thestonebank.config.KeycloakProvider;
import com.ironhack.thestonebank.http.requests.CreateAccountHolderRequest;
import com.ironhack.thestonebank.model.user.AccountHolder;
import com.ironhack.thestonebank.model.user.Address;
import com.ironhack.thestonebank.service.user.AccountHolderService;
import lombok.extern.java.Log;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


@Service
@Log
public class KeycloaUserClientService {
    private final KeycloakProvider kcProvider;
    @Value("${keycloak.realm}")
    public String realm;
    @Value(("${keycloak.resource}"))
    public String clientId;

    final AccountHolderService accountHolderService;

    public KeycloaUserClientService(KeycloakProvider keycloakProvider, AccountHolderService accountHolderService) {
        this.kcProvider = keycloakProvider;
        this.accountHolderService = accountHolderService;
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    //TODO:replicar método para crear ThirdParty users
    public Response createAccountHolderInKeycloak(CreateAccountHolderRequest user) {
        var adminKeycloak = kcProvider.getInstance();
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getUsername());
        kcUser.setEmail(user.getEmail());
        kcUser.setFirstName(user.getFirstName());
        kcUser.setLastName(user.getLastName());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);
        kcUser.setGroups(List.of("members"));

        Response response = usersResource.create(kcUser);

        if (response.getStatus() == 201) {
            List<UserRepresentation> userList = adminKeycloak.realm(realm).users().search(kcUser.getUsername()).stream()
                    .filter(userRep -> userRep.getUsername().equals(kcUser.getUsername())).toList();
            var createdUser = userList.get(0);
            log.info("User with id: " + createdUser.getId() + " created");

            var accountHolder = new AccountHolder();
            accountHolder.setId(createdUser.getId());
            accountHolder.setName(user.getFirstName() + " " + user.getLastName());
            accountHolder.setUsername(user.getUsername());
            accountHolder.setMailingAddress(user.getEmail());
            accountHolder.setPrimaryAddress(new Address(user.getRoad(),user.getCity(),user.getPostalCode(),user.getCountry()));
            accountHolder.setDateOfBirth(LocalDate.parse(user.getDateOfBirth()));

            var result = accountHolderService.create(accountHolder);
            log.info("Account holder created: " + result.getName());
        }
//
//        //Error si el username existe
//        else if (response.getStatus() == 409) {
//            List<UserRepresentation> userList = adminKeycloak.realm(realm).users().search(kcUser.getUsername()).stream()
//                    .filter(userRep -> userRep.getUsername().equals(kcUser.getUsername())).toList();
//            var foundUser = userList.get(0);
//            log.info("User with username: " + foundUser.getUsername() + " already exists");
//        }
//
//        //TODOError si el email existe
//        else if (response.getStatus() == 409) {
//            List<UserRepresentation> userList = adminKeycloak.realm(realm).users().search(kcUser.getEmail()).stream()
//                    .filter(userRep -> userRep.getEmail().equals(kcUser.getEmail())).toList();
//            var createdUser = userList.get(0);
//            log.info("User with id: " + createdUser.getId() + " already exists");
//        }
//
//
//        else{
//            log.info(response.toString());
//        }

        return response;

    }
}