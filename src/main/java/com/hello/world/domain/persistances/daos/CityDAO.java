package com.hello.world.domain.persistances.daos;

import com.hello.world.domain.entities.City;
import com.hello.world.domain.persistances.repositories.CityRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CityDAO {
    private final CityRepository cityRepository;

    public CityDAO(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Optional<City> saveOrUpdate(City city) {
        return Optional.of(cityRepository.save(city));
    }

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public List<City> findAllByGeonameIdFUOrAll(Integer geonameIdFU) {
        if (geonameIdFU != null) {
            return cityRepository.findAll().stream()
                    .filter(city -> city.getGeonameIdFU().equals(geonameIdFU)).collect(Collectors.toList());
        } else {
            return cityRepository.findAll();
        }
    }

    public Optional<City> findByName(String name) {
        return cityRepository.findAll().stream()
                .filter(city -> city.getName().contains(name)).findFirst();
    }
}
