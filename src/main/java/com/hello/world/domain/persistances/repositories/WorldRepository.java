package com.hello.world.domain.persistances.repositories;

import com.hello.world.domain.entities.World;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorldRepository extends JpaRepository<World, Long> {
    Optional<World> findByGeonameId(Integer geonameId);
    Optional<World> findByPlanet(String planet);
}
