package com.escola.authserver.config;

import com.escola.authserver.dto.UserLoginDto;
import com.escola.authserver.form.UserLoginForm;
import com.escola.authserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserLoginDto userLoginDto = userService.getUserDetails(new UserLoginForm(username));

        List<GrantedAuthority> authorities = userLoginDto.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new User(userLoginDto.getUsername(), userLoginDto.getPassword(), authorities);
    }
}
