package com.fvarasgo.bci.demo.msbcifvarasgo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDto {

    @JsonProperty("numero")
    @NotBlank(message = "El número no puede estar vacío")
    @Pattern(
            regexp = "^[0-9]{7,15}$",
            message = "El número debe contener entre 7 y 15 dígitos"
    )
    private String number;

    @JsonProperty("codigoCiudad")
    @NotBlank(message = "El código de ciudad no puede estar vacío")
    @Pattern(
            regexp = "^[0-9]{1,5}$",
            message = "El código de ciudad debe contener entre 1 y 5 dígitos"
    )
    private String citycode;

    @JsonProperty("codigoPais")
    @NotBlank(message = "El código de país no puede estar vacío")
    @Pattern(
            regexp = "^[0-9]{1,5}$",
            message = "El código de país debe contener entre 1 y 5 dígitos"
    )
    private String contrycode;

}
