package com.ctt.web.config.security;

import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.utils.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-28 下午 5:13
 */
@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //LockedException
        //DisabledException
        //AccountExpiredException
        WebResBean resBean = null;
        if(e instanceof SessionAuthenticationException){
            resBean = WebResBean.createResBean(SystemStatusEnum.E_20014);
        }else{
            resBean = WebResBean.createResBean(SystemStatusEnum.E_10010);
        }
        ResponseUtils.out(httpServletRequest,httpServletResponse,resBean);
    }
}
