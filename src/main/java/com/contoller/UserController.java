package com.contoller;

import com.model.UserInfo;
import com.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserInfoService userService;

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Object getAllUser(@RequestHeader HttpHeaders requestHeader) {
        List<UserInfo> userInfos = userService.getAllActiveUserInfo();
        if (userInfos == null || userInfos.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
        return userInfos;
    }

    @PostMapping("/user")
    public UserInfo addUser(@RequestBody UserInfo userRecord) {
        return userService.addUser(userRecord);
    }

    @PutMapping("/user/{id}")
    public UserInfo updateUser(@RequestBody UserInfo userRecord, @PathVariable Integer id) {
        return userService.updateUser(id, userRecord);
    }

    @PutMapping("/user/changePassword/{id}")
    public UserInfo updateUserPassword(@Valid @RequestBody UserInfo userRecord, @PathVariable Integer id) {
        return userService.updatePassword(id, userRecord);
    }

    @PutMapping("/user/changeRole/{id}")
    public UserInfo updateUserRole(@RequestBody UserInfo userRecord, @PathVariable Integer id) {
        return userService.updateRole(id, userRecord);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserInfo> getUserById(@PathVariable Integer id) {
        UserInfo userInfo = userService.getUserInfoById(id);
        if (userInfo == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }






    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String currentUser2() {
        return "{\"id\":\"1\"}";
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    public String myuser() {
        return "{\"id\":\"2\"}";
    }




}
