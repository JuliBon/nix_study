package com.nixsolutions.bondarenko.study.security;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            User user;
            try {
                user = userDao.findByLogin(login);
            } catch (UsernameNotFoundException e){
                throw new UsernameNotFoundException("Username not found");
            }
            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                    true, true, true, true, getGrantedAuthorities(user));
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getLocalizedMessage(), e);
        }
    }


    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        System.out.print("authorities :" + authorities);
        return authorities;
    }
}