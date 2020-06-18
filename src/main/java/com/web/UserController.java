package com.web;

import com.exception.AppException;
import com.model.UserInfo;
import com.service.UserInfoService;
import com.tools.GeneralTools;
import com.view.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Api(value = "users")
public class UserController {
    @GetMapping("/info")
    public String information() {
        return " this information system";
    }

    @PostMapping("/postuser")
    public UserInfo postuser(@Valid @RequestBody SingUpDto singUp) {
        if (singUp.getUserName().equals("usertest")) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName("USERlOGIN");
            return userInfo;
        } else {
            throw new AppException("user.not.found");
        }
    }


    @Autowired
    private UserInfoService userInfoSrv;

    @PreAuthorize("hasRole('ROLE_MANAGE')")
    @ApiOperation(value = "ROLE : MANAGE")
    @PostMapping("/sign-up")
    public UserGeneralResponse singUp(@Valid @RequestBody SingUpDto singUp) {
        return userInfoSrv.singup(singUp);
    }


    @ApiIgnore
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/sign-up-management")
    public UserGeneralResponse singUpManagement(@Valid @RequestBody SingUpDto singUp) {
        return userInfoSrv.singUpManagement(singUp);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "ROLE:USER -This method is used to change the current user password")
    @PostMapping("/change-Password")
    public UserGeneralResponse changePassword(@Valid @RequestBody ChangePasswordDto changePassword) {
        return userInfoSrv.changePassword(changePassword);
    }

    @PreAuthorize("hasRole('ROLE_MANAGE')")
    @ApiOperation(value = "ROLE:MANAGE-This method for forget the password that call by user. response is new password")
    @PostMapping("/forget-Password")
    public String forgetPassword(@Valid @RequestBody ForgetPasswordDto forgetPasswordDto) {
        return userInfoSrv.resetPassword(forgetPasswordDto, "user");
    }

    @PreAuthorize("hasRole('ROLE_MANAGE')")
    @ApiOperation(value = "ROLE:MANAGE-This method for force change the password by owner. response is new password")
    @PostMapping("/reset-Password-byOwner")
    public String resetPassword(@Valid @RequestBody ForgetPasswordDto resetPassword) {
        return userInfoSrv.resetPassword(resetPassword, "management");
    }

    @PreAuthorize("hasRole('ROLE_MANAGE')")
    @ApiOperation(value = "ROLE:MANAGE-This method is for changing the user's status.")
    @PostMapping("/change-Status-User")
    public UserGeneralResponse changeStatusUser(@Valid @RequestBody ChangeStatusUserDto statusUser) {
        return userInfoSrv.changeStatusUser(statusUser);
    }

    @PreAuthorize("hasRole('ROLE_MANAGE')")
    @ApiOperation(value = "ROLE:MANAGE-This method return list of users.")
    @PostMapping("/list")
    public SimplePageResponse<UserView> list(@Valid @RequestBody UserSearchView search) {
        return userInfoSrv.list(GeneralTools.convertToCriteriaList(search, ""));
    }


    @ApiOperation(value = "This method for logout any users.")
    @PostMapping("/logout")
    public UserGeneralResponse logout() {
        return userInfoSrv.logout();
    }
}
