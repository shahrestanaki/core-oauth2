package com.service;


import
        com.exception.AppException;
import com.model.UserInfo;
import com.repository.UserDetailsRepository;
import com.tools.CorrectDate;
import com.view.ChangePasswordDto;
import com.view.SingUpDto;
import com.view.UserGeneralResponse;
import com.view.UserSignUpResponse;
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
    private UserDetailsRepository userDatailsRepository;

    public UserSignUpResponse singup(SingUpDto singUp) {
        UserInfo userInfo = new UserInfo(singUp.getUserName(), new BCryptPasswordEncoder().encode(singUp.getPassword()), singUp.getRole().name());
        userDatailsRepository.save(userInfo);

        UserSignUpResponse result = new UserSignUpResponse();
        result.setResult(HttpStatus.OK);
        return result;
    }

    public UserInfo getUserInfoByUserName(String userName) {
        return userDatailsRepository.findByUserName(userName);
    }

    public UserInfo getUserByToken(String userName) {
        UserInfo user = userDatailsRepository.findByUserName(userName);
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
        return userDatailsRepository.findAllByActive(true);
    }

    @Deprecated
    public UserInfo getUserInfoById(Integer id) {
        return userDatailsRepository.findById(id);
    }


    public UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        return userDatailsRepository.save(userInfo);
    }

    @Deprecated
    public UserInfo updateUser(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDatailsRepository.findById(id);
        userInfo.setUserName(userRecord.getUserName());
        userInfo.setPassword(userRecord.getPassword());
        /*userInfo.setRole(userRecord.getRole());*/
        userInfo.setActive(userRecord.isActive());
        return userDatailsRepository.save(userInfo);
    }

    @Deprecated
    public void deleteUser(Integer id) {
        userDatailsRepository.deleteById(id);
    }

    public UserInfo updatePassword(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDatailsRepository.findById(id);
        userInfo.setPassword(userRecord.getPassword());
        return userDatailsRepository.save(userInfo);
    }

    @Deprecated
    public UserInfo updateRole(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDatailsRepository.findById(id);
        /*userInfo.setRole(userRecord.getRole());*/
        return userDatailsRepository.save(userInfo);
    }

    private UserInfo update(UserInfo userInfo) {
        userInfo.setChangeDate(new Date());
        return userDatailsRepository.save(userInfo);
    }

    //ToDO only update fields submit
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
            this.update(userInfo);
        } else {
            count++;
            userInfo.setWrongPass(count);
            this.update(userInfo);
        }
    }

    public void submitUserLogin(String userName) {
        UserInfo userInfo = getUserInfoByUserName(userName);
        userInfo.setLockStatus(false);
        userInfo.setLockDate(null);
        userInfo.setWrongPass(0);
        this.update(userInfo);
    }

    public UserGeneralResponse changePassword(ChangePasswordDto changePassword) {
        if (!changePassword.getNewPassword().equals(changePassword.getRepeatPassword())) {
            throw new AppException("ui.changepassword.notequals");
        }
        //getUserInfoByUserName();
        return null;
    }
}