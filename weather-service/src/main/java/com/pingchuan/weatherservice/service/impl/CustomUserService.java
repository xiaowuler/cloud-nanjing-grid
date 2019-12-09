package com.pingchuan.weatherservice.service.impl;

import com.pingchuan.domain.Caller;
import com.pingchuan.weatherservice.service.CallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CustomUserService implements UserDetailsService {

    @Autowired
    private CallerService callerService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Caller caller = callerService.findOneByLoginName(loginName);
        String encodePassword = passwordEncoder.encode(caller.getLoginPassword());
        User user = new User(caller.getLoginName(), encodePassword, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        return user;
    }
}
