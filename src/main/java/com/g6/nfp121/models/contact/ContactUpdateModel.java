package com.g6.nfp121.models.contact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ContactUpdateModel {

    @ApiModelProperty(value = "Le prénom", dataType = "String", required = true, example = "Jules")
    private String firstname;

    @ApiModelProperty(value = "Le nom", dataType = "String", required = true, example = "Dupont")
    private String lastname;

    @ApiModelProperty(value = "Les ID des Adresses du Contact", dataType = "List", required = true, example = "[1, 2, 3]")
    private List<Long> addressesID;
}
