package com.fvarasgo.bci.demo.msbcifvarasgo.service.impl;

import com.fvarasgo.bci.demo.msbcifvarasgo.dao.UserDao;
import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;
import com.fvarasgo.bci.demo.msbcifvarasgo.exception.UsuarioNoEncontradoException;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.UserService;
import com.fvarasgo.bci.demo.msbcifvarasgo.utils.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

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
                .isaActive(true)
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
                .isaActive(true)
                .build();
    }

    @Override
    @Transactional
    public void save(UserData user) {
        userDao.save(user);
    }

    @Override
    public UserData findByMail(String email) {
        return userDao.findByMail(email);
    }

    //métodos privados

    //validar mail
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

    //obtener token
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
