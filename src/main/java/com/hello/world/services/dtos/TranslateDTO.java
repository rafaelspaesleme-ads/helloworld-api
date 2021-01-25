package com.hello.world.services.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class TranslateDTO {
    private Long id;
    @ApiModelProperty(value = "Linguagem que deseja traduzir o nome do país")
    private String language;
    @ApiModelProperty(value = "Nome do país que deseja traduzir")
    private String nationInput;
    @ApiModelProperty(value = "Nome do país traduzido")
    private String nationOutput;
    private Boolean status;
}
