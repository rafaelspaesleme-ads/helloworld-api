package com.hello.world.apis.dtos.countryapi;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with", toBuilder = true)
public class ResponseCountryDTO {
    private String Name;
    private String Alpha2Code;
    private String Alpha3Code;
    private String NativeName;
    private String Region;
    private String SubRegion;
    private String Latitude;
    private String Longitude;
    private Integer Area;
    private Integer NumericCode;
    private String NativeLanguage;
    private String CurrencyCode;
    private String CurrencyName;
    private String CurrencySymbol;
    private String Flag;
    private String FlagPng;
}
