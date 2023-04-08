package com.g6.nfp121.models.contact;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContactCreateModel {
    @NotNull(message = "Vous devez préciser le prénom")
    @ApiModelProperty(value = "Prénom", dataType = "String", required = true, example = "Jules")
    private String firstname;

    @NotNull(message = "Vous devez préciser le nom")
    @ApiModelProperty(value = "Nom", dataType = "String", required = true, example = "Dupont")
    private String lastname;

    @NotNull(message = "La liste des adresses ne peut être nulle")
    @ApiModelProperty(value = "La liste des IDs des adresses", dataType = "[Long]", required = true, example = "[1, 3, 5]")
    private List<Long> addressesIDs;
}
