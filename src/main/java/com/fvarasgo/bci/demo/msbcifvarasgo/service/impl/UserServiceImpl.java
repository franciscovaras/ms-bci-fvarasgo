package com.fvarasgo.bci.demo.msbcifvarasgo.service.impl;

import com.fvarasgo.bci.demo.msbcifvarasgo.controller.dto.UserMapper;
import com.fvarasgo.bci.demo.msbcifvarasgo.dao.UserDao;
import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;
import com.fvarasgo.bci.demo.msbcifvarasgo.exception.UsuarioException;
import com.fvarasgo.bci.demo.msbcifvarasgo.exception.UsuarioNoEncontradoException;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.UserService;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.domain.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.domain.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.utils.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementación del servicio de usuario que maneja las operaciones de registro, actualización, eliminación,
 * búsqueda y autenticación de usuarios.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param userRequest Información del usuario a registrar.
     * @return Respuesta con la información del usuario registrado.
     * @throws UsuarioNoEncontradoException Si el correo electrónico ya está registrado.
     */
    @Override
    public UserResponse userRegister(UserRequest userRequest) {
        String email = userRequest.getCorreo();
        checkEmail(email); //verificar formato de mail sea correcto
        String token = getJWTToken(userRequest.getNombre()); //obtener token

        UserData user = UserData.builder()
                .name(userRequest.getNombre())
                .email(userRequest.getCorreo())
                .password(userRequest.getPassword())
                .created(Utils.obtenerDiaActual())
                .modified(Utils.obtenerDiaActual())
                .lastLogin(Utils.obtenerDiaActual())
                .token(token)
                .isActive(true)
                .build();

        //validar si se encuentra registrado el mail consultado en BD
        UserData resultado = findByMail(user.getEmail());
        if (resultado != null) {
            throw new UsuarioNoEncontradoException("El mail ya se encuentra registrado.");
        }

        //Guardamos la data en bd h2
        save(user);

        //Retornamos el objeto response
        return UserResponse.builder()
                .id(user.getId())
                .created(Utils.obtenerDiaActual())
                .modified(Utils.obtenerDiaActual())
                .lastLogin(Utils.obtenerDiaActual())
                .token(token)
                .isActive(true)
                .build();
    }

    /**
     * Guarda un usuario en la base de datos.
     *
     * @param user El usuario a guardar.
     */
    @Override
    @Transactional
    public void save(UserData user) {
        userDao.save(user);
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email El correo electrónico del usuario a buscar.
     * @return El usuario encontrado, o {@code null} si no existe.
     */
    @Override
    public UserData findByMail(String email) {
        return userDao.findByMail(email);
    }

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     *
     * @return Lista de respuestas con la información de todos los usuarios.
     */
    @Override
    public List<UserResponse> getAllUsers() {
        return StreamSupport.stream(userDao.findAll().spliterator(), false)
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id El ID del usuario a buscar.
     * @return Respuesta con la información del usuario encontrado.
     * @throws UsuarioException.ErrorSolicitud Si el usuario no es encontrado.
     */
    @Override
    public UserResponse getUserById(UUID id) {
        UserData user = userDao.findById(id)
                .orElseThrow(() -> new UsuarioException.ErrorSolicitud("mensaje","Usuario no encontrado"));
        return UserMapper.toResponse(user);
    }

    /**
     * Actualiza los datos de un usuario.
     *
     * @param id      El ID del usuario a actualizar.
     * @param request Los nuevos datos del usuario.
     * @return Respuesta con la información del usuario actualizado.
     * @throws UsuarioException.ErrorSolicitud Si el usuario no es encontrado.
     */
    @Override
    public UserResponse updateUser(UUID id, UserRequest request) {
        UserData user = userDao.findById(id)
                .orElseThrow(() -> new UsuarioException.ErrorSolicitud("mensaje","Usuario no encontrado"));
        user.setName(request.getNombre());
        user.setEmail(request.getCorreo());
        user.setPassword(request.getPassword());
        user.setModified(Utils.toDate(LocalDateTime.now()));
        return UserMapper.toResponse(userDao.save(user));
    }

    /**
     * Realiza una actualización parcial de los datos de un usuario.
     *
     * @param id      El ID del usuario a actualizar.
     * @param updates El mapa de campos a actualizar.
     * @return Respuesta con la información del usuario actualizado.
     * @throws UsuarioException.ErrorSolicitud Si el usuario no es encontrado.
     */
    @Override
    public UserResponse patchUser(UUID id, Map<String, Object> updates) {
        UserData user = userDao.findById(id)
                .orElseThrow(() -> new UsuarioException.ErrorSolicitud("mensaje","Usuario no encontrado"));
        updates.forEach((key, value) -> {
            switch (key) {
                case "nombre":
                    user.setName((String) value);
                    break;
                case "correo":
                    user.setEmail((String) value);
                    break;
            }
        });
        user.setModified(Utils.toDate(LocalDateTime.now()));
        return UserMapper.toResponse(userDao.save(user));
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id El ID del usuario a eliminar.
     * @throws UsuarioException.ErrorSolicitud Si el usuario no es encontrado.
     */
    @Override
    public void deleteUser(UUID id) {
        if (!userDao.existsById(id)) {
            throw new UsuarioException.ErrorSolicitud("mensaje","Usuario no encontrado");
        }
        userDao.deleteById(id);
    }

    //métodos privados

    /**
     * Valida el formato del correo electrónico proporcionado.
     *
     * @param email El correo electrónico a validar.
     * @throws UsuarioNoEncontradoException Si el formato del correo no es válido.
     */
    private void checkEmail(String email) {
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        boolean response = mather.find();
        if (response) {
            System.out.println("El formato de email ingresado es válido.");
        } else {

            throw new UsuarioNoEncontradoException("El formato de email ingresado es válido.");
        }

    }

    /**
     * Genera un token JWT para el usuario.
     *
     * @param username El nombre del usuario para el que se generará el token.
     * @return El token JWT generado.
     */
    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
