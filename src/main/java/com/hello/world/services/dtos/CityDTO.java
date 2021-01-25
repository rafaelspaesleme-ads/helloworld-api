package com.hello.world.services.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class CityDTO {
    private Long id;
    @ApiModelProperty(value = "Nome da cidade")
    private String name;
    @ApiModelProperty(value = "Código postal")
    private String zipCode;
    private Integer geonameId;
    private Integer geonameIdFU;
    private Integer geonameIdNation;
    @ApiModelProperty(value = "Quantidade populacional")
    private Integer population;
    private Long federativeUnitsId;
    private Integer countCity;
    private Boolean status;
}
