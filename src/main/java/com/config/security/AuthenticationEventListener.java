package com.config.security;

import com.service.ClientService;
import com.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;


public class AuthenticationEventListener extends DaoAuthenticationProvider {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ClientService clientService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        try {
            Authentication fullAuthentication = SecurityContextHolder.getContext().getAuthentication();
            String clientId = null;
            if (fullAuthentication.getPrincipal().getClass().isAssignableFrom(User.class)) {
                clientId = ((User)fullAuthentication.getPrincipal()).getUsername();
            }
            if(!clientService.matchUserWithClient(userDetails.getUsername(),clientId)){
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }

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