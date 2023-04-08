package com.g6.nfp121.typemaps;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.g6.nfp121.entities.Address;
import com.g6.nfp121.entities.Contact;
import com.g6.nfp121.models.contact.ContactResponseModel;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContactTypemap {
        private TypeMap<Contact, ContactResponseModel> contactMap;
        private final ModelMapper modelMapper;

        @PostConstruct
        public void initialize() {
                Converter<List<Address>, List<Long>> addressListToIdList = ctx -> ctx.getSource().stream()
                                .map(Address::getId).collect(Collectors.toList());

                modelMapper.getConfiguration().setSkipNullEnabled(true);

                contactMap = modelMapper.createTypeMap(Contact.class, ContactResponseModel.class)
                                .addMappings(map -> map.using(addressListToIdList).map(Contact::getAddresses,
                                                ContactResponseModel::setAddressesIDs));
        }

        public TypeMap<Contact, ContactResponseModel> getMap() {
                return contactMap;
        }
}
