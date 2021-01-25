package com.hello.world.resources.v1.controllers;

import com.hello.world.services.clis.ContinentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/world/continents")
@ApiOperation(value = "Retorna uma lista de continentes.")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista de continentes retornado com sucesso."),
        @ApiResponse(code = 204, message = "Não foi encontrado nenhum continente."),
        @ApiResponse(code = 500, message = "Não foi possivel retornar dados do servidor."),
        @ApiResponse(code = 503, message = "O servidor não esta preparado para lidar com a requisição executada."),
        @ApiResponse(code = 404, message = "Endpoint não encontrado ou mencionado incorretamente."),
})
public class ContinentController {
    private final ContinentService continentService;

    public ContinentController(ContinentService continentService) {
        this.continentService = continentService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        Optional<?> continents = continentService.findAll();
        return continents.isPresent()
                ? ResponseEntity.ok(continents)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
