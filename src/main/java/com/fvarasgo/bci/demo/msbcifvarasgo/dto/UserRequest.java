package com.fvarasgo.bci.demo.msbcifvarasgo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Request para crear un usuario")
public class UserRequest {

    private String nombre;
    private String correo;
    @JsonProperty("contraseña")
    private String password;
    @JsonProperty("telefonos")
    private List<Phone> phones;

}
