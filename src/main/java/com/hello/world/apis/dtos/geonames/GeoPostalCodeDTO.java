package com.hello.world.apis.dtos.geonames;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeoPostalCodeDTO {
    private List<PostalCodeDTO> postalcodes;

    public GeoPostalCodeDTO() {
    }
}
