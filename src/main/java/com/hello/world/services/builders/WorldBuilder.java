package com.hello.world.services.builders;

import com.hello.world.domain.entities.World;
import com.hello.world.services.dtos.WorldDTO;
import org.springframework.stereotype.Component;

@Component
public class WorldBuilder {
    public static WorldDTO buildWorldDTO(World world) {
        return WorldDTO.builder()
                .withCountWorld(world.getCountWorld())
                .withGeonameId(world.getGeonameId())
                .withId(world.getId())
                .withPlanet(world.getPlanet())
                .withStatus(world.getStatus())
                .build();
    }

    public static World buildWorld(Integer geonameId, String planet, Boolean status) {
        return World.builder()
                .withGeonameId(geonameId)
                .withPlanet(planet)
                .withStatus(status)
                .build();
    }
}
