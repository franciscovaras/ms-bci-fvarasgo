package com.fvarasgo.bci.demo.msbcifvarasgo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private String token;
    private boolean isaActive;


}
