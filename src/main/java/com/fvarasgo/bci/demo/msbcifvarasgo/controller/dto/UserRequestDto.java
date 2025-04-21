package com.fvarasgo.bci.demo.msbcifvarasgo.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.domain.Phone;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.domain.UserRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Request para crear un usuario")
public class UserRequestDto {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,50}$", message = "El nombre debe contener solo letras y espacios (2-50 caracteres)")
    private String nombre;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @JsonProperty("contraseña")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&\\-_])[A-Za-z\\d@$!%*?&\\-_]{8,20}$",
            message = "La contraseña debe tener entre 8 y 20 caracteres, incluyendo mayúsculas, minúsculas, un número y un carácter especial"
    )
    private String password;

    @JsonProperty("telefonos")
    private List<PhoneDto> phones;

    public UserRequest toDomain() {
        return UserRequest.builder()
                .nombre(this.nombre)
                .correo(this.correo)
                .password(this.password)
                .phones(this.phones.stream()
                        .map(phoneDto -> Phone.builder()
                                .number(phoneDto.getNumber())
                                .citycode(phoneDto.getCitycode())
                                .contrycode(phoneDto.getContrycode())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}

