package com.hello.world.domain.persistances.repositories;

import com.hello.world.domain.entities.FederativeUnits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FederativeUnitsRepository extends JpaRepository<FederativeUnits, Long> {
}
