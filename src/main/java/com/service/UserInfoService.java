package com.service;


import com.model.UserInfo;
import com.repository.UserDetailsRepository;
import com.view.SingUpDto;
import com.view.UserSignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        return null;
    }


    public UserInfo getUserInfoByUserName(String userName) {
        return userDatailsRepository.findByUserNameAndActive(userName, true);
    }

    public List<UserInfo> getAllActiveUserInfo() {
        return userDatailsRepository.findAllByActive(true);
    }

    public UserInfo getUserInfoById(Integer id) {
        return userDatailsRepository.findById(id);
    }

    public UserInfo addUser(UserInfo userInfo) {
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
        return userDatailsRepository.save(userInfo);
    }

    public UserInfo updateUser(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDatailsRepository.findById(id);
        userInfo.setUserName(userRecord.getUserName());
        userInfo.setPassword(userRecord.getPassword());
        /*userInfo.setRole(userRecord.getRole());*/
        userInfo.setActive(userRecord.isActive());
        return userDatailsRepository.save(userInfo);
    }

    public void deleteUser(Integer id) {
        userDatailsRepository.deleteById(id);
    }

    public UserInfo updatePassword(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDatailsRepository.findById(id);
        userInfo.setPassword(userRecord.getPassword());
        return userDatailsRepository.save(userInfo);
    }

    public UserInfo updateRole(Integer id, UserInfo userRecord) {
        UserInfo userInfo = userDatailsRepository.findById(id);
        /*userInfo.setRole(userRecord.getRole());*/
        return userDatailsRepository.save(userInfo);
    }
}