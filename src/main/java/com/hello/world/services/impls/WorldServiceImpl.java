package com.hello.world.services.impls;

import com.hello.world.domain.entities.World;
import com.hello.world.domain.persistances.daos.WorldDAO;
import com.hello.world.services.clis.WorldService;
import com.hello.world.utils.functions.TranslateFunction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.hello.world.services.builders.WorldBuilder.*;
import static com.hello.world.utils.functions.TranslateFunction.*;

@Service
public class WorldServiceImpl implements WorldService {

    @Value(value = "${geonames.children-json.world.id}")
    private String geonameId;
    @Value(value = "${geonames.children-json.world.planet}")
    private String planet;

    private final WorldDAO worldDAO;

    private WorldServiceImpl(WorldDAO worldDAO) {
        this.worldDAO = worldDAO;
    }

    public Optional<?> findOrSave(String planet) {

        String namePlanet = planet == null ? this.planet : planet;

        String translatePlanet = translatePlanet(namePlanet);

        if (!translatePlanet.equals("Error")) {

            Optional<World> world = worldDAO.findByPlanetAndGeonameId(namePlanet, Integer.parseInt(geonameId));

            if (world.isPresent()) {
                return Optional.of(buildWorldDTO(world.get()));
            } else {
                Optional<World> worldSave = worldDAO.save(buildWorld(Integer.parseInt(geonameId), namePlanet, true));
                if (worldSave.isPresent()) {
                    return Optional.of(buildWorldDTO(worldSave.get()));
                } else {
                    return Optional.empty();
                }
            }

        } else {
            return Optional.empty();
        }

    }
}
