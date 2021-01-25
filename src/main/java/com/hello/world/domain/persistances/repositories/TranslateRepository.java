package com.hello.world.domain.persistances.repositories;

import com.hello.world.domain.entities.Translate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslateRepository extends JpaRepository<Translate, Long> {
    List<Translate> findAllByStatus(Boolean status);
}
