package com.hello.world.services.builders;

import com.hello.world.apis.dtos.geonames.ContinentsDTO;
import com.hello.world.domain.entities.Continent;
import com.hello.world.domain.entities.World;
import com.hello.world.domain.persistances.daos.WorldDAO;
import com.hello.world.services.dtos.ContinentDTO;
import org.springframework.stereotype.Component;

import static com.hello.world.utils.functions.TranslateFunction.*;

@Component
public class ContinentBuilder {

    public static ContinentDTO buildContinentDTO(Continent continent) {
        return ContinentDTO.builder()
                .withId(continent.getId())
                .withIdWorld(continent.getWorld().getId())
                .withCountContinent(continent.getCountContinent())
                .withGeonameId(continent.getGeonameId())
                .withGeonameIdWorld(continent.getGeonameIdWorld())
                .withName(continent.getName())
                .withNamePtBr(continent.getNamePtBr())
                .withPopulation(continent.getPopulation())
                .withStatus(continent.getStatus())
                .build();
    }

    public static ContinentDTO buildContinentDTO(ContinentsDTO continentsDTO, Integer geonameIdWorld, Integer count, Boolean status) {
        return ContinentDTO.builder()
                .withName(continentsDTO.getName())
                .withNamePtBr(translatorContinentToPtBr(continentsDTO.getName()))
                .withPopulation(continentsDTO.getPopulation())
                .withGeonameIdWorld(geonameIdWorld)
                .withGeonameId(continentsDTO.getGeonameId())
                .withCountContinent(count)
                .withStatus(status)
                .build();
    }

    public static Continent buildContinent(ContinentDTO continentDTO, World world) {
        return Continent.builder()
                .withId(continentDTO.getId())
                .withWorld(world)
                .withCountContinent(continentDTO.getCountContinent())
                .withGeonameId(continentDTO.getGeonameId())
                .withGeonameIdWorld(continentDTO.getGeonameIdWorld())
                .withName(continentDTO.getName())
                .withNamePtBr(continentDTO.getNamePtBr())
                .withPopulation(continentDTO.getPopulation())
                .withStatus(continentDTO.getStatus())
                .build();
    }

    public static Continent buildContinentCentralAmerica(Integer geonameId, Integer geonameIdWorld, World world, String name, Boolean status) {
        return Continent.builder()
                .withGeonameId(geonameId)
                .withName(name)
                .withStatus(status)
                .withPopulation("0")
                .withWorld(world)
                .withGeonameIdWorld(geonameIdWorld)
                .withNamePtBr(translatorContinentToPtBr(name))
                .withCountContinent(0)
                .build();

    }
}
