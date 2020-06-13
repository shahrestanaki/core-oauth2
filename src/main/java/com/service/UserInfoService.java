package com.service;


import com.exception.AppException;
import com.model.UserInfo;
import com.repository.UserDetailsRepository;
import com.tools.CorrectDate;
import com.tools.GeneralTools;
import com.tools.TokenRead;
import com.view.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserInfoService {

    @Autowired
    private UserDetailsRepository userRepo;

    @Autowired
    private TokenService tokenService;
    private Mapper mapper;

    public UserSingupView singup(SingUpDto singUp) {
        UserInfo userInfo = new UserInfo(singUp.getUserName(), new BCryptPasswordEncoder().encode(singUp.getPassword()),
                singUp.getRole().name(), singUp.getOwnerKey());
        userRepo.save(userInfo);

        return mapper.map(userInfo, UserSingupView.class);
    }

    public UserInfo getUserInfoByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    public UserInfo getUserByToken(String userName) {
        UserInfo user = userRepo.findByUserName(userName);
        if (user == null) {
            throw new AppException("User not Exist");
        } else if (user.isLockStatus()) {
            Date unlockDate = CorrectDate.addToDate(user.getLockDate(), 10, Calendar.MINUTE);
            if (unlockDate.after(new Date())) {
                throw new AppException("User is Locked as " + user.getLockDate());
            }
        } else if (!user.isActive()) {
            throw new AppException("User Deactivate as " + user.getChangeDate());
        }
        return user;
    }

    @Deprecated
    public List<UserInfo> getAllActiveUserInfo() {
        return userRepo.findAllByActive(true);
    }

    @Deprecated
    public UserInfo getUserInfoById(Integer id) {
        return userRepo.findById(id);
    }

    @Deprecated
    public UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        return userRepo.save(userInfo);
    }

    @Deprecated
    public UserInfo updateUser(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userRepo.findById(id);
        userInfo.setUserName(userRecord.getUserName());
        userInfo.setPassword(userRecord.getPassword());
        /*userInfo.setRole(userRecord.getRole());*/
        userInfo.setActive(userRecord.isActive());
        return userRepo.save(userInfo);
    }

    @Deprecated
    public void deleteUser(Integer id) {
        userRepo.deleteById(id);
    }

    public UserInfo updatePassword(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userRepo.findById(id);
        userInfo.setPassword(userRecord.getPassword());
        return userRepo.save(userInfo);
    }

    @Deprecated
    public UserInfo updateRole(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userRepo.findById(id);
        /*userInfo.setRole(userRecord.getRole());*/
        return userRepo.save(userInfo);
    }

    private UserInfo update(UserInfo userInfo, String opration, String by) {
        //TODO log
        userInfo.setChangeDate(new Date());
        return userRepo.save(userInfo);
    }

    public void submitWrongPassword(String userName) {
        UserInfo userInfo = getUserInfoByUserName(userName);
        int count = userInfo.getWrongPass() == null ? 0 : userInfo.getWrongPass();

        if (count >= 3) {// max wrong
            if (userInfo.isLockStatus()) {// update lock date
                Date unlockDate = CorrectDate.addToDate(userInfo.getLockDate(), 10, Calendar.MINUTE);
                if (unlockDate.before(new Date())) {
                    userInfo.setLockDate(new Date());
                }
            } else {// new lock
                userInfo.setLockDate(new Date());
                userInfo.setLockStatus(true);
            }
            this.update(userInfo, "submitWrongPassword", "system");
        } else {
            count++;
            userInfo.setWrongPass(count);
            this.update(userInfo, "submitWrongPassword", "system");
        }
    }

    public void submitUserLogin(String userName) {
        UserInfo userInfo = getUserInfoByUserName(userName);
        if (userInfo.isLockStatus()) {
            userInfo.setLockStatus(false);
            userInfo.setLockDate(null);
            userInfo.setWrongPass(0);
            this.update(userInfo, "submitUserLogin", "system");
        }
    }

    public UserGeneralResponse changePassword(ChangePasswordDto changePassword) {
        if (!changePassword.getNewPassword().equals(changePassword.getRepeatPassword())) {
            throw new AppException("changepassword.notequals");
        }
        UserInfo user = getUserInfoByUserName(TokenRead.getUserName());
        if (user == null) {
            throw new AppException("user.not.found");
        } else if (!user.isActive()) {
            throw new AppException("user.Deactivate");
        } else if (user.isLockStatus()) {
            throw new AppException("user.islock");
        } else if (!new BCryptPasswordEncoder().matches(changePassword.getOldPassword(), user.getPassword())) {
            throw new AppException("changepassword.oldPassword.error");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(changePassword.getNewPassword()));
        this.update(user, "changePassword", "user");

        tokenService.logOut(user.getUserName());
        return new UserGeneralResponse(HttpStatus.OK);
    }

    public String resetPassword(ForgetPasswordDto forget, String by) {
        UserInfo user = userRepo.findByUserNameAndOwnerKey(forget.getUserName(), forget.getOwnerKey());
        if (user == null) {
            throw new AppException("user.not.found");
        } else if (!user.isActive()) {
            throw new AppException("user.Deactivate");
        } else if (user.isLockStatus()) {
            throw new AppException("user.islock");
        }
        String newPassword = GeneralTools.createRandom("password", 7);
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        this.update(user, "resetPassword", by);
        return newPassword;
    }

    public UserGeneralResponse changeStatusUser(ChangeStatusUserDto statusUser) {
        UserGeneralResponse response = new UserGeneralResponse(HttpStatus.OK);
        UserInfo user = userRepo.findByUserNameAndOwnerKey(statusUser.getUserName(), statusUser.getOwnerKey());
        if (user.isActive() == statusUser.isActive() || user.isLockStatus() == statusUser.isLock()) {
            return response;
        }
        user.setActive(statusUser.isActive());
        if (statusUser.isLock()) {
            user.setLockStatus(true);
            user.setLockDate(new Date());
        } else {
            user.setLockStatus(false);
            user.setLockDate(null);
        }

        this.update(user, "changeStatusUser", "owner");
        return response;
    }
}