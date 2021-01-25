package com.hello.world.services.impls;

import com.hello.world.apis.clients.GeonamesApiClient;
import com.hello.world.apis.clients.GeonamesApiV2Client;
import com.hello.world.apis.dtos.geonames.GeoCitiesDTO;
import com.hello.world.apis.dtos.geonames.GeoPostalCodeDTO;
import com.hello.world.domain.entities.City;
import com.hello.world.domain.entities.FederativeUnits;
import com.hello.world.domain.persistances.daos.CityDAO;
import com.hello.world.domain.persistances.daos.FederativeUnitsDAO;
import com.hello.world.services.clis.CityService;
import com.hello.world.services.clis.FederativeUnitsService;
import com.hello.world.services.dtos.CityDTO;
import com.hello.world.services.dtos.FederativeUnitsDTO;
import com.hello.world.services.dtos.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hello.world.services.builders.CityBuilder.*;

@Service
public class CityServiceImpl implements CityService {
    private static final String MSG_SUCCESS_SELECT = "Cidades do estado selecionado, listado com sucesso!";
    private static final String MSG_SUCCESS = "Lista de cidades emitida com sucesso!";
    private static final String MSG_ERROR = "Não há cidades em nossa base de dados, apenas essa lista de estados. Especifique de qual estado você gostaria de ver a listagem de cidades, talvez desta forma você tenha mais sorte.";
    private static final String MSG_SUCCESS_ZIPCODE = "Cidade com o seguinte codigo postal retornado com sucesso!";
    private static final String MSG_SUCCESS_ZIPCODES = "Lista de cidades que contém o número do codigo postal digital, retornado com sucesso!";

    @Value(value = "${geonames.postal-code-lookup-json.username}")
    private String usernameGeoname;

    @Autowired
    private GeonamesApiClient geonamesApiClient;
    @Autowired
    private GeonamesApiV2Client geonamesApiV2Client;

    private final CityDAO cityDAO;
    private final FederativeUnitsDAO federativeUnitsDAO;
    private final FederativeUnitsService federativeUnitsService;

    public CityServiceImpl(CityDAO cityDAO, FederativeUnitsDAO federativeUnitsDAO, FederativeUnitsService federativeUnitsService) {
        this.cityDAO = cityDAO;
        this.federativeUnitsDAO = federativeUnitsDAO;
        this.federativeUnitsService = federativeUnitsService;
    }

    @Override
    public List<CityDTO> findAllByFUInternal(String nation, String nativeLangNation, String federativeUnits) {

        List<FederativeUnitsDTO> federativeUnitsDTOList = federativeUnitsService.findAllByNationInternal(nation, nativeLangNation);

        Optional<FederativeUnitsDTO> fu = federativeUnitsDTOList.stream().filter(federativeUnitsDTO -> federativeUnitsDTO.getName().contains(federativeUnits)).findFirst();

        return findAllCities(federativeUnits, fu.map(FederativeUnitsDTO::getGeonameId).orElse(null));
    }

    @Override
    public Optional<?> findAllByFU(String nation, String nativeLangNation, String federativeUnits) {

        List<FederativeUnitsDTO> federativeUnitsDTOList = federativeUnitsService.findAllByNationInternal(nation, nativeLangNation);

        Optional<FederativeUnitsDTO> fu = federativeUnitsDTOList.stream().filter(federativeUnitsDTO -> federativeUnitsDTO.getName().contains(federativeUnits)).findFirst();

        List<CityDTO> cityDTOList = findAllCities(federativeUnits, fu.map(FederativeUnitsDTO::getGeonameId).orElse(null));

        if (cityDTOList != null) {
            return Optional.of(ResponseDTO.builder()
                    .withMessage(federativeUnits != null ? MSG_SUCCESS_SELECT : MSG_SUCCESS)
                    .withCount(cityDTOList.size())
                    .withData(cityDTOList)
                    .build());
        } else {
            return Optional.of(ResponseDTO.builder()
                    .withMessage(MSG_ERROR)
                    .withCount(federativeUnitsDTOList.size())
                    .withData(federativeUnitsDTOList)
                    .build());
        }
    }

    @Override
    public List<CityDTO> findAllInternal() {
        return findAllCities(null, null);
    }

    @Override
    public List<CityDTO> findAllZipCodeInternal(String searchZipCode, String searchShortNameNation) {


        federativeUnitsService.findAllByShortNameNationInternal(searchShortNameNation);

        return findAllPostalCode(searchZipCode, searchShortNameNation);

    }

