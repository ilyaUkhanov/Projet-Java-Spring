package com.g6.nfp121.hateoas;

import com.g6.nfp121.models.contact.ContactResponseModel;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ContactModelAssembler
        implements RepresentationModelAssembler<ContactResponseModel, EntityModel<ContactResponseModel>> {

    @Override
    public EntityModel<ContactResponseModel> toModel(ContactResponseModel contact) {
        EntityModel<ContactResponseModel> model = EntityModel.of(contact);
        // model.add(linkTo(methodOn(ContactController.class).getChallenge(contact.getId())).withSelfRel());
        // model.add(linkTo(methodOn(ContactController.class).getAllChallenges())
        // .withRel("challenges"));

        return model;
    }
}
