package com.hello.world.apis.dtos.geonames;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostalCodeDTO {
    private String adminCode2;
    private String adminCode1;
    private String countryCode;
    private String postalcode;
    private String adminName1;
    private String placeName;

    public PostalCodeDTO() {
    }
}
