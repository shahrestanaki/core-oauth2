package com.model;

import lombok.Data;

import javax.persistence.*;


@Entity
@Table(name = "Client")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

}
