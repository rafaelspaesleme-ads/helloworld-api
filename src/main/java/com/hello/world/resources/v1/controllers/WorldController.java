package com.hello.world.resources.v1.controllers;

import com.hello.world.services.clis.WorldService;
import com.hello.world.utils.functions.TemplatesFunction;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@RestController
@RequestMapping
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "O planeta digitado foi encontrado com sucesso."),
        @ApiResponse(code = 204, message = "Não foi possivél encontrar o planeta digitado em nossa base de dados."),
        @ApiResponse(code = 500, message = "Não foi possivel retornar dados do servidor."),
        @ApiResponse(code = 503, message = "O servidor não esta preparado para lidar com a requisição executada."),
        @ApiResponse(code = 404, message = "Endpoint não encontrado ou mencionado incorretamente."),
})
public class WorldController {
    public final WorldService worldService;
    private final TemplatesFunction templatesFunction;

    public WorldController(WorldService worldService, TemplatesFunction templatesFunction) {
        this.worldService = worldService;
        this.templatesFunction = templatesFunction;
    }

    @GetMapping(value = "/world/search")
    public ResponseEntity<?> findOrSave(@RequestParam String planet) {
        Optional<?> world = worldService.findOrSave(planet);
        return world.isPresent()
                ? ResponseEntity.ok(world)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/world")
    public ResponseEntity<?> findOrSave() {
        Optional<?> world = worldService.findOrSave(null);
        return world.isPresent()
                ? ResponseEntity.ok(world)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiIgnore
    @GetMapping(value = "/")
    public ResponseEntity<?> start() {
        String s = templatesFunction.initalPageHtml();
        return s != null ? ResponseEntity.ok(s) : ResponseEntity.noContent().build();
    }
}
