package com.fvarasgo.bci.demo.msbcifvarasgo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Phone {

    private Long id;
    private String number;
    private String citycode;
    private String contrycode;



}
