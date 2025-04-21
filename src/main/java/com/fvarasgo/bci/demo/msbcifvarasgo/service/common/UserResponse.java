package com.fvarasgo.bci.demo.msbcifvarasgo.service.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Respuesta del registro de usuario")
public class UserResponse {

    private UUID id;
    @JsonProperty("creado")
    private Date created;
    @JsonProperty("modificado")
    private Date modified;
    @JsonProperty("ultimoLogin")
    private Date lastLogin;
    private String token;
    @JsonProperty("activo")
    private boolean isActive;


}
