package com.fvarasgo.bci.demo.msbcifvarasgo.controller;

import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.exception.UsuarioException;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.UserService;
import com.fvarasgo.bci.demo.msbcifvarasgo.utils.Utils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Registrar un nuevo usuario", notes = "Proporciona los datos del usuario para registrarlo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario registrado exitosamente"),
            @ApiResponse(code = 400, message = "Solicitud incorrecta"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    @PostMapping("/usuario")
    public ResponseEntity<Object> registerUsuario(@RequestBody UserRequest userRequest) {

        UserResponse response = null;
        try {
            response = userService.userRegister(userRequest);
            return ResponseEntity.ok().body(response);

        } catch (UsuarioException.ErrorSolicitud e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Utils.messageJson("Error en la solicitud: " + e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Utils.messageJson("Error interno del servidor: " + e.getMessage()));
        }
    }
}
