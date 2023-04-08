package com.g6.nfp121.entities;

import com.g6.nfp121.models.address.AddressCreateModel;

import java.util.List;
import java.util.Set;

public class AddressFactory {
    public static Address create(AddressCreateModel addressCreateModel, Set<Contact> contacts) {
        return new Address();
    }
}
