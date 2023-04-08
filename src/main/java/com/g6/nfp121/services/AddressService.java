package com.g6.nfp121.services;

import java.util.*;
import java.util.stream.Collectors;
import com.g6.nfp121.entities.*;
import com.g6.nfp121.entities.Contact;
import com.g6.nfp121.exceptions.ApiIdNotFoundException;
import com.g6.nfp121.models.address.AddressCreateModel;
import com.g6.nfp121.models.address.AddressEditModel;
import com.g6.nfp121.models.address.AddressResponseModel;
import com.g6.nfp121.repositories.AddressRepository;
import com.g6.nfp121.repositories.ContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    public Address findAddress(Long id) throws ApiIdNotFoundException {
        return addressRepository.findById(id).orElseThrow(() -> new ApiIdNotFoundException("address", id));
    }

    public List<Address> findAllAddresses() {
        return new ArrayList<>(addressRepository.findAll());
    }

    public Address create(Address address) {
        return addressRepository.save(address);
    }

    public Address update(Address address, AddressEditModel addressEditModel)
            throws ApiIdNotFoundException {

        address.setCity(addressEditModel.getCity());
        address.setStreet(addressEditModel.getStreet());
        address.setZipcode(addressEditModel.getZipcode());

        if (!address.getContacts().stream()
                .map(Contact::getId)
                .collect(Collectors.toList())
                .equals(addressEditModel.getContactIDs()))
        {
            Set<Contact> contacts = contactRepository.findByContactIds(addressEditModel.getContactIDs());
            address.setContacts(contacts);
        }

        return addressRepository.save(address);
    }

    public AddressResponseModel convertToResponseModel(Address address) {
        return modelMapper.map(address, AddressResponseModel.class);
    }

    public Address create(AddressCreateModel addressModel, User user) throws ApiIdNotFoundException {
        Set<Contact> contacts = contactRepository.findByContactIds(addressModel.getContactIDs());
        Address address = AddressFactory.create(addressModel, contacts);

        return addressRepository.save(address);
    }

    public Address save(Address addressToEdit) {
        return addressRepository.save(addressToEdit);
    }

    public void delete(Address address) {
        addressRepository.delete(address);
    }
}
