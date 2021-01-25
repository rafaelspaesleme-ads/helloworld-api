package com.hello.world.resources.v1.controllers;

import com.hello.world.services.clis.FederativeUnitsService;
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
@RequestMapping(value = "/world/continents/nations/federative-units")
@ApiOperation(value = "Retorna uma lista de estados filtrados por país.")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista de estados do país selecionado, retornado com sucesso."),
        @ApiResponse(code = 204, message = "Não foi encontrado nenhum estado para o país selecionado."),
        @ApiResponse(code = 500, message = "Não foi possivel retornar dados do servidor."),
        @ApiResponse(code = 503, message = "O servidor não esta preparado para lidar com a requisição executada."),
        @ApiResponse(code = 404, message = "Endpoint não encontrado ou mencionado incorretamente."),
})
public class FederativeUnitsController {
    private final FederativeUnitsService federativeUnitsService;

    public FederativeUnitsController(FederativeUnitsService federativeUnitsService) {
        this.federativeUnitsService = federativeUnitsService;
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> findAllByNation(@RequestParam String searchPerNation, @RequestParam String nativeLang) {
        Optional<?> nation = federativeUnitsService.findAllByNation(searchPerNation, nativeLang);
        return nation.isPresent()
                ? ResponseEntity.ok(nation)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
