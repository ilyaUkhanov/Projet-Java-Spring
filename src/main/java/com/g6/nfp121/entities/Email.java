package com.g6.nfp121.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String street = null;
    String city = null;
    Integer zipcode = null;

    public Email() {}

    public Email(String street, String city, Integer zipcode) {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }

    public Email addContact(Contact contact) {
        contact.addEmail(this);
        return this;
    }

    public Email removeContact(Contact contact) {
        contact.removeEmail(this);
        return this;
    }
}
