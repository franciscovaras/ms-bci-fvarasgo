package com.fvarasgo.bci.demo.msbcifvarasgo.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Respuesta del registro de usuario")
public class UserResponse {

    private Long id;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private String token;
    private boolean isaActive;


}
