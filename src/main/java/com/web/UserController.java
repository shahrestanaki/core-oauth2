package com.web;

import com.service.UserInfoService;
import com.view.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Api(value = "users")
public class UserController {

    @GetMapping("/info")
    public String information() {
        return " this information system";
    }

/*    @GetMapping("/me")
    public ResponseEntity<Principal> get(final Principal principal) {
        return ResponseEntity.ok(principal);
    }*/

    @Autowired
    private UserInfoService userInfoSrv;

    @PostMapping("/sign-up")
    public UserSingupView singup(@Valid @RequestBody SingUpDto singUp) {
        return userInfoSrv.singup(singUp);
    }

    @ApiOperation(value = "This method is used to change the current user password")
    @PostMapping("/change-Password")
    public UserGeneralResponse changePassword(@Valid @RequestBody ChangePasswordDto changePassword) {
        return userInfoSrv.changePassword(changePassword);
    }

    @ApiOperation(value = "This method for forget the password that call by user. response is new password")
    @PostMapping("/forget-Password")
    public String forgetPassword(@Valid @RequestBody ForgetPasswordDto forgetPasswordDto) {
        return userInfoSrv.resetPassword(forgetPasswordDto, "user");
    }

    @ApiOperation(value = "This method for force change the password by owner. response is new password")
    @PostMapping("/reset-Password-byOwner")
    public String resetPassword(@Valid @RequestBody ForgetPasswordDto resetPassword) {
        return userInfoSrv.resetPassword(resetPassword, "owner");
    }

    @ApiOperation(value = "This method is for changing the user's status.")
    @PostMapping("/change-Status-User")
    public UserGeneralResponse changeStatusUser(@Valid @RequestBody ChangeStatusUserDto statusUser) {
        return userInfoSrv.changeStatusUser(statusUser);
    }
}
