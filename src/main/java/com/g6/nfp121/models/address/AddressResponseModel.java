package com.g6.nfp121.models.address;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class AddressResponseModel {
    @ApiModelProperty(name = "La rue", dataType = "String", required = true, example = "12, rue de Nomeny")
    private String street;

    @ApiModelProperty(name = "La ville", dataType = "String", required = true, example = "Strasbourg")
    private String city;

    @Positive
    @ApiModelProperty(name = "Le code postal", dataType = "String", required = true, example = "67100")
    private Integer zipcode;
}
