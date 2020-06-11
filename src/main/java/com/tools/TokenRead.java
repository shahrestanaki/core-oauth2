package com.tools;

import com.exception.AppException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class TokenRead {
    public static String getUserName() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();

            return authentication.getPrincipal().toString();
        } catch (Exception exp) {
            throw new AppException("token.error.getusername");
        }
    }

    public static Collection<? extends GrantedAuthority> getRols() {
        try {
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();

            return authentication.getAuthorities();
        } catch (Exception exp) {
            throw new AppException("token.error.getRols");
        }
    }
}
