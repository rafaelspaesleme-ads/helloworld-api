package com.hello.world.domain.persistances.daos;

import com.hello.world.domain.entities.Translate;
import com.hello.world.domain.persistances.repositories.TranslateRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TranslateDAO {
    private final TranslateRepository translateRepository;

    public TranslateDAO(TranslateRepository translateRepository) {
        this.translateRepository = translateRepository;
    }

    public Optional<Translate> saveOrUpdate(Translate translate) {
        return Optional.of(translateRepository.save(translate));
    }
}
