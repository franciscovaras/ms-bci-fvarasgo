package com.fvarasgo.bci.demo.msbcifvarasgo.controller.dto;

import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.common.Phone;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.common.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.common.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserRequest toDomain(UserRequestDto dto) {
        if (dto == null) return null;

        return UserRequest.builder()
                .nombre(dto.getNombre())
                .correo(dto.getCorreo())
                .password(dto.getPassword())
                .phones(toDomainPhones(dto.getPhones()))
                .build();
    }

    public static UserRequestDto toDto(UserRequest domain) {
        if (domain == null) return null;

        return UserRequestDto.builder()
                .nombre(domain.getNombre())
                .correo(domain.getCorreo())
                .password(domain.getPassword())
                .phones(toDtoPhones(domain.getPhones()))
                .build();
    }

    private static List<Phone> toDomainPhones(List<PhoneDto> dtos) {
        if (dtos == null) return null;
        return dtos.stream()
                .map(p -> Phone.builder()
                        .number(p.getNumber())
                        .citycode(p.getCitycode())
                        .contrycode(p.getContrycode())
                        .build())
                .collect(Collectors.toList());
    }

    private static List<PhoneDto> toDtoPhones(List<Phone> phones) {
        if (phones == null) return null;
        return phones.stream()
                .map(p -> PhoneDto.builder()
                        .number(p.getNumber())
                        .citycode(p.getCitycode())
                        .contrycode(p.getContrycode())
                        .build())
                .collect(Collectors.toList());
    }

    public static UserResponse toResponse(UserData entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .created(entity.getCreated())
                .modified(entity.getModified())
                .lastLogin(entity.getLastLogin())
                .isActive(entity.isActive())
                .token(entity.getToken())
                .build();
    }

    public static List<Phone> toPhoneEntities(List<PhoneDto> phoneDtos) {
        return phoneDtos.stream()
                .map(dto -> Phone.builder()
                        .number(dto.getNumber())
                        .citycode(dto.getCitycode())
                        .contrycode(dto.getContrycode())
                        .build())
                .collect(Collectors.toList());
    }

}
