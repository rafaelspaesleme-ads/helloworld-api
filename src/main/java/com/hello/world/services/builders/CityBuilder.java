package com.hello.world.services.builders;

import com.hello.world.apis.dtos.geonames.CitiesDTO;
import com.hello.world.domain.entities.City;
import com.hello.world.domain.entities.FederativeUnits;
import com.hello.world.services.dtos.CityDTO;
import org.springframework.stereotype.Component;

@Component
public class CityBuilder {
    public static City buildCity(CityDTO cityDTO, FederativeUnits federativeUnits) {
        return City.builder()
                .withId(cityDTO.getId())
                .withCountCity(cityDTO.getCountCity())
                .withFederativeUnits(federativeUnits)
                .withGeonameId(cityDTO.getGeonameId())
                .withGeonameIdFU(cityDTO.getGeonameIdFU())
                .withName(cityDTO.getName())
                .withPopulation(cityDTO.getPopulation())
                .withStatus(cityDTO.getStatus())
                .withZipCode(cityDTO.getZipCode())
                .build();
    }

    public static CityDTO buildCityDTO(City city) {
        return CityDTO.builder()
                .withId(city.getId())
                .withCountCity(city.getCountCity())
                .withFederativeUnitsId(city.getFederativeUnits().getId())
                .withGeonameId(city.getGeonameId())
                .withGeonameIdFU(city.getGeonameIdFU())
                .withName(city.getName())
                .withPopulation(city.getPopulation())
                .withStatus(city.getStatus())
                .withZipCode(city.getZipCode())
                .build();
    }

    public static City buildCityToGeoname(CitiesDTO citiesDTO, Boolean status, FederativeUnits federativeUnits, Integer countCities) {
        return City.builder()
                .withPopulation(citiesDTO.getPopulation())
                .withStatus(status)
                .withName(citiesDTO.getName())
                .withGeonameIdFU(federativeUnits.getGeonameId())
                .withGeonameId(citiesDTO.getGeonameId())
                .withFederativeUnits(federativeUnits)
                .withCountCity(countCities)
                .withGeonameIdNation(Integer.parseInt(citiesDTO.getCountryId()))
                .build();
    }

    public static City buildCityUpdatePerZipCode(City city, String zipCode) {
        return City.builder()
                .withId(city.getId())
                .withCountCity(city.getCountCity())
                .withFederativeUnits(city.getFederativeUnits())
                .withGeonameId(city.getGeonameId())
                .withGeonameIdFU(city.getGeonameIdFU())
                .withGeonameIdNation(city.getGeonameIdNation())
                .withName(city.getName())
                .withPopulation(city.getPopulation())
                .withStatus(city.getStatus())
                .withZipCode(zipCode)
                .withGeonameIdNation(city.getGeonameIdNation())
                .build();
    }

    public static City buildCityUpdatePerZipCodeDTO(CityDTO city, FederativeUnits federativeUnits, String zipCode) {
        return City.builder()
                .withId(city.getId())
                .withCountCity(city.getCountCity())
                .withFederativeUnits(federativeUnits)
                .withGeonameId(city.getGeonameId())
                .withGeonameIdFU(city.getGeonameIdFU())
                .withGeonameIdNation(city.getGeonameIdNation())
                .withName(city.getName())
                .withPopulation(city.getPopulation())
                .withStatus(city.getStatus())
                .withZipCode(zipCode)
                .withGeonameIdNation(city.getGeonameIdNation())
                .build();
    }

}
