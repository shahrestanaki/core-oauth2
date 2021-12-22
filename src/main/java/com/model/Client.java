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

    @Column(name = "manager_Name", nullable = false)
    private String managerName;

    @Column(name = "client_Id", nullable = false)
    private String clientId;

}
