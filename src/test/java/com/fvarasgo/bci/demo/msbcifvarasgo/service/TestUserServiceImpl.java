package com.fvarasgo.bci.demo.msbcifvarasgo.service;

import com.fvarasgo.bci.demo.msbcifvarasgo.dao.UserDao;
import com.fvarasgo.bci.demo.msbcifvarasgo.exception.UsuarioException;
import com.fvarasgo.bci.demo.msbcifvarasgo.exception.UsuarioNoEncontradoException;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.common.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.common.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.impl.UserServiceImpl;
import com.fvarasgo.bci.demo.msbcifvarasgo.utils.TestUtils;
import com.fvarasgo.bci.demo.msbcifvarasgo.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestUserServiceImpl {

    @Mock
    UserDao userDao;

    @InjectMocks
    UserServiceImpl userService;

    private UserRequest userRequest;
    private UserData userData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRequest = new UserRequest();
        userRequest.setNombre("John");
        userRequest.setCorreo("john.doe@email.com");
        userRequest.setPassword("1234");

        userData = UserData.builder()
                .id(UUID.randomUUID())
                .name("John")
                .email("john.doe@email.com")
                .password("1234")
                .created(Utils.obtenerDiaActual())
                .modified(Utils.obtenerDiaActual())
                .lastLogin(Utils.obtenerDiaActual())
                .token("token")
                .isActive(true)
                .build();
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

    @Test
    void userRegister_Success() {
        when(userDao.findByMail(userRequest.getCorreo())).thenReturn(null);
        when(userDao.save(any(UserData.class))).thenReturn(userData);

        UserResponse response = userService.userRegister(userRequest);

        assertNotNull(response);
        assertEquals("John", userRequest.getNombre());
        verify(userDao).save(any(UserData.class));
    }

    @Test
    void userRegister_EmailAlreadyExists_ThrowsException() {
        when(userDao.findByMail(userRequest.getCorreo())).thenReturn(userData);

        UsuarioNoEncontradoException ex = assertThrows(UsuarioNoEncontradoException.class,
                () -> userService.userRegister(userRequest));
        assertEquals("El mail ya se encuentra registrado.", ex.getMessage());
    }

    @Test
    void getUserById_Success() {
        UUID id = UUID.randomUUID();
        when(userDao.findById(id)).thenReturn(Optional.of(userData));

        UserResponse response = userService.getUserById(id);

        assertNotNull(response);

    }

    @Test
    void getUserById_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(userDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(UsuarioException.ErrorSolicitud.class, () -> userService.getUserById(id));
    }

    @Test
    void updateUser_Success() {
        UUID id = UUID.randomUUID();
        when(userDao.findById(id)).thenReturn(Optional.of(userData));
        when(userDao.save(any(UserData.class))).thenReturn(userData);

        UserResponse response = userService.updateUser(id, userRequest);

        assertNotNull(response);
        verify(userDao).save(any(UserData.class));
    }

    @Test
    void patchUser_Success() {
        UUID id = UUID.randomUUID();
        Map<String, Object> updates = Map.of("nombre", "Jane", "correo", "jane@example.com");
        when(userDao.findById(id)).thenReturn(Optional.of(userData));
        when(userDao.save(any(UserData.class))).thenReturn(userData);

        UserResponse response = userService.patchUser(id, updates);

        assertNotNull(response);
        verify(userDao).save(any(UserData.class));
    }

    @Test
    void deleteUser_Success() {
        UUID id = UUID.randomUUID();
        when(userDao.existsById(id)).thenReturn(true);
        doNothing().when(userDao).deleteById(id);

        userService.deleteUser(id);

        verify(userDao).deleteById(id);
    }

    @Test
    void deleteUser_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(userDao.existsById(id)).thenReturn(false);

        assertThrows(UsuarioException.ErrorSolicitud.class, () -> userService.deleteUser(id));
    }

    @Test
    void getAllUsers_ReturnsList() {
        when(userDao.findAll()).thenReturn(List.of(userData));

        List<UserResponse> responses = userService.getAllUsers();

        assertEquals(1, responses.size());

    }

}
