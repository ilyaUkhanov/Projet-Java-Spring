package com.g6.nfp121.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.g6.nfp121.entities.*;
import com.g6.nfp121.entities.Contact;
import com.g6.nfp121.exceptions.ApiIdNotFoundException;
import com.g6.nfp121.models.contact.ContactCreateModel;
import com.g6.nfp121.models.contact.ContactUpdateModel;
import com.g6.nfp121.repositories.AddressRepository;
import com.g6.nfp121.repositories.ContactRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@RequiredArgsConstructor
@Service
public class ContactService implements ContactServiceI {

    private final ContactRepository contactRepository;
    private final AddressService addressService;
    private final AddressRepository addressRepository;

    private JAXBContext context;
    private final String filesFolder = "/home/camelya/IdeaProjects/spring-jaxb/src/main/resources/files";
    private final String filename = "Book1.xml";

    @PostConstruct
    void init() throws JAXBException {
        context = JAXBContext.newInstance(Contact.class);
    }

    public Contact findContact(Long id) throws ApiIdNotFoundException {
        return contactRepository.findById(id).orElseThrow(() -> new ApiIdNotFoundException("Contact", id));
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }


    public void deleteContact(Long id) throws ApiIdNotFoundException {

        Contact contact = findContact(id);

        contactRepository.delete(contact);

    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact createContact(ContactCreateModel contactCreateModel) {
        List<Long> addressesIDs = contactCreateModel.getAddressesIDs();
        List<Address> addresses = new ArrayList<>();
        if (addressesIDs != null && !addressesIDs.isEmpty()) {
            addresses = addressRepository.findAllById(addressesIDs);
        }
        Contact contact = ContactFactory.create(contactCreateModel, addresses);
        return contactRepository.save(contact);
    }

    public Contact updateContact(Contact contact, ContactUpdateModel contactUpdateModel) {

        if(!contact.getFirstname().equals(contactUpdateModel.getFirstname())) {
            contact.setFirstname(contactUpdateModel.getFirstname());
        }

        if(!contact.getLastname().equals(contactUpdateModel.getLastname())) {
            contact.setLastname(contactUpdateModel.getLastname());
        }

        if (!contact.getAddresses().stream()
                .map(Address::getId)
                .collect(Collectors.toList())
                .equals(contactUpdateModel.getAddressesID()))
        {
            List<Address> addresses = addressRepository.findAllById(contactUpdateModel.getAddressesID());
            contact.setAddresses(addresses);
        }

        return contactRepository.save(contact);
    }

    public Long delete(Contact contact) {
        Long id = contact.getId();
        for (Address address: contact.getAddresses()) {
            contact.removeAddress(address);
        }
        contactRepository.delete(contact);

        return id;
    }

    public void createXml(Contact contact) {
        try {
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            Path dir = Path.of("classpath:/xml/");
            Path pathToFile = dir.resolve((contact.getFirstname() + "_" + contact.getLastname()).concat(".xml"));

            if (Files.notExists(pathToFile)) {
                Files.createDirectories(dir);
                Files.createFile(pathToFile);
            }
            mar.marshal(contact, pathToFile.toFile());
        } catch (Exception e) {
            throw new RuntimeException("Can't write the file");
        }
    }
}
