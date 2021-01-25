package com.hello.world.services.impls;

import com.hello.world.apis.clients.CountryApiClient;
import com.hello.world.apis.clients.GeonamesApiClient;
import com.hello.world.apis.dtos.countryapi.CountryDTO;
import com.hello.world.apis.dtos.countryapi.ResponseCountryDTO;
import com.hello.world.apis.dtos.geonames.GeoCountriesDTO;
import com.hello.world.domain.entities.Continent;
import com.hello.world.domain.entities.Nation;
import com.hello.world.domain.entities.World;
import com.hello.world.domain.persistances.daos.ContinentDAO;
import com.hello.world.domain.persistances.daos.NationDAO;
import com.hello.world.domain.persistances.daos.WorldDAO;
import com.hello.world.services.builders.ContinentBuilder;
import com.hello.world.services.clis.ContinentService;
import com.hello.world.services.clis.NationService;
import com.hello.world.services.dtos.ContinentDTO;
import com.hello.world.services.dtos.NationDTO;
import com.hello.world.services.dtos.ResponseDTO;
import com.hello.world.utils.enums.ApiEnum;
import com.hello.world.utils.functions.ConvertionsFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hello.world.services.builders.ContinentBuilder.*;
import static com.hello.world.services.builders.NationBuilder.*;
import static com.hello.world.utils.functions.ConvertionsFunction.*;
import static com.hello.world.utils.functions.TranslateFunction.*;

@Service
public class NationServiceImpl implements NationService {

    private static final String MSG_SUCCESS_SELECT = "Nações do continente selecionado, listado com sucesso!";
    private static final String MSG_SUCCESS = "Lista de nações emitida com sucesso!";
    private static final String MSG_ERROR = "Não há nações em nossa base de dados, apenas essa lista de continentes. Especifique de qual continente você gostaria de ver a listagem de nações, talvez desta forma você tenha mais sorte.";

    @Value(value = "${geonames.children-json.world.id}")
    private String geonamesWorldId;

    @Autowired
    private GeonamesApiClient geonamesApiClient;

    @Autowired
    private CountryApiClient countryApiClient;

    private final NationDAO nationDAO;
    private final ContinentService continentService;
    private final WorldDAO worldDAO;
    private final ContinentDAO continentDAO;

    private NationServiceImpl(NationDAO nationDAO, ContinentService continentService, WorldDAO worldDAO, ContinentDAO continentDAO) {
        this.nationDAO = nationDAO;
        this.continentService = continentService;
        this.worldDAO = worldDAO;
        this.continentDAO = continentDAO;
    }

    @Override
    public Optional<?> findAllByContinent(String continent, String nativeLang) {
        List<ContinentDTO> continentsDatabase = new ArrayList<>();
        List<Continent> continents = continentDAO.findAll();
        List<NationDTO> allNations = new ArrayList<>();
        if (continents.isEmpty()) {
            continentsDatabase.addAll(continentService.findAllInternal());
        } else {
            continents.forEach(c -> continentsDatabase.add(buildContinentDTO(c)));
        }

        if (continent != null) {
             allNations.addAll(findAllNations(continent, continentsDatabase, nativeLang));
        } else {
            allNations.addAll(findAll());
        }

        return Optional.of(ResponseDTO.builder()
                .withMessage(continent == null ? MSG_SUCCESS : MSG_SUCCESS_SELECT)
                .withCount(allNations.size())
                .withData(allNations)
                .build());
    }

    @Override
    public List<NationDTO> findAllByContinentInternal(String continent, String nativeLang, List<ContinentDTO> continentsDatabase) {
        return findAllNations(continent, continentsDatabase, nativeLang);
    }

    @Override
    public List<NationDTO> findAll() {
        List<NationDTO> nationDTOList = new ArrayList<>();
        List<ContinentDTO> continents = continentService.findAllInternal();

        List<Nation> nations = nationDAO.findAll();

        if (nations.size() > 0) {
            nations.forEach(nation -> {
                nationDTOList.add(buildNationDTO(nation));
            });
        } else {
            continents.forEach(continentDTO -> {
                Optional<GeoCountriesDTO> countries = geonamesApiClient.getCountries(continentDTO.getGeonameId());
                countries.get().getGeonames().forEach(countriesDTO -> {
                    Optional<CountryDTO> countryApi = countryApiClient.getNameEng(countriesDTO.getName());
                    Optional<ResponseCountryDTO> responseCountryDTO = countryApi.get()
                            .getResponse()
                            .stream()
                            .filter(r -> r.getName().equals(countriesDTO.getName()))
                            .findFirst();
                    Optional<Continent> continent = continentDAO.findAll()
                            .stream()
                            .filter(c -> c.getGeonameId().equals(continentDTO.getGeonameId()))
                            .findFirst();
                    nationDTOList.add(buildNationDTO(nationDAO.saveOrUpdate(buildNationGeonameApiToSave(
                            countriesDTO,
                            responseCountryDTO.orElse(null),
                            continent.get(),
                            countries.get().getTotalResultsCount(),
                            true)).get()));
                });
            });
        }

        return nationDTOList;
    }

    public List<NationDTO> findAllNationsPerContinent() {
        List<NationDTO> nationDTOList = new ArrayList<>();

        continentDAO.findAll().forEach(continent -> {
            Optional<GeoCountriesDTO> countries = geonamesApiClient.getCountries(continent.getGeonameId());
            Optional<CountryDTO> countryDTO = countryApiClient.getNameEng(continent.getName());
            countries.ifPresent(geoCountriesDTO -> {
                geoCountriesDTO.getGeonames().forEach(countriesDTO -> {

                    Optional<ResponseCountryDTO> responseCountryDTO = countryDTO.get()
                            .getResponse()
                            .stream().filter(r -> r.getName().equals(countriesDTO.getName()))
                            .findFirst();

                    nationDTOList.add(buildNationDTO(nationDAO.saveOrUpdate(buildNationGeonameApiToSave(
                            countriesDTO,
                            responseCountryDTO.orElse(null),
                            continent,
                            geoCountriesDTO.getTotalResultsCount(),
                            true)).get()));

                });
            });
        });
        return nationDTOList;
    }


