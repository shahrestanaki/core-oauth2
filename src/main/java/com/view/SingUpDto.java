package com.view;

import com.enump.RoleEnum;
import com.tools.UserName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SingUpDto {
    @UserName
    @Length(min = 5, max = 50)
    private String userName;

    @Length(min = 5, max = 250)
    private String password;

    private RoleEnum role;
}
