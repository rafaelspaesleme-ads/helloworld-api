package com.hello.world.apis.clients;

import com.hello.world.apis.dtos.geonames.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(value = "geoname-api", url = "http://www.geonames.org")
public interface GeonamesApiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/childrenJSON?geonameId={geonameId}", produces = "application/json")
    Optional<GeoContinentsDTO> getContinents(@PathVariable(value = "geonameId") Integer geonameId);

    @RequestMapping(method = RequestMethod.GET, value = "/childrenJSON?geonameId={geonameId}", produces = "application/json")
    Optional<GeoCountriesDTO> getCountries(@PathVariable(value = "geonameId") Integer geonameId);

    @RequestMapping(method = RequestMethod.GET, value = "/childrenJSON?geonameId={geonameId}", produces = "application/json")
    Optional<GeoFederativeUnitsDTO> getFederativeUnits(@PathVariable(value = "geonameId") Integer geonameId);

    @RequestMapping(method = RequestMethod.GET, value = "/childrenJSON?geonameId={geonameId}", produces = "application/json")
    Optional<GeoCitiesDTO> getCities(@PathVariable(value = "geonameId") Integer geonameId);

    @RequestMapping(method = RequestMethod.GET, value = "/childrenJSON?geonameId={geonameId}", produces = "application/json")
    Optional<GeoDistrictsDTO> getDistricts(@PathVariable(value = "geonameId") Integer geonameId);
}