    @Override
    public Optional<?> findAllZipCode(String searchZipCode, String searchShortNameNation) {

        List<FederativeUnitsDTO> federativeUnitsDTOList = federativeUnitsService.findAllByShortNameNationInternal(searchShortNameNation);

        List<CityDTO> cityDTOList = findAllPostalCode(searchZipCode, searchShortNameNation);

        if (cityDTOList != null || cityDTOList.size() > 0) {
            return Optional.of(ResponseDTO.builder()
                    .withMessage(cityDTOList.size() > 1 ? MSG_SUCCESS_ZIPCODES : MSG_SUCCESS_ZIPCODE)
                    .withCount(cityDTOList.size())
                    .withData(cityDTOList)
                    .build());
        } else {
            return Optional.of(ResponseDTO.builder()
                    .withMessage(MSG_ERROR)
                    .withCount(federativeUnitsDTOList.size())
                    .withData(federativeUnitsDTOList)
                    .build());
        }
    }

    private List<CityDTO> findAllPostalCode(String zipCode, String shortNameNation) {
        List<CityDTO> cityDTOList = new ArrayList<>();
        Optional<GeoPostalCodeDTO> geoPostalCodeDTO;

        if (shortNameNation != null) {
            geoPostalCodeDTO = geonamesApiV2Client.getPostalCodeAndShortNameContry(zipCode, shortNameNation, String.valueOf(usernameGeoname));
        } else {
            geoPostalCodeDTO = geonamesApiV2Client.getPostalCode(zipCode, String.valueOf(usernameGeoname));
        }


        if (geoPostalCodeDTO.isPresent()) {
            geoPostalCodeDTO.get().getPostalcodes().forEach(paramsPostalCodeDTO -> {
                Optional<City> city = cityDAO.findByName(paramsPostalCodeDTO.getPlaceName());
                if (city.isPresent()) {
                    cityDTOList.add(buildCityDTO(cityDAO.saveOrUpdate(buildCityUpdatePerZipCode(city.get(), paramsPostalCodeDTO.getPostalcode())).get()));
                } else {

                    String nameFU = paramsPostalCodeDTO.getAdminName1().trim().replaceAll("\\s+", " ");

                    List<CityDTO> cities = findAllCities(nameFU, null);
                    if (cities != null) {
                        cities.forEach(cityDTO -> {
                            if (cityDTO.getName().contains(paramsPostalCodeDTO.getPlaceName())) {
                                cityDTOList.add(buildCityDTO(cityDAO.saveOrUpdate(buildCityUpdatePerZipCodeDTO(
                                        cityDTO,
                                        federativeUnitsDAO.findById(cityDTO.getFederativeUnitsId()).get(),
                                        paramsPostalCodeDTO.getPostalcode()))
                                        .get()));
                            }
                        });
                    }
                }
            });
            return cityDTOList;
        } else {
            return null;
        }
    }

    private List<CityDTO> findAllCities(String federativeUnits, Integer geonameIdFU) {
        List<CityDTO> cityDTOList = new ArrayList<>();
        List<City> cities = new ArrayList<>();
        if (geonameIdFU != null) {
            cities = cityDAO.findAllByGeonameIdFUOrAll(geonameIdFU);
        }

        if (cities.size() > 0) {
            cities.forEach(city -> {
                cityDTOList.add(buildCityDTO(city));
            });
            return cityDTOList;
        } else {
            if (federativeUnits != null) {
                Optional<FederativeUnits> federativeUnitsExisting = federativeUnitsDAO.findAll()
                        .stream()
                        .filter(fu -> fu.getName().contains(federativeUnits))
                        .findFirst();

                if (federativeUnitsExisting.isPresent()) {
                    Optional<GeoCitiesDTO> geoCitiesDTO = geonamesApiClient.getCities(federativeUnitsExisting.get().getGeonameId());

                    if (geoCitiesDTO.isPresent()) {
                        geoCitiesDTO.get().getGeonames().forEach(citiesDTO -> {
                            cityDTOList.add(buildCityDTO(cityDAO.saveOrUpdate(buildCityToGeoname(
                                    citiesDTO,
                                    true,
                                    federativeUnitsExisting.get(),
                                    geoCitiesDTO.get().getTotalResultsCount()
                            )).get()));
                        });
                        return cityDTOList;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                federativeUnitsDAO.findAll().forEach(fu -> {
                    Optional<GeoCitiesDTO> geoCitiesDTO = geonamesApiClient.getCities(fu.getGeonameId());
                    geoCitiesDTO.get().getGeonames().forEach(citiesDTO -> {
                        cityDTOList.add(buildCityDTO(cityDAO.saveOrUpdate(buildCityToGeoname(
                                citiesDTO,
                                true,
                                fu,
                                geoCitiesDTO.get().getTotalResultsCount()
                        )).get()));
                    });
                });
                return cityDTOList;
            }
        }
    }
}
