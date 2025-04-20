package com.fvarasgo.bci.demo.msbcifvarasgo.utils;

import com.fvarasgo.bci.demo.msbcifvarasgo.dto.Phone;
import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;

import java.util.Arrays;

public class TestUtils {

    public static final String TOKEN = "\"kiOiJzb2Z0dGVrSldUIiwic3ViIjoiSnVhbiBSb\" +\n" +
            "                        \"2RyaWd1ZXoiLCJhdXRob3JpdGllcyI6WyJST0xFX1\" +\n" +
            "                        \"VTRVIiXSwiaWF0IjoxNzA3MDk1OTA1LCJleHAiOjE3MDc\" +\n" +
            "                        \"wOTY1MDV9.w5vbIz9Ljfvs0WuuYnZ0hNcmHWdHLgWoIkkv5k\" +\n" +
            "                        \"WeYynTn70_sghBuJTiafoexCD7T8EdSsJpWlxqcHsOvxmu7A\"";

    public static UserRequest getUserRequest(){
        return UserRequest.builder()
                .nombre("Juan Rodriguez")
                .correo("juan111@rodriguez.org")
                .password("hunter2")
                .phones(Arrays.asList(getPhone()))
                .build();
    }

    public static Phone getPhone(){
        return  Phone.builder()
                .number("1234567")
                .citycode("1")
                .contrycode("57")
                .build();
    }

    public static UserResponse getUserResponse() {
        return UserResponse.builder()
                .id(5L)
                .created(Utils.obtenerDiaActual())
                .modified(Utils.obtenerDiaActual())
                .lastLogin(Utils.obtenerDiaActual())
                .token(TOKEN)
                .isaActive(true)
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
                .isaActive(true)
                .build();
    }

}
