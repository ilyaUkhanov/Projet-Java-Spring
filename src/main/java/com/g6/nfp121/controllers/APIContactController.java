package com.g6.nfp121.controllers;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import com.g6.nfp121.entities.Contact;
import com.g6.nfp121.entities.Role;
import com.g6.nfp121.exceptions.ApiIdNotFoundException;
import com.g6.nfp121.exceptions.ApiNotAdminException;
import com.g6.nfp121.exceptions.ApiWrongParamsException;
import com.g6.nfp121.hateoas.ContactModelAssembler;
import com.g6.nfp121.models.contact.ContactCreateModel;
import com.g6.nfp121.models.contact.ContactResponseModel;
import com.g6.nfp121.models.contact.ContactUpdateModel;
import com.g6.nfp121.repositories.ContactRepository;
import com.g6.nfp121.security.AuthenticationFacade;
import com.g6.nfp121.services.ContactService;
import com.g6.nfp121.typemaps.ContactTypemap;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

@RequiredArgsConstructor
@RequestMapping(value = "/api/contacts")
@Controller
@Api(value = "API Contact Controller", tags = "Contact")
public class APIContactController {
    private final ContactRepository contactRepository;
    private final ContactService contactService;
    private final ContactModelAssembler modelAssembler;
    private final AuthenticationFacade authenticationFacade;
    private final ContactTypemap typemap;

    @PostConstruct
    public void initialize() {

    }

    @ApiOperation(value = "Créer un Contact", response = Iterable.class, tags = "Contact")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Success|OK"), //
            @ApiResponse(code = 401, message = "not authorized"), //
            @ApiResponse(code = 403, message = "forbidden"), //
            @ApiResponse(code = 404, message = "not found") //
    })
    @PostMapping
    public ResponseEntity<EntityModel<ContactResponseModel>> createContact(
            @RequestBody @Valid ContactCreateModel contactCreateModel)
            throws ApiNotAdminException, ApiIdNotFoundException {
        if (!authenticationFacade.getUserRoles().contains(Role.ROLE_ADMIN)) {
            throw new ApiNotAdminException("Vous");
        }

        Contact contact = contactService.createContact(contactCreateModel);
        ContactResponseModel contactModel = typemap.getMap().map(contact);

        EntityModel<ContactResponseModel> contactHateoas = modelAssembler.toModel(contactModel);

        return ResponseEntity.ok().body(contactHateoas);
    }

    @ApiOperation(value = "Modifier un Contact par ID", response = Iterable.class, tags = "Contact")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Success|OK"), //
            @ApiResponse(code = 401, message = "not authorized"), //
            @ApiResponse(code = 403, message = "forbidden"), //
            @ApiResponse(code = 404, message = "not found") //
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ContactResponseModel>> updateContact(@PathVariable("id") Long id,
                                                                              @RequestBody @Valid ContactUpdateModel contactUpdateModel)
            throws ApiNotAdminException, ApiIdNotFoundException, ApiWrongParamsException
    {
        if (!authenticationFacade.getUserRoles().contains(Role.ROLE_ADMIN)) {
            throw new ApiNotAdminException("Vous");
        }

        Contact contact = contactService.findContact(id);
        Contact updatedContact = contactService.updateContact(contact, contactUpdateModel);
        ContactResponseModel contactModel = typemap.getMap().map(updatedContact);

        EntityModel<ContactResponseModel> contactHateoas = modelAssembler.toModel(contactModel);

        return ResponseEntity.ok().body(contactHateoas);
    }

    @ApiOperation(value = "Récupérer un Contact par ID", response = Iterable.class, tags = "Contact")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ContactResponseModel>> get(@PathVariable("id") Long id)
            throws ApiIdNotFoundException {

        Contact contact = contactService.findContact(id);

        // Transformer l'entité en un modèle
        ContactResponseModel model = typemap.getMap().map(contact);
        EntityModel<ContactResponseModel> contactHateoas = modelAssembler.toModel(model);

        return ResponseEntity.ok().body(contactHateoas);
    }

    @ApiOperation(value = "Supprimer un Contact par ID", response = Iterable.class, tags = "Contact")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized"),
            @ApiResponse(code = 403, message = "forbidden"),
            @ApiResponse(code = 404, message = "not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) throws ApiIdNotFoundException {
        Contact contact = contactService.findContact(id);
        Long idDeleted = contactService.delete(contact);
        String message = "Contact [id: " + idDeleted + "] supprimé avec succès";

        return ResponseEntity.ok().body(message);
    }
}