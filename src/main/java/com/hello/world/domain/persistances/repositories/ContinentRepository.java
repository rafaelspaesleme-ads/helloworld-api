package com.hello.world.domain.persistances.repositories;

import com.hello.world.domain.entities.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, Long> {
    Optional<Continent> findByName(String name);
    Optional<Continent> findByGeonameId(Integer geonameId);
}
