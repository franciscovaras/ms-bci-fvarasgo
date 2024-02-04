package com.fvarasgo.bci.demo.msbcifvarasgo.service.impl;

import com.fvarasgo.bci.demo.msbcifvarasgo.dao.UserDao;
import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserRequest;
import com.fvarasgo.bci.demo.msbcifvarasgo.dto.UserResponse;
import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;
import com.fvarasgo.bci.demo.msbcifvarasgo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    public static final String FMT_FECHA_YYYY_MM_DD_GUION = "yyyy-MM-dd";

    @Autowired
    UserDao userDao;
    @Override
    public UserResponse userRegister(UserRequest userRequest) throws Exception {
        String email = userRequest.getEmail();
        checkEmail(email);
        UserData user = UserData.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .created(obtenerDiaActual())
                .modified(obtenerDiaActual())
                .lastLogin(obtenerDiaActual())
                .token("")
                .isaActive(true)
                .build();
        findByMail(user.getEmail());
        save(user);

        return UserResponse.builder()
                .id(user.getId())
                .created(obtenerDiaActual())
                .modified(obtenerDiaActual())
                .lastLogin(obtenerDiaActual())
                .token("")
                .isaActive(true)
                .build();
    }
    @Override
    @Transactional
    public void save(UserData user) {
        userDao.save(user);
    }

    public UserData findByMail(String email){
        return userDao.findByMail(email);
    }

    private Date obtenerDiaActual() {
        SimpleDateFormat formatter = new SimpleDateFormat(FMT_FECHA_YYYY_MM_DD_GUION);
        return formatter.get2DigitYearStart();
    }


    private void checkEmail(String email) throws Exception {
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        boolean response = mather.find();
        if (response) {
            System.out.println("El email ingresado es válido.");
        } else {
             throw new Exception("El email ingresado es inválido.");
        }

    }
}
