package com.g6.nfp121.hateoas;

import com.g6.nfp121.models.address.AddressResponseModel;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class AddressModelAssembler
                implements RepresentationModelAssembler<AddressResponseModel, EntityModel<AddressResponseModel>> {

        @Override
        public EntityModel<AddressResponseModel> toModel(AddressResponseModel address) {
                EntityModel<AddressResponseModel> model = EntityModel.of(address);

                return model;
        }
}
