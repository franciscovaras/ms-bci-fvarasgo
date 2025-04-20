package com.fvarasgo.bci.demo.msbcifvarasgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {
    @JsonProperty("numero")
    private String number;
    @JsonProperty("codigoCiudad")
    private String citycode;
    @JsonProperty("codigoPais")
    private String contrycode;



}
