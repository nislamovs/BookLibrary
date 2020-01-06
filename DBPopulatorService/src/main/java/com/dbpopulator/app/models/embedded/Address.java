package com.dbpopulator.app.models.embedded;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Address {

    private String city;
    private String street;
    private String postalCode;
    private String additionalInfo;
}
