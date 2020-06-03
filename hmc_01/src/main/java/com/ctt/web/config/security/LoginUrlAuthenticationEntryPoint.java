package com.ctt.web.config.security;

import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
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
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            StringBuffer sb = new StringBuffer();
            WebResBean resBean = WebResBean.createResBean(SystemStatusEnum.E_20011);
            sb.append(resBean.toString());
            out.write(sb.toString());
            out.flush();
            out.close();
    }
}
