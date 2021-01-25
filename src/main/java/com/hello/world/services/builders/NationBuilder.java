package com.hello.world.services.builders;

import com.hello.world.apis.dtos.countryapi.CountryDTO;
import com.hello.world.apis.dtos.countryapi.ResponseCountryDTO;
import com.hello.world.apis.dtos.geonames.CountriesDTO;
import com.hello.world.domain.entities.Continent;
import com.hello.world.domain.entities.Nation;
import com.hello.world.services.dtos.NationDTO;
import org.springframework.stereotype.Component;

@Component
public class NationBuilder {
    public static NationDTO buildNationDTO(Nation nation) {
        return NationDTO.builder()
                .withId(nation.getId())
                .withName(nation.getName())
                .withNativeName(nation.getNativeName())
                .withShortName(nation.getShortName())
                .withContinentId(nation.getContinent().getId())
                .withStatus(nation.getStatus())
                .withFlag(nation.getFlag())
                .withCountNation(nation.getCountNation())
                .withGeonameId(nation.getGeonameId())
                .withGeonameIdContinent(nation.getGeonameIdContinent())
                .withPopulation(nation.getPopulation())
                .build();
    }

    public static Nation buildNationGeonameApiToSave(CountriesDTO countriesDTO, ResponseCountryDTO responseCountryDTO, Continent continent, Integer countNations, Boolean status) {
        return Nation.builder()
                .withContinent(continent)
                .withCountNation(countNations)
                .withFlag(responseCountryDTO != null ? responseCountryDTO.getFlag() : null)
                .withGeonameId(countriesDTO.getGeonameId())
                .withGeonameIdContinent(continent.getGeonameId())
                .withName(countriesDTO.getName())
                .withShortName(countriesDTO.getCountryCode())
                .withPopulation(countriesDTO.getPopulation())
                .withNativeName(responseCountryDTO != null ? responseCountryDTO.getNativeName() : null)
                .withStatus(status)
                .build();
    }

    public static Nation buildNationCountryApiToSave(ResponseCountryDTO responseCountryDTO, Continent continent, Integer countNations, Boolean status) {
        return Nation.builder()
                .withContinent(continent)
                .withCountNation(countNations)
                .withFlag(responseCountryDTO.getFlag())
                .withGeonameId(responseCountryDTO.getNumericCode())
                .withGeonameIdContinent(continent.getGeonameId())
                .withName(responseCountryDTO.getName())
                .withShortName(responseCountryDTO.getAlpha2Code())
                .withNativeName(responseCountryDTO.getNativeName())
                .withStatus(status)
                .build();
    }
}
