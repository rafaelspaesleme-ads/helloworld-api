package com.hello.world.services.dtos;

import com.hello.world.domain.entities.Nation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class ContinentDTO {
    private Long id;
    private Integer geonameId;
    private Integer geonameIdWorld;
    private Long idWorld;
    @ApiModelProperty(value = "Nome do continente")
    private String name;
    @ApiModelProperty(value = "Nome do continente em português")
    private String namePtBr;
    @ApiModelProperty(value = "Quantidade populacional deste continente")
    private String population;
    private Integer countContinent;
    private Boolean status;
}
