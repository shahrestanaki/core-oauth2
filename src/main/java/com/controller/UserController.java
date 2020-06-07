package com.controller;


import com.service.UserInfoService;
import com.view.SingUpDto;
import com.view.UserSignUpResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(value = "user")
public class UserController {

    @Autowired
    private UserInfoService userInfoSrv;

    @PostMapping("/singup")
    public UserSignUpResponse singup(@RequestBody SingUpDto singUp) {
        return userInfoSrv.singup(singUp);
    }
}
