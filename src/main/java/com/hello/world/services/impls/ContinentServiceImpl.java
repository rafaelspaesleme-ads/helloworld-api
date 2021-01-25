package com.hello.world.services.impls;

import com.hello.world.apis.clients.GeonamesApiClient;
import com.hello.world.apis.dtos.geonames.GeoContinentsDTO;
import com.hello.world.domain.entities.Continent;
import com.hello.world.domain.entities.World;
import com.hello.world.domain.persistances.daos.ContinentDAO;
import com.hello.world.domain.persistances.daos.WorldDAO;
import com.hello.world.services.builders.WorldBuilder;
import com.hello.world.services.clis.ContinentService;
import com.hello.world.services.dtos.ContinentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hello.world.services.builders.ContinentBuilder.*;

@Service
public class ContinentServiceImpl implements ContinentService {

    @Value(value = "${geonames.children-json.world.id}")
    private String geonameIdWorld;
    @Value(value = "${geonames.children-json.world.planet}")
    private String planet;

    private World world;

    @Autowired
    private GeonamesApiClient geonamesApiClient;

    private final ContinentDAO continentDAO;
    private final WorldDAO worldDAO;

    private ContinentServiceImpl(ContinentDAO continentDAO, WorldDAO worldDAO) {
        this.continentDAO = continentDAO;
        this.worldDAO = worldDAO;
    }

    @Override
    public Optional<?> findAll() {
        return Optional.of(findAllContinent());
    }

    @Override
    public List<ContinentDTO> findAllInternal() {
        return findAllContinent();
    }

    protected List<ContinentDTO> findAllContinent() {
        List<ContinentDTO> continentDTOList = new ArrayList<>();

        Integer countContinent = continentDAO.getCount();
        Optional<GeoContinentsDTO> continents = geonamesApiClient.getContinents(Integer.parseInt(geonameIdWorld));
        if (continents.isPresent()) {

            world = worldDAO.findByPlanetAndGeonameId(planet, Integer.parseInt(geonameIdWorld)).orElse(null);
            if (world == null) {
                world = worldDAO.save(WorldBuilder.buildWorld(Integer.parseInt(geonameIdWorld), planet, true)).get();
            }

            Integer countContientApi = continents.get().getTotalResultsCount();
            if (countContientApi.equals(countContinent)) {
                List<Continent> continentList = continentDAO.findAll();
                continentList.forEach(continent -> {
                    continentDTOList.add(buildContinentDTO(continent));
                });
            } else {
                if (countContinent > 0) {
                    continentDAO.deleteAll();
                }
                continents.get().getGeonames().forEach(continentsDTO -> {
                    continentDTOList.add(buildContinentDTO(continentDAO
                            .save(buildContinent(buildContinentDTO(
                                    continentsDTO,
                                    Integer.parseInt(geonameIdWorld),
                                    countContientApi,
                                    true), world))
                            .get()));
                });
            }
            return continentDTOList;
        } else {
            List<Continent> continentList = continentDAO.findAll();
            if (continentList.size() > 0) {
                continentList.forEach(continent -> {
                    continentDTOList.add(buildContinentDTO(continent));
                });
                return continentDTOList;
            } else {
                return null;
            }
        }
    }
}
