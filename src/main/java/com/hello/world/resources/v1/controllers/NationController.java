package com.hello.world.resources.v1.controllers;

import com.hello.world.services.clis.NationService;
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
@RequestMapping(value = "/world/continents/nations")
@ApiOperation(value = "Retorna uma lista de países filtrados por continente.")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista de países do continente selecionado, retornado com sucesso."),
        @ApiResponse(code = 204, message = "Não foi encontrado nenhum país para o continente selecionado."),
        @ApiResponse(code = 500, message = "Não foi possivel retornar dados do servidor."),
        @ApiResponse(code = 503, message = "O servidor não esta preparado para lidar com a requisição executada."),
        @ApiResponse(code = 404, message = "Endpoint não encontrado ou mencionado incorretamente."),
})
public class NationController {

    private final NationService nationService;

    public NationController(NationService nationService) {
        this.nationService = nationService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> findAllContinents(@RequestParam String searchPerContinent, @RequestParam String nativeLang) {
        Optional<?> nations = nationService.findAllByContinent(searchPerContinent, nativeLang);
        return nations.isPresent()
                ? ResponseEntity.ok(nations)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        Optional<?> nations = nationService.findAllByContinent(null, null);
        return nations.isPresent()
                ? ResponseEntity.ok(nations)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
