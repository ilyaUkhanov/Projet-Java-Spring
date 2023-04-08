package com.g6.nfp121.models.address;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddressEditModel {
    @NotBlank(message = "Vous devez précisez la rue")
    @ApiModelProperty(name = "La rue", dataType = "String", required = true, example = "12, rue de Nomeny")
    private String street;

    @NotBlank(message = "Vous devez précisez la ville")
    @ApiModelProperty(name = "La ville", dataType = "String", required = true, example = "Strasbourg")
    private String city;

    @NotNull(message = "Vous devez précisez le code postal")
    @Positive
    @ApiModelProperty(name = "Le code postal", dataType = "String", required = true, example = "67100")
    private Integer zipcode;

    @NotNull(message = "Vous devez précisez les IDs des contacts")
    @Positive
    @ApiModelProperty(name = "Les IDs des contacts", dataType = "[Long]", required = true, example = "[1, 2, 3]")
    private List<Long> contactIDs;
}
