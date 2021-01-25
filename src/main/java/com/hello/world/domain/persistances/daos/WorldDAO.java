package com.hello.world.domain.persistances.daos;

import com.hello.world.domain.entities.World;
import com.hello.world.domain.persistances.repositories.WorldRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WorldDAO {
    private final WorldRepository worldRepository;

    public WorldDAO(WorldRepository worldRepository) {
        this.worldRepository = worldRepository;
    }

    public Optional<World> save(World world) {
        return Optional.of(worldRepository.save(world));
    }

    public Optional<World> findByGeonameId(Integer geonameId) {
        return worldRepository.findByGeonameId(geonameId);
    }

    public Optional<World> findByPlanetAndGeonameId(String planet, Integer geonameId) {
        return worldRepository.findByGeonameId(geonameId)
                .filter(world -> world.getPlanet().equals(planet));
    }
}
