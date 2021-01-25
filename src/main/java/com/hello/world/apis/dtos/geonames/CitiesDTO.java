package com.hello.world.apis.dtos.geonames;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class CitiesDTO {
    private String adminCode1;
    private String lng;
    private Integer geonameId;
    private String toponymName;
    private String countryId;
    private String fcl;
    private Integer population;
    private String countryCode;
    private String name;
    private String fclName;
    private String countryName;
    private String fcodeName;
    private String adminName1;
    private String lat;
    private String fcode;
}
