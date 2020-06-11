package com.config.security;

import com.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    public WebSecurityConfiguration(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

  /*  @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }*/

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        if (passwordEncoder == null) {
            passwordEncoder = DefaultPasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
        return passwordEncoder;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        if (userDetailsService == null) {
            userDetailsService = new UserDetailsServiceImpl();/*JdbcDaoImpl();*/
            /* ((JdbcDaoImpl) userDetailsService).setDataSource(dataSource);*/
        }
        return userDetailsService;
    }

    //----------------------------------
    @Bean
    public AuthenticationEventListener authenticationProvider() {
        AuthenticationEventListener provider = new AuthenticationEventListener();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

}