    public List<NationDTO> findAllNations(String continent, List<ContinentDTO> continentsDatabase, String nativeLang) {
        String continentToEngCountryApi = translatorContinentNativeLangToEng(continent, ApiEnum.COUNTRY, convertStringToNLE(nativeLang));
        String continentToEngGeonameApi = translatorContinentNativeLangToEng(continent, ApiEnum.GEONAME, convertStringToNLE(nativeLang));
        List<NationDTO> nationDTOList = new ArrayList<>();
        World world = worldDAO.findByGeonameId(Integer.parseInt(geonamesWorldId)).orElse(null);

        if (continentsDatabase.size() > 0) {
            Optional<ContinentDTO> continentExistingDatabase = continentsDatabase.stream().filter(continentDTO ->
                    continentDTO.getName().equals(continentToEngGeonameApi) || continentDTO.getName().equals(continentToEngCountryApi)).findFirst();

            Optional<CountryDTO> region = countryApiClient.getRegion(continentToEngCountryApi);
            Optional<CountryDTO> subRegion = countryApiClient.getSubRegion(continentToEngCountryApi);

            if (continentExistingDatabase.isPresent()) {
                Integer countNationDatabase = nationDAO.getCountPerContinent(continentExistingDatabase.get().getGeonameId());
                Optional<GeoCountriesDTO> countriesGeonameApi = geonamesApiClient.getCountries(continentExistingDatabase.get().getGeonameId());
                Continent buildContinent = buildContinent(continentExistingDatabase.get(), world);

                return countriesGeonameApi.map(geoCountriesDTO ->
                        findAllRestWorldGeonameApi(geoCountriesDTO, countNationDatabase, buildContinent, nationDTOList, continent))
                        .orElseGet(() -> region.map(countryDTO ->
                                findAllRestWorldCountryApi(countryDTO, countNationDatabase, buildContinent, nationDTOList, null))
                                .orElseGet(() -> subRegion.map(countryDTO ->
                                        findAllRestWorldCountryApi(countryDTO, countNationDatabase, buildContinent, nationDTOList, null)).orElse(null)));
            } else {
                Integer count = nationDAO.getCount();

                if (count > 0) {
                    nationDAO.findAll().forEach(nation -> {
                        nationDTOList.add(buildNationDTO(nation));
                    });
                    return nationDTOList;

                } else {
                    return null;
                }

            }
        } else {
            return null;
        }

    }

    private List<NationDTO> findAllRestWorldGeonameApi(GeoCountriesDTO countriesGeonameApi, Integer countNationDatabase, Continent continent, List<NationDTO> nationDTOList, String valueRequest) {

        if (countriesGeonameApi.getTotalResultsCount().equals(countNationDatabase)) {
            if (valueRequest != null) {
                List<Nation> nations = nationDAO.findAll()
                        .stream()
                        .filter(nation -> nation.getGeonameIdContinent().equals(continent.getGeonameId()))
                        .collect(Collectors.toList());
                nations.forEach(nation -> {
                    nationDTOList.add(buildNationDTO(nation));
                });
            } else {
                List<Nation> nations = nationDAO.findAll();
                nations.forEach(nation -> {
                    nationDTOList.add(buildNationDTO(nation));
                });
            }
        } else {
            countriesGeonameApi.getGeonames().forEach(countriesDTO -> {
                Optional<CountryDTO> countryDTO = countryApiClient.getNameEng(countriesDTO.getName());
                if (countryDTO.isPresent()) {
                    Optional<ResponseCountryDTO> responseCountryDTO = countryDTO.get().getResponse()
                            .stream()
                            .filter(r -> r.getName().equals(countriesDTO.getName()))
                            .findFirst();

                    nationDTOList.add(buildNationDTO(nationDAO.saveOrUpdate(buildNationGeonameApiToSave(
                            countriesDTO,
                            responseCountryDTO.orElse(null),
                            continent,
                            countriesGeonameApi.getTotalResultsCount(),
                            true)).get()));

                }
            });
        }
        return nationDTOList;
    }

    private List<NationDTO> findAllRestWorldCountryApi(CountryDTO countryDTO, Integer countNationDatabase, Continent continent, List<NationDTO> nationDTOList, World world) {

        if (countNationDatabase == 1) {
            List<Nation> nations = nationDAO.findAll()
                    .stream()
                    .filter(nation -> nation.getGeonameIdContinent().equals(continent != null ? continent.getCountContinent() : countryDTO.getTotalCount()))
                    .collect(Collectors.toList());
            nations.forEach(nation -> {
                nationDTOList.add(buildNationDTO(nation));
            });
        } else {
            countryDTO.getResponse().forEach(responseCountryDTO -> {
                nationDTOList.add(buildNationDTO(nationDAO.saveOrUpdate(buildNationCountryApiToSave(
                        responseCountryDTO,
                        continent != null ? continent : continentDAO.save(new Continent(
                                null,
                                countryDTO.getTotalCount(),
                                Integer.parseInt(geonamesWorldId),
                                responseCountryDTO.getSubRegion(),
                                translatorContinentToPtBr(responseCountryDTO.getSubRegion()),
                                "0",
                                world,
                                null,
                                true)).orElse(null),
                        countryDTO.getTotalCount(),
                        true
                )).get()));
            });
        }
        return nationDTOList;

    }

}
