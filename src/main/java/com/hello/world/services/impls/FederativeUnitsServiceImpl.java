package com.hello.world.services.impls;

import com.hello.world.apis.clients.GeonamesApiClient;
import com.hello.world.apis.dtos.geonames.GeoFederativeUnitsDTO;
import com.hello.world.domain.entities.FederativeUnits;
import com.hello.world.domain.entities.Nation;
import com.hello.world.domain.persistances.daos.FederativeUnitsDAO;
import com.hello.world.domain.persistances.daos.NationDAO;
import com.hello.world.services.builders.NationBuilder;
import com.hello.world.services.clis.ContinentService;
import com.hello.world.services.clis.FederativeUnitsService;
import com.hello.world.services.clis.NationService;
import com.hello.world.services.clis.TranslateService;
import com.hello.world.services.dtos.*;
import com.hello.world.utils.enums.LanguagesEnum;
import com.hello.world.utils.functions.ConvertionsFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.hello.world.services.builders.FederativeUnitsBuilder.*;
import static com.hello.world.services.builders.FederativeUnitsBuilder.buildFederativeUnitsDTO;
import static com.hello.world.services.builders.NationBuilder.*;
import static com.hello.world.utils.functions.TranslateFunction.*;
import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class FederativeUnitsServiceImpl implements FederativeUnitsService {
    private static final String MSG_SUCCESS_SELECT = "Estados do país selecionado, listado com sucesso!";
    private static final String MSG_SUCCESS = "Lista de estados emitida com sucesso!";
    private static final String MSG_ERROR = "Não há estados em nossa base de dados, apenas essa lista de países. Especifique de qual país você gostaria de ver a listagem de estados, talvez desta forma você tenha mais sorte.";

    @Autowired
    private GeonamesApiClient geonamesApiClient;

    private final FederativeUnitsDAO federativeUnitsDAO;
    private final NationService nationService;
    private final TranslateService translateService;
    private final ContinentService continentService;
    private final NationDAO nationDAO;

    public FederativeUnitsServiceImpl(FederativeUnitsDAO federativeUnitsDAO, NationService nationService, TranslateService translateService, ContinentService continentService, NationDAO nationDAO) {
        this.federativeUnitsDAO = federativeUnitsDAO;
        this.nationService = nationService;
        this.translateService = translateService;
        this.continentService = continentService;
        this.nationDAO = nationDAO;
    }

    @Override
    public Optional<?> findAllByNation(String nation, String nativeLang) {

        String nationFilter = ConvertionsFunction.convertionNameUSA(nation);

        if (nationDAO.findAll().isEmpty()) {
            List<ContinentDTO> continentDTOList = continentService.findAllInternal();
            continentDTOList.forEach(continentDTO -> {
                nationService.findAllByContinentInternal(continentDTO.getName(), "ENGLISH", continentDTOList);
            });
        }

        List<FederativeUnitsDTO> federativeUnitsDTOList = findAllFU(nationFilter, nativeLang);

        if (federativeUnitsDTOList != null) {
            return Optional.of(ResponseDTO.builder()
                    .withMessage(nationFilter != null ? MSG_SUCCESS_SELECT : MSG_SUCCESS)
                    .withCount(federativeUnitsDTOList.size())
                    .withData(federativeUnitsDTOList)
                    .build());
        } else {
            List<NationDTO> nations = nationService.findAll();
            return Optional.of(ResponseDTO.builder()
                    .withMessage(MSG_ERROR)
                    .withCount(nations.size())
                    .withData(nations.size() > 0 ? nations : null)
                    .build());
        }
    }

    @Override
    public List<FederativeUnitsDTO> findAllByNationInternal(String nation, String nativeLang) {

        if (nationDAO.findAll().isEmpty()) {
            List<ContinentDTO> continentDTOList = continentService.findAllInternal();
            continentDTOList.forEach(continentDTO -> {
                nationService.findAllByContinentInternal(continentDTO.getName(), "ENGLISH", continentDTOList);
            });
        }

        return findAllFU(nation, nativeLang);
    }

    @Override
    public List<FederativeUnitsDTO> findAllByShortNameNationInternal(String shortNameNation) {
        List<NationDTO> nationsDtoList = new ArrayList<>();
        List<Nation> nations = nationDAO.findAll();

        if (nations.isEmpty()) {
            List<ContinentDTO> continentDTOList = continentService.findAllInternal();
            continentDTOList.forEach(continentDTO -> {
                nationsDtoList.addAll(nationService.findAllByContinentInternal(continentDTO.getName(), "ENGLISH", continentDTOList));
            });
        } else {
            nations.forEach(nation -> {
                nationsDtoList.add(buildNationDTO(nation));
            });
        }

        Optional<NationDTO> nationDTO = nationsDtoList.stream().filter(n -> n.getShortName().contains(shortNameNation)).findFirst();

        return findAllFU(nationDTO.get().getName(), "ENGLISH");

    }

    @Override
    public List<FederativeUnitsDTO> findAllInternal() {
        List<FederativeUnitsDTO> federativeUnitsDTOList = new ArrayList<>();
        List<NationDTO> nationDTOList = new ArrayList<>();

        List<FederativeUnits> federativeUnitsList = federativeUnitsDAO.findAll();

        if (federativeUnitsList.isEmpty()) {
            List<ContinentDTO> continentDTOList = continentService.findAllInternal();
            continentDTOList.forEach(continentDTO -> {
                nationDTOList.addAll(nationService.findAllByContinentInternal(continentDTO.getName(), "ENGLISH", continentDTOList));
            });
            nationDTOList.forEach(nationDTO -> {
                List<FederativeUnitsDTO> allFU = findAllFU(nationDTO.getName(), "ENGLISH");
                if (allFU != null) {
                    federativeUnitsDTOList.addAll(allFU);
                }
            });
        } else {
            federativeUnitsList.forEach(federativeUnits -> {
                federativeUnitsDTOList.add(buildFederativeUnitsDTO(federativeUnits));
            });
        }

        return federativeUnitsDTOList;
    }

    private List<FederativeUnitsDTO> findAllFU(String nation, String nativeLang) {
        List<FederativeUnitsDTO> federativeUnitsDTOList = new ArrayList<>();

        Optional<TranslateDTO> translateDTO = translateService.translatorInternal(convertNativeLang(nativeLang), nation, LanguagesEnum.EN, true);

        if (translateDTO.isPresent()) {
            Optional<Nation> nationExisting = nationDAO.findByName(translateDTO.get().getNationOutput());
            if (nationExisting.isPresent()) {
                List<FederativeUnits> fu = federativeUnitsDAO.findAllByGeonameIdNation(nationExisting.get().getGeonameId());

                if (fu.size() > 0) {
                    fu.forEach(federativeUnits -> {
                        federativeUnitsDTOList.add(buildFederativeUnitsDTO(federativeUnits));
                    });
                    return federativeUnitsDTOList;
                } else {
                    Optional<GeoFederativeUnitsDTO> federativeUnits = geonamesApiClient.getFederativeUnits(nationExisting.get().getGeonameId());
                    if (federativeUnits.isPresent()) {
                        federativeUnits.get().getGeonames().forEach(fUnitsDTO -> {
                            federativeUnitsDTOList.add(buildFederativeUnitsDTO(federativeUnitsDAO.save(buildFederativeUnits(
                                    fUnitsDTO,
                                    federativeUnits.get().getTotalResultsCount(),
                                    nationExisting.get(),
                                    true
                            )).get()));
                        });
                        return federativeUnitsDTOList;
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
