package com.view;

import com.tools.UserName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ChangeStatusUserDto {
    @UserName
    @Length(min = 5, max = 50)
    private String userName;
    @Length(min = 5, max = 50)
    private String ownerKey;

    private boolean active;
    private boolean lock;
}
