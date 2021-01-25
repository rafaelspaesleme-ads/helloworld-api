package com.hello.world.resources.v1.controllers;

import com.hello.world.services.clis.TranslateService;
import com.hello.world.services.dtos.TranslateDTO;
import com.hello.world.utils.enums.LanguagesEnum;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Optional;

@RestController
@RequestMapping(value = "/translate", produces="application/json", consumes="application/json")
@ApiOperation(value = "Retorna uma tradução pré-definida pelo usuário, do nome de um país digitado pela linguagem nativa desse mesmo usuário.")
@ApiResponses(value = {
        @ApiResponse(code = 201, message = "Dados traduzidos com sucesso."),
        @ApiResponse(code = 501, message = "Não foi possivel para o servidor, implementar a tradução de dados enviados nessa requisição."),
        @ApiResponse(code = 500, message = "Não foi possivel retornar dados do servidor."),
        @ApiResponse(code = 503, message = "O servidor não esta preparado para lidar com a requisição executada."),
        @ApiResponse(code = 404, message = "Endpoint não encontrado ou mencionado incorretamente."),
})
public class TranslateController {
    private final TranslateService translateService;

    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }

    @ApiIgnore
    @PostMapping(value = "/to")
    public ResponseEntity<?> saveTranslate(@RequestBody TranslateDTO translateDTO, @RequestParam(value = "langTranslateTo") String langTranslateTo) {
        LanguagesEnum nativeLang = LanguagesEnum.valueOf(translateDTO.getLanguage());
        LanguagesEnum lang = LanguagesEnum.valueOf(langTranslateTo);
        Optional<?> translate = translateService.translator(nativeLang, translateDTO.getNationInput(), lang, translateDTO.getStatus());
        return translate.isPresent()
                ? ResponseEntity.status(HttpStatus.CREATED).body(translate)
                : ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
