package com.g6.nfp121.models.contact;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContactResponseModel {
    @ApiModelProperty(value = "Le ID du checkpoint", dataType = "Long", required = true, example = "1")
    Long id;

    @ApiModelProperty(value = "Prénom", dataType = "String", required = true, example = "Jules")
    String firstname;

    @ApiModelProperty(value = "Nom", dataType = "String", required = true, example = "Dupont")
    String lastname;

    @ApiModelProperty(value = "Les ID des adresses du contact", dataType = "List<Long>", required = true, example = "[1, 3, 5]")
    List<Long> addressesIDs;
}
