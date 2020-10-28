package com.ctt.web.config.security;

import com.ctt.utils.EncryptUtil;
import com.ctt.web.bean.User;
import com.ctt.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-28 下午 3:13
 */
@Service
public class DbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUerName(s);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        UserInfo userInfo = new UserInfo(user.getUsername(), user.getPassword(), simpleGrantedAuthorities);
        userInfo.setAvatar(user.getAvatar());
        userInfo.setRoles(user.getRoles());
        return userInfo;
    }
}
