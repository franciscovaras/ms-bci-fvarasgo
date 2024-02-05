package com.fvarasgo.bci.demo.msbcifvarasgo.controller;

import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.exception.UsuarioException;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.UserService;
import com.fvarasgo.bci.demo.msbcifvarasgo.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/usuario")
    public ResponseEntity<Object> registerUsuario(@RequestBody UserRequest userRequest) {

        UserResponse response = null;
        try {
            response = userService.userRegister(userRequest);

        } catch (UsuarioException.ErrorSolicitud e) {
            ResponseEntity.badRequest().body(Utils.messageJson("error en la insersión"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(response);
    }

}


