package com.ctt.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author HHF
 * @Description
 * @create 2020-07-26 下午 5:59
 */
public class Test extends AbstractRememberMeServices {

    protected Test(String key, UserDetailsService userDetailsService) {
        super(key, userDetailsService);
    }

    public String addStrings(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        StringBuffer sb = new StringBuffer();
        int c = 0;
        while (len1 > 0 || len2 > 0){
            int i = len1 > 0 ? num1.charAt(len1 - 1) - '0' : 0;
            int j = len2 > 0 ? num1.charAt(len2 - 1) - '0' : 0;
            sb.append((i + j + c) % 10);
            c = (i + j + c) / 10;
            len1 -- ;
            len2 -- ;
        }
        if(c > 0){
            sb.append(c);
        }
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
//        int a = 2;
//        int b = 3;
//        System.out.println(a | b);
        Test test = new Test("1231", new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                return null;
            }
        });
        String[] strings = test.decodeCookie("QUJhc2YlMkZsU2NHRSUyQlR4elVmRE92QkElM0QlM0Q6Vk9XbUYwdWF2bTJHQmlFVExhTmFrUSUzRCUzRA");
        Arrays.stream(strings).forEach(System.out::println);
    }

    @Override
    protected void onLoginSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] strings, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws RememberMeAuthenticationException, UsernameNotFoundException {
        return null;
    }
}
