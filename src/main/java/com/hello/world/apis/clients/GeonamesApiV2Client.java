package com.hello.world.apis.clients;

import com.hello.world.apis.dtos.geonames.GeoPostalCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(value = "geoname-api2", url = "http://api.geonames.org")
public interface GeonamesApiV2Client {

    @RequestMapping(method = RequestMethod.GET, value = "/postalCodeLookupJSON?postalcode={postalCode}&username={username}",
            produces = "application/json")
    Optional<GeoPostalCodeDTO> getPostalCode(@PathVariable(value = "postalCode") String postalCode, @PathVariable(value = "username") String username);

    @RequestMapping(method = RequestMethod.GET, value = "/postalCodeLookupJSON?postalcode={postalCode}&country={shortNameCountry}&username={username}",
            produces = "application/json")
    Optional<GeoPostalCodeDTO> getPostalCodeAndShortNameContry(@PathVariable(value = "postalCode") String postalCode,
                                                               @PathVariable(value = "shortNameCountry") String shortNameCountry,
                                                               @PathVariable(value = "username") String username);
}
