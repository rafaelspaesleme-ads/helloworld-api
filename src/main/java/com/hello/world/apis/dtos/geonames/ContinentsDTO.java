package com.hello.world.apis.dtos.geonames;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class ContinentsDTO {
    private String lng;
    private Integer geonameId;
    private String name;
    private String fclName;
    private String toponymName;
    private String fcodeName;
    private String adminName1;
    private String lat;
    private String fcl;
    private String fcode;
    private String population;
}
