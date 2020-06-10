package com.view;

import lombok.Data;

@Data
public class LoginUserDto  {
    private String token;
    private String firstName;
    private String lastName;
    private String fullName;
    private String realUserName;
    private Long personnelCode;
    private String userRoleDesc;
    private Long id;
}
