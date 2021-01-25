package com.hello.world.services.clis;

import com.hello.world.services.dtos.FederativeUnitsDTO;

import java.util.List;
import java.util.Optional;

public interface FederativeUnitsService {
    Optional<?> findAllByNation(String nation, String nativeLang);
    List<FederativeUnitsDTO> findAllByNationInternal(String nation, String nativeLang);
    List<FederativeUnitsDTO> findAllInternal();
    List<FederativeUnitsDTO> findAllByShortNameNationInternal(String shortNameNation);
}
