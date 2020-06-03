package com.ctt.web.config.security;

import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.utils.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**用来解决认证过的用户访问无权限资源时的异常
 * @Description
 * @auther HHF
 * @create 2020-05-28 下午 4:16
 */
@Service
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        WebResBean resBean = WebResBean.createResBean(SystemStatusEnum.E_502);
        ResponseUtils.out(httpServletRequest,httpServletResponse,resBean);
    }
}
