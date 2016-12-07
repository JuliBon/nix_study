package com.nixsolutions.bondarenko.study.security;

import com.nixsolutions.bondarenko.study.dao.UserDao;
import com.nixsolutions.bondarenko.study.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserDao userDao;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            User user = userDao.findByLogin(login);
            System.out.println("User : " + user);
            if (user == null) {
                System.out.println("User not found");
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