package com.hello.world.domain.persistances.repositories;

import com.hello.world.domain.entities.Nation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NationRepository extends JpaRepository<Nation, Long> {
    Optional<Nation> findByName(String name);
}
