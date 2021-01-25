package com.hello.world.services.clis;

import com.hello.world.services.dtos.ContinentDTO;
import com.hello.world.services.dtos.NationDTO;

import java.util.List;
import java.util.Optional;

public interface NationService {
    Optional<?> findAllByContinent(String continent, String nativeLang);
    List<NationDTO> findAllByContinentInternal(String continent, String nativeLang, List<ContinentDTO> continentsDatabase);
    List<NationDTO> findAll();

}
