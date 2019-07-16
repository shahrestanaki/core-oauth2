package com.service.entity;

import com.model.entity.User;
import com.repository.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("************* UserDetailsServiceImpl: " + username);
         User  user = userRepository.loadUserByUsername(username);
        Optional<User> optionalUser = Optional.ofNullable(user);
        return Optional.ofNullable(optionalUser).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"))
                .map(UserDetailsImpl::new).get();
    }*/

    @Transactional(readOnly = true)
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        System.out.println("********** getUserByUsername*loadUserByUsername: " + username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found:" + username);
        }
        return user;
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        User user = userRepository.loadUserById(userId);
        System.out.println("********** getUserByUsername*loadUserByUsername: " + userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }
        return user;
    }
}