package com.service;


import com.enump.RoleEnum;
import com.exception.AppException;
import com.model.UserInfo;
import com.repository.UserDetailsRepository;
import com.service.mapper.UserMapper;
import com.service.search.SearchCriteria;
import com.service.search.SearchCriteriaList;
import com.tools.GeneralDateTools;
import com.tools.GeneralTools;
import com.tools.GetResourceBundle;
import com.tools.TokenRead;
import com.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

@Service
public class UserInfoService {
/*    @Qualifier("defaultTokenServices")
    ConsumerTokenServices tokenServices2;*/
/*
    @Qualifier("tokenStore")
    TokenStore tokenStore2;*/

    private final int MAX_LOCK_TIME = Integer.parseInt(GetResourceBundle.getConfig.getString("max.user.lock.time"));
    private final int MAX_PASS_WRONG = Integer.parseInt(GetResourceBundle.getConfig.getString("max.user.wrong.pass"));

    @Autowired
    private UserDetailsRepository userRepo;

    @Autowired
    private TokenService tokenSrv;

/*    @Autowired
    private TokenStore tokenStore;*/

    public UserGeneralResponse singup(SingUpDto singUp) {
        String manager = TokenRead.getUserName();
        UserInfo userInfo = new UserInfo(singUp.getUserName(), new BCryptPasswordEncoder().encode(singUp.getPassword()),
                singUp.getRoleStr(), manager);
        userRepo.save(userInfo);
        return new UserGeneralResponse(HttpStatus.OK);
    }

    public UserInfo getUserInfoByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    public UserInfo getUserByToken(String userName) {
        UserInfo user = userRepo.findByUserName(userName);
        if (user == null) {
            throw new AppException("User not Exist");
        } else if (user.getLockStatus()) {
            Date unlockDate = GeneralDateTools.addToDate(user.getLockDate(), MAX_LOCK_TIME, Calendar.MINUTE);
            if (unlockDate.after(new Date())) {
                throw new AppException("User is Locked as " + user.getLockDate());
            }
        } else if (!user.getActive()) {
            throw new AppException("User Deactivate as " + user.getChangeDate());
        }
        return user;
    }

    private UserInfo update(UserInfo userInfo, String opration, String by) {
        //TODO log
        userInfo.setChangeDate(new Date());
        return userRepo.save(userInfo);
    }

    public void submitWrongPassword(String userName) {
        UserInfo userInfo = getUserInfoByUserName(userName);
        int count = userInfo.getWrongPass() == null ? 0 : userInfo.getWrongPass();

        if (count >= MAX_PASS_WRONG) {// max wrong
            if (userInfo.getLockStatus()) {// update lock date
                Date unlockDate = GeneralDateTools.addToDate(userInfo.getLockDate(), MAX_LOCK_TIME, Calendar.MINUTE);
                if (unlockDate.before(new Date())) {
                    userInfo.setLockDate(new Date());
                }
            } else {// new lock
                userInfo.setLockDate(new Date());
                userInfo.setLockStatus(true);
            }
        } else {
            count++;
            userInfo.setWrongPass(count);
        }
        this.update(userInfo, "submitWrongPassword", "system");
    }

    public void submitUserLogin(String userName) {
        UserInfo userInfo = getUserInfoByUserName(userName);
        if (userInfo.getLockStatus() || userInfo.getWrongPass() > 0) {
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
        } else if (!user.getActive()) {
            throw new AppException("user.Deactivate");
        } else if (user.getLockStatus()) {
            throw new AppException("user.islock");
        } else if (!new BCryptPasswordEncoder().matches(changePassword.getOldPassword(), user.getPassword())) {
            throw new AppException("changepassword.oldPassword.error");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(changePassword.getNewPassword()));
        user.setRole(changeRole(user.getRole(), RoleEnum.ROLE_CHANGE_PASSWORD.name(), false));
        this.update(user, "changePassword", "user");


        tokenSrv.logOut(user.getUserName(), TokenRead.getClientId());
        return new UserGeneralResponse(HttpStatus.OK);
    }

    public String resetPassword(ForgetPasswordDto forget, String by) {
        UserInfo user = userRepo.findByUserNameAndManager(forget.getUserName(), TokenRead.getUserName());
        if (user == null) {
            throw new AppException("user.not.found");
        } else if (!user.getActive()) {
            throw new AppException("user.Deactivate");
        } else if (user.getLockStatus()) {
            throw new AppException("user.islock");
        }
        String newPassword = GeneralTools.createRandom("number", 7);
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        user.setRole(changeRole(user.getRole(), RoleEnum.ROLE_CHANGE_PASSWORD.name(), true));
        user.setChangePassDate(new Date());
        this.update(user, "resetPassword", by);
        tokenSrv.logOut(user.getUserName(), TokenRead.getClientId());
        return newPassword;
    }

    private String changeRole(String currentRole, String role, boolean add) {
        List<String> roleList = new ArrayList<>();
        roleList.addAll(Arrays.asList(currentRole.split(",")));

        roleList.removeIf(o -> o.equals(role));
        if (add) {
            roleList.add(role);
        }
        return String.join(",", roleList);
    }

    public UserGeneralResponse changeStatusUser(ChangeStatusUserDto statusUser) {
        UserGeneralResponse response = new UserGeneralResponse(HttpStatus.OK);
        UserInfo user = userRepo.findByUserNameAndManager(statusUser.getUserName(), TokenRead.getUserName());
        if (user.getActive() == statusUser.isActive() && user.getLockStatus() == statusUser.isLock()) {
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

        this.update(user, "changeStatusUser", "management");
        tokenSrv.logOut(user.getUserName(), TokenRead.getClientId());
        return response;
    }


    //TODO
    public UserGeneralResponse singUpManagement(@Valid SingUpManageDto singUp) {
        UserInfo userInfo = new UserInfo(singUp.getUserName(), new BCryptPasswordEncoder().encode(singUp.getPassword()),
                singUp.getRole().name(), "Admin");
        userRepo.save(userInfo);
        return new UserGeneralResponse(HttpStatus.OK);
    }

    public SimplePageResponse<UserView> list(SearchCriteriaList search) {
        HashSet<SearchCriteria> filter = search.getSearch();
        filter.add(new SearchCriteria("manager", ":", TokenRead.getUserName()));
        search.setSearch(filter);

        SimplePageResponse<UserInfo> listNews = userRepo.findAllCriteria(search);
        List<UserView> listView = UserMapper.INSTANCE.listMap(listNews.getContent());
        SimplePageResponse<UserView> result = new SimplePageResponse<>(listView, listNews.getCount());
        return result;
    }

    public UserGeneralResponse logout() {
        tokenSrv.logOut(TokenRead.getUserName(), TokenRead.getClientId());
        return new UserGeneralResponse(HttpStatus.OK);
    }

/*    public void revokeToken(String tokenId) {
        tokenServices.revokeToken(tokenId);
    }*/

/*    public void revokeRefreshToken(String tokenId) {
        if (tokenStore instanceof JdbcTokenStore) {
            ((JdbcTokenStore) tokenStore2).removeRefreshToken(tokenId);
        }
    }*/
}