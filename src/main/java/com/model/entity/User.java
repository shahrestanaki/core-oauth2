package com.model.entity;


import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "User")
@Table(name = "users")
@DynamicUpdate
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @NotEmpty(message = "{user.password.null}")
    @Size(min = 2, max = 255, message = "{user.password.size}")
    @Column(name = "password", length = 255, nullable = false)
    private String password;


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

    public User() {
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "role")
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamili() {
        return famili;
    }

    public void setFamili(String famili) {
        this.famili = famili;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRules() {
        return rules;
    }

    public void setRules(int rules) {
        this.rules = rules;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getChangedate() {
        return changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

    public Long getEdituser() {
        return edituser;
    }

    public void setEdituser(Long edituser) {
        this.edituser = edituser;
    }

    public Date getLockdate() {
        return lockdate;
    }

    public void setLockdate(Date lockdate) {
        this.lockdate = lockdate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
