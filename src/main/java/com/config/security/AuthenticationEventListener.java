package com.config.security;

import com.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;


public class AuthenticationEventListener extends DaoAuthenticationProvider {

    @Autowired
    UserInfoService userInfoService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        try {
            super.additionalAuthenticationChecks(userDetails, authentication);
            userInfoService.submitUserLogin(userDetails.getUsername());
        } catch (BadCredentialsException ex) {

            String presentedPassword = authentication.getCredentials().toString();
            if (!getPasswordEncoder().matches(userDetails.getPassword(), presentedPassword)) {
                userInfoService.submitWrongPassword(userDetails.getUsername());
            }
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }
}