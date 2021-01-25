package com.hello.world.domain.persistances.daos;

import com.hello.world.domain.entities.FederativeUnits;
import com.hello.world.domain.persistances.repositories.FederativeUnitsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FederativeUnitsDAO {
    private final FederativeUnitsRepository federativeUnitsRepository;

    public FederativeUnitsDAO(FederativeUnitsRepository federativeUnitsRepository) {
        this.federativeUnitsRepository = federativeUnitsRepository;
    }

    public Optional<FederativeUnits> findById(Long id) {
        return federativeUnitsRepository.findById(id);
    }

    public Integer getCountFU() {
        return federativeUnitsRepository.findAll().size();
    }

    public List<FederativeUnits> findAll() {
        return federativeUnitsRepository.findAll();
    }

    public List<FederativeUnits> findAllByGeonameIdNation(Integer geonameIdNation) {
        return federativeUnitsRepository.findAll().stream()
                .filter(federativeUnits -> federativeUnits.getGeonameIdNation().equals(geonameIdNation))
                .collect(Collectors.toList());
    }

    public Optional<FederativeUnits> save(FederativeUnits federativeUnits) {
        return Optional.of(federativeUnitsRepository.save(federativeUnits));
    }
}
