package com.fvarasgo.bci.demo.msbcifvarasgo.dao;

import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserDao extends CrudRepository<UserData, UUID> {
    @Query("select p from UserData p where p.email = ?1")
    UserData findByMail(String email);

}
