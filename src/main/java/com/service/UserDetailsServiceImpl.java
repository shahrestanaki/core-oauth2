package com.service;

import com.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private com.service.UserInfoService userInfoDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoDAO.getUserByToken(userName);
        Set<GrantedAuthority> authority = new HashSet<>();
        List<String> roles = Arrays.asList(userInfo.getRole().split(","));
        roles.forEach(item ->
                authority.add(new SimpleGrantedAuthority(item))
        );
        return new User(userInfo.getUserName(), userInfo.getPassword(), authority);
    }
}
