package com.hello.world.services.clis;

import com.hello.world.services.dtos.ContinentDTO;

import java.util.List;
import java.util.Optional;

public interface ContinentService {
    Optional<?> findAll();
    List<ContinentDTO> findAllInternal();
}
