package com.view;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangePasswordDto {

    @Length(min = 5, max = 250)
    private String oldPassword;
    @Length(min = 5, max = 250)
    private String newPassword;
    @Length(min = 5, max = 250)
    private String repeatPassword;

}
