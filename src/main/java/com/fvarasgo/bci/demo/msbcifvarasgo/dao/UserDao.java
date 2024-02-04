package com.fvarasgo.bci.demo.msbcifvarasgo.dao;

import com.fvarasgo.bci.demo.msbcifvarasgo.entity.UserData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserData, Long> {
    @Query("select p from UserData p where p.email = ?1")
    public UserData findByMail(String email);
}
