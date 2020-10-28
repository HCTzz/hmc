package com.ctt.web.config.security;

import com.alibaba.fastjson.JSONObject;
import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.utils.ResponseUtils;
import io.lettuce.core.RedisClient;
import io.lettuce.core.cluster.RedisClusterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-28 下午 5:16
 */
@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //用户信息 默认w诶用户名
        UserInfo principal = (UserInfo) authentication.getPrincipal();
        //凭证，默认为密码
//        Object credentials = authentication.getCredentials();
        //额外的信息IP等
//        Object details = authentication.getDetails();
        //角色
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        WebResBean resBean = WebResBean.createResBean(SystemStatusEnum.E_20000);
        resBean.setData(principal);
        ResponseUtils.out(httpServletRequest,httpServletResponse,resBean);
    }
}
