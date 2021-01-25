package com.hello.world.domain.persistances.daos;

import com.hello.world.domain.entities.Continent;
import com.hello.world.domain.persistances.repositories.ContinentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ContinentDAO {
    private final ContinentRepository continentRepository;

    public ContinentDAO(ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }

    public Integer getCount() {
        return continentRepository.findAll().size();
    }

    public List<Continent> findAll() {
        return continentRepository.findAll();
    }

    public void deleteAll() {
        continentRepository.deleteAll();
    }

    public Optional<Continent> save(Continent continent) {
        return Optional.of(continentRepository.save(continent));
    }

}
