package com.view;

import com.tools.UserName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ForgetPasswordDto {
    @UserName
    @Length(min = 5, max = 50)
    private String userName;
    private String privateKey;
    private String ownerKey;
}
