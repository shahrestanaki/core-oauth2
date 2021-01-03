package com.view;

import com.enump.RoleSingUpEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tools.UserName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class SingUpDto {
    @UserName
    @Length(min = 5, max = 50)
    private String userName;

    @Length(min = 5, max = 250)
    private String password;

    @NotNull
    private RoleSingUpEnum[] roles;

    @JsonIgnore
    private String roleStr = "";

    public void setRole(RoleSingUpEnum[] roles) {
        this.roles = roles;
        for (RoleSingUpEnum item : roles) {
            roleStr = roleStr + "," + item.name();
        }
        roleStr = roleStr != null ? roleStr.substring(1) : "";
    }
}
