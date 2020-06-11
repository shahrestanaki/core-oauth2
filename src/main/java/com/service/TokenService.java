package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final String clientId = "clientId";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void logOut(String username) {
        String sql = "delete from oauth_refresh_token where TOKEN_ID = (select REFRESH_TOKEN from oauth_access_token where USER_NAME = '" + username + "') ;" +
                " delete from oauth_access_token where USER_NAME = '" + username + "' and CLIENT_ID = '" + clientId + "'";
        jdbcTemplate.execute(sql);
    }
}
