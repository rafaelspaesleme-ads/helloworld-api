package com.hello.world.apis.dtos.geonames;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class GeoCountriesDTO {
    private Integer totalResultsCount;
    private List<CountriesDTO> geonames;
}
