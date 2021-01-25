package com.hello.world.services.builders;

import com.hello.world.apis.dtos.geonames.FUnitsDTO;
import com.hello.world.domain.entities.FederativeUnits;
import com.hello.world.domain.entities.Nation;
import com.hello.world.services.dtos.FederativeUnitsDTO;
import org.springframework.stereotype.Component;

@Component
public class FederativeUnitsBuilder {
    public static FederativeUnits buildFederativeUnits(FUnitsDTO fUnitsDTO, Integer countFU, Nation nation, Boolean status) {
        return FederativeUnits.builder()
                .withCountFU(countFU)
                .withGeonameId(fUnitsDTO.getGeonameId())
                .withGeonameIdNation(Integer.parseInt(fUnitsDTO.getCountryId()))
                .withName(fUnitsDTO.getName())
                .withNation(nation)
                .withShortNameNation(fUnitsDTO.getCountryCode())
                .withPopulation(fUnitsDTO.getPopulation())
                .withStatus(status)
                .build();
    }

    public static FederativeUnitsDTO buildFederativeUnitsDTO(FederativeUnits federativeUnits) {
        return FederativeUnitsDTO.builder()
                .withId(federativeUnits.getId())
                .withCountFU(federativeUnits.getCountFU())
                .withGeonameId(federativeUnits.getGeonameId())
                .withGeonameIdNation(federativeUnits.getGeonameIdNation())
                .withName(federativeUnits.getName())
                .withNationId(federativeUnits.getNation().getId())
                .withShortNameNation(federativeUnits.getShortNameNation())
                .withPopulation(federativeUnits.getPopulation())
                .withShortName(federativeUnits.getShortName())
                .withStatus(federativeUnits.getStatus())
                .build();
    }

    public static FederativeUnits buildFederativeUnitsDTO(FederativeUnitsDTO federativeUnitsDTO, Nation nation) {
        return FederativeUnits.builder()
                .withId(federativeUnitsDTO.getId())
                .withCountFU(federativeUnitsDTO.getCountFU())
                .withGeonameId(federativeUnitsDTO.getGeonameId())
                .withGeonameIdNation(federativeUnitsDTO.getGeonameIdNation())
                .withName(federativeUnitsDTO.getName())
                .withNation(nation)
                .withShortNameNation(federativeUnitsDTO.getShortNameNation())
                .withPopulation(federativeUnitsDTO.getPopulation())
                .withShortName(federativeUnitsDTO.getShortName())
                .withStatus(federativeUnitsDTO.getStatus())
                .build();
    }
}
