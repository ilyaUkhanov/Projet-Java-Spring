package com.g6.nfp121.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.g6.nfp121.entities.Address;
import com.g6.nfp121.entities.Contact;
import com.g6.nfp121.entities.AddressFactory;
import com.g6.nfp121.exceptions.ApiIdNotFoundException;
import com.g6.nfp121.exceptions.ApiWrongParamsException;
import com.g6.nfp121.models.address.AddressCreateModel;
import com.g6.nfp121.models.address.AddressEditModel;
import com.g6.nfp121.models.address.AddressResponseModel;
import com.g6.nfp121.services.AddressService;

import com.g6.nfp121.services.ContactService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/addresses")
@Api(value = "Address Controller", tags = "Address")
public class APIAddressController {
    private final AddressService addressService;
    private final ContactService contactService;
    private final ModelMapper modelMapper;

    @ApiOperation(value = "Récupérer le Address par ID", response = Iterable.class, tags = "Address")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Success|OK"), //
            @ApiResponse(code = 401, message = "not authorized"), //
            @ApiResponse(code = 403, message = "forbidden"), //
            @ApiResponse(code = 404, message = "not found") //
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseModel> getById(@PathVariable("id") Long id) throws ApiIdNotFoundException {
        Address address = addressService.findAddress(id);

        if (address == null) {
            return ResponseEntity.badRequest().body(null);
        }

        AddressResponseModel response = modelMapper.map(address, AddressResponseModel.class);

        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Créer un address", response = Iterable.class, tags = "Address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found")
    })
    @PostMapping
    public ResponseEntity<AddressResponseModel> create(@Valid @RequestBody AddressCreateModel addressCreateModel)
            throws ApiIdNotFoundException {

        Set<Contact> contacts = new HashSet<>();
        for (Long contactID: addressCreateModel.getContactIDs()) {
            Contact contact = contactService.findContact(contactID);
            contacts.add(contact);
        }

        Address address = AddressFactory.create(addressCreateModel, contacts);
        Address persistedAddress = addressService.create(address);

        AddressResponseModel response = modelMapper.map(persistedAddress, AddressResponseModel.class);

        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Modifier un Address par ID", response = Iterable.class, tags = "Address")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Success|OK"), //
            @ApiResponse(code = 401, message = "not authorized"), //
            @ApiResponse(code = 403, message = "forbidden"), //
            @ApiResponse(code = 404, message = "not found") //
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseModel> update(@PathVariable("id") Long id,
            @Valid @RequestBody AddressEditModel addressUpdateModel)
            throws ApiIdNotFoundException, ApiWrongParamsException {
        Address address = addressService.findAddress(id);
        Address persistedAddress = addressService.update(address, addressUpdateModel);
        AddressResponseModel response = modelMapper.map(persistedAddress, AddressResponseModel.class);

        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Supprimer un Address par ID", response = Iterable.class, tags = "Address")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Success|OK"), //
            @ApiResponse(code = 401, message = "not authorized"), //
            @ApiResponse(code = 403, message = "forbidden"), //
            @ApiResponse(code = 404, message = "not found") //
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) throws ApiIdNotFoundException {
        Address address = addressService.findAddress(id);
        addressService.delete(address);

        return ResponseEntity.ok().body(address.getId());
    }
}
