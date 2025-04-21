package com.fvarasgo.bci.demo.msbcifvarasgo.controller;

import com.fvarasgo.bci.demo.msbcifvarasgo.controller.dto.UserRequestDto;
import com.fvarasgo.bci.demo.msbcifvarasgo.controller.dto.UserMapper;
import com.fvarasgo.bci.demo.msbcifvarasgo.exception.UsuarioException;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.UserService;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.common.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TestUserController {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerUsuarioTest() throws Exception {
        // Arrange: Preparar los datos y las expectativas
        UserRequestDto userRequestDto = TestUtils.getUserRequestDto(); // DTO con datos de prueba
        UserResponse userResponse = TestUtils.getUserResponse(); // Respuesta del servicio esperada


        when(userService.userRegister(UserMapper.toDomain(userRequestDto))).thenReturn(userResponse);
        ResponseEntity<Object> response = userController.registerUsuario(userRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
    }

    @Test
    void registerUsuario_ShouldReturnUserResponse_WhenRequestIsValid() throws Exception {
        // Arrange
        UserRequestDto userRequestDto = TestUtils.getUserRequestDto();
        UserResponse userResponse = TestUtils.getUserResponse();

        when(userService.userRegister(userRequestDto.toDomain())).thenReturn(userResponse);

        // Act
        ResponseEntity<Object> response = userController.registerUsuario(userRequestDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getAllUsuarios_ShouldReturnListOfUsers() {
        // Arrange
        when(userService.getAllUsers()).thenReturn(List.of(TestUtils.getUserResponse()));

        // Act
        ResponseEntity<Object> response = userController.getAllUsuarios();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getUsuarioById_ShouldReturnUserResponse_WhenIdExists() {
        // Arrange
        UUID userId = UUID.randomUUID();
        when(userService.getUserById(userId)).thenReturn(TestUtils.getUserResponse());

        // Act
        ResponseEntity<Object> response = userController.getUsuarioById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void deleteUsuario_ShouldReturnNoContent_WhenUserIsDeleted() {
        // Arrange
        UUID userId = UUID.randomUUID();

        // Act
        ResponseEntity<Object> response = userController.deleteUsuario(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
