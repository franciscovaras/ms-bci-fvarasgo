package com.fvarasgo.bci.demo.msbcifvarasgo.service;

import com.fvarasgo.bci.demo.msbcifvarasgo.dao.UserDao;
import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;
import com.fvarasgo.bci.demo.msbcifvarasgo.exception.UsuarioNoEncontradoException;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.impl.UserServiceImpl;
import com.fvarasgo.bci.demo.msbcifvarasgo.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestUserServiceImpl {

    @Mock
    UserDao userDao;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void userRegisterTest() {
        when(userDao.findByMail(TestUtils.getUserData().getEmail())).thenReturn(null);
        UserResponse response = userService.userRegister(TestUtils.getUserRequest());
        assertNotNull(response);
    }

    @Test
    void saveTest(){
        when(userDao.save(any())).thenReturn(TestUtils.getUserData());
        userService.save(TestUtils.getUserData());
    }

    @Test
    void findByMailTest(){
        Mockito.when(userDao.findByMail(any())).thenReturn(TestUtils.getUserData());
        UserData response = userService.findByMail(TestUtils.getUserData().getEmail());
        assertNotNull(response);
    }

}
