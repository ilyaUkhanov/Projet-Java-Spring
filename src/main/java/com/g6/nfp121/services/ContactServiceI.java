package com.g6.nfp121.services;

import com.g6.nfp121.entities.Contact;
import com.g6.nfp121.entities.Address;
import com.g6.nfp121.exceptions.ApiIdNotFoundException;
import com.g6.nfp121.exceptions.ApiWrongParamsException;
import com.g6.nfp121.models.contact.ContactCreateModel;
import com.g6.nfp121.models.contact.ContactUpdateModel;

public interface ContactServiceI {
    public Contact findContact(Long id) throws ApiIdNotFoundException;

    public void deleteContact(Long id) throws ApiIdNotFoundException;

    public Contact save(Contact contact);

    public Contact createContact(ContactCreateModel contactCreateModel) throws ApiIdNotFoundException;

    public Contact updateContact(Contact contact, ContactUpdateModel contactUpdateModel) throws ApiIdNotFoundException, ApiWrongParamsException;

    public Long delete(Contact contact);
}