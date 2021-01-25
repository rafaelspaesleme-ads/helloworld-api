package com.hello.world.apis.clients;

import com.hello.world.apis.dtos.countryapi.CountryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(value = "countryapi", url = "http://countryapi.gear.host/")
public interface CountryApiClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/Country/getCountries", produces = "application/json")
    Optional<CountryDTO> getFullCountries();

    @RequestMapping(method = RequestMethod.GET, value = "/v1/Country/getCountries?pNativeName={nativeName}", produces = "application/json")
    Optional<CountryDTO> getNativeName(@PathVariable(value = "nativeName") String nativeName);

    @RequestMapping(method = RequestMethod.GET, value = "/v1/Country/getCountries?pName={name}", produces = "application/json")
    Optional<CountryDTO> getNameEng(@PathVariable(value = "name") String name);

    @RequestMapping(method = RequestMethod.GET, value = "/v1/Country/getCountries?pSubRegion={subRegion}", produces = "application/json")
    Optional<CountryDTO> getSubRegion(@PathVariable(value = "subRegion") String subRegion);

    @RequestMapping(method = RequestMethod.GET, value = "/v1/Country/getCountries?pRegion={region}", produces = "application/json")
    Optional<CountryDTO> getRegion(@PathVariable(value = "region") String region);
}
