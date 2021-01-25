package com.hello.world.apis.dtos.countryapi;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class CountryDTO {
    private Boolean IsSuccess;
    private String UserMessage;
    private String TechnicalMessage;
    private Integer TotalCount;
    private List<ResponseCountryDTO> Response;
}
