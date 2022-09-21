package com.ironhack.thestonebank.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ThirdParty {
    @Id
    private String id;
    private String name;
    private String hashedKey;
}