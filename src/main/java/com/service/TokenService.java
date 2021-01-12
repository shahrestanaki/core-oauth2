package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void logOut(String username, String clientId) {
        String sql = "delete from oauth_refresh_token where TOKEN_ID = (select REFRESH_TOKEN from oauth_access_token where USER_NAME = '" + username + "' and CLIENT_ID = '" + clientId + "')";
        jdbcTemplate.update(sql);
        System.out.println(sql);

        sql = " delete from oauth_access_token where USER_NAME = '" + username + "' and CLIENT_ID = '" + clientId + "'";
        System.out.println(sql);
        jdbcTemplate.update(sql);
    }
}
