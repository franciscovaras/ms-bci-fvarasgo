package com.fvarasgo.bci.demo.msbcifvarasgo.utils;

import com.fvarasgo.bci.demo.msbcifvarasgo.controller.dto.PhoneDto;
import com.fvarasgo.bci.demo.msbcifvarasgo.controller.dto.UserRequestDto;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.domain.Phone;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.domain.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.domain.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static final String TOKEN = "\"kiOiJzb2Z0dGVrSldUIiwic3ViIjoiSnVhbiBSb\" +\n" +
            "                        \"2RyaWd1ZXoiLCJhdXRob3JpdGllcyI6WyJST0xFX1\" +\n" +
            "                        \"VTRVIiXSwiaWF0IjoxNzA3MDk1OTA1LCJleHAiOjE3MDc\" +\n" +
            "                        \"wOTY1MDV9.w5vbIz9Ljfvs0WuuYnZ0hNcmHWdHLgWoIkkv5k\" +\n" +
            "                        \"WeYynTn70_sghBuJTiafoexCD7T8EdSsJpWlxqcHsOvxmu7A\"";

    // Método para crear un UserRequestDto (lo que se recibe en el controlador)
    public static UserRequestDto getUserRequestDto(){
        return UserRequestDto.builder()
                .nombre("Juan Rodriguez")
                .correo("juan111@rodriguez.org")
                .password("hunter2")
                .phones(List.of(getPhoneDto()))
                .build();
    }

    // Método para crear un UserRequest (lo que se usa en el servicio)
    public static UserRequest getUserRequest(){
        return UserRequest.builder()
                .nombre("Juan Rodriguez")
                .correo("juan111@rodriguez.org")
                .password("hunter2")
                .phones(Arrays.asList(getPhone()))
                .build();
    }

    public static Phone getPhone(){
        return Phone.builder()
                .number("1234567")
                .citycode("1")
                .contrycode("57")
                .build();
    }

    public static PhoneDto getPhoneDto(){
        return PhoneDto.builder()
                .number("1234567")
                .citycode("1")
                .contrycode("57")
                .build();
    }

    public static UserResponse getUserResponse() {
        return UserResponse.builder()
                .id(UUID.randomUUID())
                .created(Utils.obtenerDiaActual())
                .modified(Utils.obtenerDiaActual())
                .lastLogin(Utils.obtenerDiaActual())
                .token(TOKEN)
                .isActive(true)
                .build();
    }

    public static UserData getUserData() {
        return UserData.builder()
                .name(getUserRequest().getNombre())
                .email(getUserRequest().getCorreo())
                .password(getUserRequest().getPassword())
                .created(Utils.obtenerDiaActual())
                .modified(Utils.obtenerDiaActual())
                .lastLogin(Utils.obtenerDiaActual())
                .token(TOKEN)
                .isActive(true)
                .build();
    }
}
