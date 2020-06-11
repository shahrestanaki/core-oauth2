package com.web;

import com.service.UserInfoService;
import com.view.ChangePasswordDto;
import com.view.SingUpDto;
import com.view.UserGeneralResponse;
import com.view.UserSignUpResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/users")
@Api(value = "users")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<Principal> get(final Principal principal) {
        return ResponseEntity.ok(principal);
    }

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
