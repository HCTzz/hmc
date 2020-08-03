package com.ctt.web.config.security;

import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.utils.ResponseUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
 * @Description
 * @auther HHF
 * @create 2020-05-28 下午 3:46
 */
@Service
public class LoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        WebResBean resBean = WebResBean.createResBean(SystemStatusEnum.E_20011);
        ResponseUtils.out(request,response,resBean);

    }
}
