package com.g6.nfp121.models.contact;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "contact")
@XmlType(propOrder = { "id", "firstname", "lastname", "addressesIDs" })
public class ContactResponseXMLModel {
    @ApiModelProperty(value = "Le ID du checkpoint", dataType = "Long", required = true, example = "1")
    @XmlElement(name = "id")
    Long id;

    @ApiModelProperty(value = "Prénom", dataType = "String", required = true, example = "Jules")
    @XmlElement(name = "firstname")
    String firstname;

    @ApiModelProperty(value = "Nom", dataType = "String", required = true, example = "Dupont")
    @XmlElement(name = "lastname")
    String lastname;

    @ApiModelProperty(value = "Les ID des adresses du contact", dataType = "List<Long>", required = true, example = "[1, 3, 5]")
    @XmlElement(name = "addressesIDs")
    List<Long> addressesIDs;

    public void setAddressesIDs(List<Long> addressesIDs) {
        this.addressesIDs = addressesIDs;
    }
}
