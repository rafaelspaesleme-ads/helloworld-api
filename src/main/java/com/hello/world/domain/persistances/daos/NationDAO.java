package com.hello.world.domain.persistances.daos;

import com.hello.world.domain.entities.Nation;
import com.hello.world.domain.persistances.repositories.NationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class NationDAO {
    private final NationRepository nationRepository;

    public NationDAO(NationRepository nationRepository) {
        this.nationRepository = nationRepository;
    }

    public Optional<Nation> saveOrUpdate(Nation nation) {
        return Optional.of(nationRepository.save(nation));
    }

    public List<Nation> findAllByStatus(Boolean status) {
        return nationRepository.findAll().stream()
                .filter(nation -> nation.getStatus().equals(status)).collect(Collectors.toList());
    }

    public Integer getCount() {
        return nationRepository.findAll().size();
    }

    public Integer getCountPerContinent(Integer geonameIdContinent) {
        List<Nation> nationList = nationRepository.findAll().stream().filter(nation -> nation.getGeonameIdContinent().equals(geonameIdContinent)).collect(Collectors.toList());
        return nationList.size();
    }

    public List<Nation> findAll() {
        return nationRepository.findAll();
    }

    public Optional<Nation> findByName(String name) {
        return nationRepository.findByName(name);
    }

}
