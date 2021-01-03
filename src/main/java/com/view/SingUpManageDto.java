package com.view;

import com.enump.RoleEnum;
import com.tools.UserName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class SingUpManageDto {
    @UserName
    @Length(min = 5, max = 50)
    private String userName;

    @Length(min = 5, max = 250)
    private String password;

    @NotNull
    private RoleEnum role;

}
