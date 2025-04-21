package com.fvarasgo.bci.demo.msbcifvarasgo.service;

import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.common.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.common.UserResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface UserService {
    UserResponse userRegister(UserRequest userRequest) throws Exception;
    void save(UserData user);
    UserData findByMail(String email);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(UUID id);
    UserResponse updateUser(UUID id, UserRequest request);
    UserResponse patchUser(UUID id, Map<String, Object> updates);
    void deleteUser(UUID id);
}
