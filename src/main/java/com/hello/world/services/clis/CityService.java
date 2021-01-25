package com.hello.world.services.clis;

import com.hello.world.services.dtos.CityDTO;

import java.util.List;
import java.util.Optional;

public interface CityService {
    Optional<?> findAllByFU(String searchPerNation, String nativeLangNation, String searchPerFederativeUnits);
    List<CityDTO> findAllInternal();
    List<CityDTO> findAllByFUInternal(String nation, String nativeLangNation, String federativeUnits);
    List<CityDTO> findAllZipCodeInternal(String searchZipCode, String searchShortNameNation);
    Optional<?> findAllZipCode(String searchZipCode, String searchShortNameNation);
}
