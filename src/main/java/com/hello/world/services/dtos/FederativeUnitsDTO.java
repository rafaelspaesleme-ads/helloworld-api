package com.hello.world.services.dtos;

import com.hello.world.domain.entities.Nation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class FederativeUnitsDTO {
    private Long id;
    @ApiModelProperty(value = "Nome do estado")
    private String name;
    private String shortName;
    private Integer geonameId;
    private Integer geonameIdNation;
    @ApiModelProperty(value = "Quantidade populacional deste estado")
    private Integer population;
    private Long nationId;
    @ApiModelProperty(value = "Nome abreviado do país deste estado")
    private String shortNameNation;
    private Integer countFU;
    private Boolean status;
}
