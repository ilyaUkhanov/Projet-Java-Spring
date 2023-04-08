package com.g6.nfp121.models.contact;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContactRemoveAddressModel {
    @NotNull(message = "ID d'adresse ne peut être nul")
    @Positive(message = "ID d'adresse doit être un nombre positif")
    @ApiModelProperty(value = "ID du adresse à enlever", dataType = "Long", required = true, example = "1")
    private Long addressID;
}
