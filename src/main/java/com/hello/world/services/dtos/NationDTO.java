package com.hello.world.services.dtos;

import com.hello.world.domain.entities.Continent;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class NationDTO {
    private Long id;
    @ApiModelProperty(value = "Nome do país")
    private String name;
    @ApiModelProperty(value = "Nome do país segundo a sua lingua nativa")
    private String nativeName;
    @ApiModelProperty(value = "Nome do país abreviado")
    private String shortName;
    private Integer geonameId;
    private Integer geonameIdContinent;
    @ApiModelProperty(value = "Bandeira do país")
    private String flag;
    @ApiModelProperty(value = "Quantidade populacional deste país")
    private Integer population;
    private Long continentId;
    private Integer countNation;
    private Boolean status;
}
