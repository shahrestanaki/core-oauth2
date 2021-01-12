package com.service;

import com.tools.TokenRead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {
    @Autowired
    public TokenService tokenService;

    @Autowired
    public UserInfoService userInfoSrv;

    public Long currentUserId() {
        return userInfoSrv.getUserInfoByUserName(TokenRead.getUserName()).getId();
    }

    public String currentUserName() {
        return TokenRead.getUserName();
    }

    public String currentUserManagement() {
        return TokenRead.getClientId();
    }

    public String getClientIdFromBasicAuth() {
        return TokenRead.getClientIdFromBasicAuth();
    }
}
