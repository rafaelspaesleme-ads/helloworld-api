package com.hello.world.services.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class ResponseDTO {
    @ApiModelProperty(value = "Mensagem da resposta da requisição")
    private String message;
    @ApiModelProperty(value = "Quantidade de dados dentro do objeto (Em caso de uma lista)")
    private Integer count;
    @ApiModelProperty(value = "Dados da resposta da requisição")
    private Object data;
}
