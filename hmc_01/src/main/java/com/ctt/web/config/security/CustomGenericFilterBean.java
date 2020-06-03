package com.ctt.web.config.security;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**自定义过滤器(securityConfig中配置)
 * @Description
 * @auther HHF
 * @create 2020-05-28 下午 4:05
 */
public class CustomGenericFilterBean extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}
