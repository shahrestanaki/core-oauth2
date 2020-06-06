package com.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
/*

123 :    $2a$07$8jJv74FqLDbA2OTKXZpSH.xE2lLrFl0zjXSVm6VW6r7qxYUWzq4x6

 */

@Entity
@Table(name = "users")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 25)
    private Integer id;

    @Column(name = "username", length = 50)
    private String userName;

    @NotEmpty(message = "{user.password.null}")
    @Size(min = 2, max = 800, message = "{user.password.size}")
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "role", length = 50)
    private String role;

    @Column(name = "enabled")
    private short enabled;
    @NotEmpty(message = "{user.name.null}")
    @Size(min = 2, max = 50, message = "{user.name.size}")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotEmpty(message = "{user.famili.null}")
    @Size(min = 2, max = 50, message = "{user.famili.size}")
    @Column(name = "famili", length = 50, nullable = false)
    private String famili;

    @NotEmpty(message = "{user.username.null}")
    @Size(min = 2, max = 50, message = "{user.username.size}")
    @Column(name = "username", length = 50, nullable = false)
    private String username;


    @NotNull(message = "{user.status.null}")
    @Max(value = 4, message = "{user.status.Maxsize}")
    @Min(value = 1, message = "{user.status.Minsize}")
    @Column(name = "status", precision = 1, nullable = false)
    private int status;

    @NotNull(message = "{user.rules.null}")
    @Max(value = 4, message = "{user.rules.Maxsize}")
    @Min(value = 1, message = "{user.rules.Minsize}")
    @Column(name = "rules", precision = 1, nullable = false)
    private int rules;

    //@NotNull(message = "{user.createdate.null}")
    //@ValidDate
    @Column(name = "createdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdate;

    @Column(name = "changedate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changedate;

    @Column(name = "edituser")
    private Long edituser;

    @Column(name = "lockdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockdate;

    @Column(name = "avatar", length = 255)
    private String avatar;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public short getEnabled() {
        return enabled;
    }

    public void setEnabled(short enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return String.format("UserInfo [id=%s, userName=%s, password=%s, role=%s, enabled=%s]", id, userName, password,
                role, enabled);
    }

}
