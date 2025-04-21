package com.fvarasgo.bci.demo.msbcifvarasgo.controller;

import com.fvarasgo.bci.demo.msbcifvarasgo.controller.dto.UserMapper;
import com.fvarasgo.bci.demo.msbcifvarasgo.controller.dto.UserRequestDto;
import com.fvarasgo.bci.demo.msbcifvarasgo.exception.UsuarioException;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.UserService;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.domain.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.domain.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.utils.Utils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * Controlador que maneja las operaciones relacionadas con los usuarios.
 * Proporciona métodos para registrar, obtener, actualizar, eliminar y listar usuarios.
 */
@RestController
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param userDto El DTO que contiene la información del usuario a registrar.
     * @return ResponseEntity con el código HTTP y los datos del usuario registrado.
     */
    @ApiOperation(value = "Registrar un nuevo usuario", notes = "Proporciona los datos del usuario para registrarlo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario registrado exitosamente"),
            @ApiResponse(code = 400, message = "Solicitud incorrecta"),
            @ApiResponse(code = 500, message = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Object> registerUsuario(@RequestBody UserRequestDto userDto) {
        try {

            UserRequest domainRequest = UserMapper.toDomain(userDto);
            UserResponse domainResponse = userService.userRegister(domainRequest);

            return ResponseEntity.ok(domainResponse);

        } catch (UsuarioException.ErrorSolicitud e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Utils.messageJson("Error en la solicitud: " + e.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Utils.messageJson("Error interno del servidor: " + e.getMessage()));
        }
    }

    /**
     * Obtiene todos los usuarios del sistema.
     *
     * @return ResponseEntity con la lista de usuarios.
     */
    @Operation(summary = "Obtener usuario por ID", description = "Retorna la información de un usuario usando su UUID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario encontrado"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @GetMapping
    public ResponseEntity<Object> getAllUsuarios() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id El UUID del usuario que se desea consultar.
     * @return ResponseEntity con los datos del usuario si es encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuarioById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Actualiza los datos de un usuario.
     *
     * @param id El UUID del usuario que se desea actualizar.
     * @param userDto El DTO que contiene los nuevos datos del usuario.
     * @return ResponseEntity con la información del usuario actualizado.
     */
    @Operation(summary = "Actualizar usuario", description = "Actualiza todos los datos del usuario con el UUID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario actualizado"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })


    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUsuario(@PathVariable UUID id, @RequestBody UserRequestDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, UserMapper.toDomain(userDto)));
    }


    /**
     * Realiza una actualización parcial de un usuario.
     *
     * @param id El UUID del usuario que se desea actualizar.
     * @param updates Un mapa con los campos a actualizar y sus nuevos valores.
     * @return ResponseEntity con la información del usuario parcialmente actualizado.
     */
    @Operation(summary = "Actualizar parcialmente usuario", description = "Realiza una actualización parcial de un usuario usando su UUID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuario actualizado parcialmente"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchUsuario(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(userService.patchUser(id, updates));
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El UUID del usuario que se desea eliminar.
     * @return ResponseEntity sin contenido si la eliminación fue exitosa.
     */
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario con el UUID proporcionado.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Usuario eliminado"),
            @ApiResponse(code = 404, message = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
