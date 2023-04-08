package com.g6.nfp121.controllers;

import com.g6.nfp121.entities.Address;
import com.g6.nfp121.entities.AddressFactory;
import com.g6.nfp121.entities.Contact;
import com.g6.nfp121.exceptions.ApiIdNotFoundException;
import com.g6.nfp121.exceptions.ApiWrongParamsException;
import com.g6.nfp121.hateoas.ContactModelAssembler;
import com.g6.nfp121.models.address.AddressCreateModel;
import com.g6.nfp121.models.address.AddressEditModel;
import com.g6.nfp121.models.address.AddressResponseModel;
import com.g6.nfp121.models.contact.ContactResponseModel;
import com.g6.nfp121.models.contact.ContactResponseXMLModel;
import com.g6.nfp121.services.AddressService;
import com.g6.nfp121.services.ContactService;
import com.g6.nfp121.typemaps.ContactTypemap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class APIXMLController {
    private final ContactService contactService;
    private final ModelMapper modelMapper;
    private final ContactModelAssembler modelAssembler;
    private final ContactTypemap typemap;

    @RequestMapping(value="/xml", params = "action=listContacts", produces= MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody ResponseEntity<List<ContactResponseModel>>
                listContacts(@NotNull @RequestParam(name = "action") String action)
            throws ApiIdNotFoundException
    {
        List<Contact> contacts = contactService.getAllContacts();

        // Transformer l'entité en un modèle
        List<ContactResponseModel> modelCollection = contacts.stream()
                .map(userSession -> typemap.getMap().map(userSession)).collect(Collectors.toList());

        return ResponseEntity.ok().body(modelCollection);
    }

    @RequestMapping(value="/xml", params = "action=getContact", produces= MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody ResponseEntity<ContactResponseModel>
                            getContact(@RequestParam(name = "action") String action,
                                       @NotNull @RequestParam("id") Long id) throws Exception
    {
        if (id != null) {
            Contact contact = contactService.findContact(id);

            // Transformer l'entité en un modèle
            ContactResponseModel model = typemap.getMap().map(contact);
//            EntityModel<ContactResponseModel> contactHateoas = modelAssembler.toModel(model);

            return ResponseEntity.ok().body(model);
        }

        throw new Exception("Erreur");
    }

    @RequestMapping(value="/xml", params = "action=deleteContact")
    public @ResponseBody ResponseEntity<String> deleteContact(
                    @RequestParam(name = "action")  String action, @Nullable @RequestParam("id") Long id) throws ApiIdNotFoundException
    {
        Contact contact = contactService.findContact(id);
        Long idDeleted = contactService.delete(contact);
        String message = "Contact [id: " + idDeleted + "] supprimé avec succès";

        return ResponseEntity.ok().body(message);
    }
}
