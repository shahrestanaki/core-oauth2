package com.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/*

123 :    $2a$07$8jJv74FqLDbA2OTKXZpSH.xE2lLrFl0zjXSVm6VW6r7qxYUWzq4x6
https://github.com/MShahapure/Spring-Security-OAuth2-MySQL/
 */

@Entity
@Table(name = "users")
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "users_sequence", strategy = "sequence", parameters = {
            @org.hibernate.annotations.Parameter(name = "sequenceName", value = "users_sequence"),
            @org.hibernate.annotations.Parameter(name = "initial_value", value = "20"),
            @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1"),
    })
    @GeneratedValue(generator = "users_sequence", strategy=GenerationType.SEQUENCE)
    @Column(name = "id", length = 25, nullable = false)
    private Long id;

    @Column(name = "username", length = 50, nullable = false)
    private String userName;

    @Column(name = "password",length = 255, nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean active;

    @Column(name = "wrong_Pass")
    private Integer wrongPass;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lock_Date")
    private Date lockDate;

    @Column(name = "lock_Status")
    private Boolean lockStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_Date", nullable = false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "change_Date")
    private Date changeDate;

    @Column(name = "role", length = 50,nullable = false)
    private String role;

    @Column(name = "manager", length = 50,nullable = false)
    private String manager;

    public UserInfo(){

    }
    public UserInfo(String userName,String password,String role,String manager){
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.active = true;
        this.lockStatus = false;
        this.createDate = new Date();
        this.manager = manager;
    }

}
