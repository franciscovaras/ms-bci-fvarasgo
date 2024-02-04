package com.fvarasgo.bci.demo.msbcifvarasgo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="usuario")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Temporal(TemporalType.DATE)
    private Date created;
    @Temporal(TemporalType.DATE)
    private Date modified;
    @Column(name = "last_login")
    @Temporal(TemporalType.DATE)
    private Date lastLogin;
    private String token;
    @Column(name = "isa_active")
    private boolean isaActive;
}
