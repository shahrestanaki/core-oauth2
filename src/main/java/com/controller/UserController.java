package com.controller;


import com.service.UserInfoService;
import com.view.ChangePasswordDto;
import com.view.SingUpDto;
import com.view.UserGeneralResponse;
import com.view.UserSignUpResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@Api(value = "login")
@Validated
public class UserController {

    @Autowired
    private UserInfoService userInfoSrv;

    @PostMapping("/sign-up")
    public UserSignUpResponse singup(@Valid @RequestBody SingUpDto singUp) {
        return userInfoSrv.singup(singUp);
    }

    @PostMapping("/change-Password")
    public UserGeneralResponse changePassword(@Valid @RequestBody ChangePasswordDto changePassword) {
        return userInfoSrv.changePassword(changePassword);
    }
}
