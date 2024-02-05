package com.fvarasgo.bci.demo.msbcifvarasgo.service;

import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;

public interface UserService {
    public UserResponse userRegister(UserRequest userRequest) throws Exception;
    public void save(UserData user);
    public UserData findByMail(String email);
}
