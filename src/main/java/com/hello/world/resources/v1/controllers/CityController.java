package com.hello.world.resources.v1.controllers;

import com.hello.world.services.clis.CityService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "world/continents/nations/federative-units/cities")
@ApiOperation(value = "Retorna uma lista de cidades filtradas por país e estado ou por código postal.")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista de cidades do país e estado ou codigo postal digitado, retornada com sucesso."),
        @ApiResponse(code = 204, message = "Não foi encontrado nenhuma cidade para o país e estado selecionado ou codigo postal digitado."),
        @ApiResponse(code = 500, message = "Não foi possivel retornar dados do servidor."),
        @ApiResponse(code = 503, message = "O servidor não esta preparado para lidar com a requisição executada."),
        @ApiResponse(code = 404, message = "Endpoint não encontrado ou mencionado incorretamente."),
})
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> findAllByFU(@RequestParam String searchPerNation, @RequestParam String nativeLangNation, @RequestParam String searchPerFederativeUnits) {
        Optional<?> cities = cityService.findAllByFU(searchPerNation, nativeLangNation, searchPerFederativeUnits);
        return cities.isPresent()
                ? ResponseEntity.ok(cities)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/zip-code")
    public ResponseEntity<?> findAllZipCode(@RequestParam String searchZipCode, @RequestParam String searchShortNameNation) {
        Optional<?> cityPerZipCode = cityService.findAllZipCode(searchZipCode, searchShortNameNation);
        return cityPerZipCode.isPresent()
                ? ResponseEntity.ok(cityPerZipCode)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
