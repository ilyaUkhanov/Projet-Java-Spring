package com.g6.nfp121.entities;

import javax.persistence.*;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String street = null;
    String city = null;
    Integer zipcode = null;

    @ManyToMany(mappedBy = "addresses")
    private Set<Contact> contacts;

    public Address() {
        this.contacts = new HashSet<>();;
    }

    public Address(String street, String city, Integer zipcode, Set<Contact> contacts) {
        this.contacts = new HashSet<>();

        if (contacts != null) {
            this.contacts = contacts;
        }
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }

    public Address addContact(Contact contact) {
        contact.addAddress(this);
        this.contacts.add(contact);
        return this;
    }

    public Address removeContact(Contact contact) {
        this.contacts.remove(contact);
        return this;
    }
}
