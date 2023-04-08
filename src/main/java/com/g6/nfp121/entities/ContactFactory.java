package com.g6.nfp121.entities;

import java.util.List;

import com.g6.nfp121.models.contact.ContactCreateModel;

public class ContactFactory {
    public static Contact create(ContactCreateModel contactCreateModel, List<Address> addresses) {
        Contact contact = new Contact();
        contact.setFirstname(contactCreateModel.getFirstname());
        contact.setLastname(contactCreateModel.getLastname());
        contact.setAddresses(addresses);

        return contact;
    }
}
