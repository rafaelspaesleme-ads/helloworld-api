package com.hello.world.services.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class WorldDTO {
    private Long id;
    @ApiModelProperty(value = "Nome do planeta")
    private String planet;
    private Integer geonameId;
    private Integer countWorld;
    private Boolean status;
}
