package com.ctt.web.filter;

import com.ctt.web.service.SysFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description
 * @auther Administrator
 * @create 2020-03-25 上午 9:54
 */
@Component
public class HttpFilter implements Filter, ApplicationContextAware {

    private Logger log = LoggerFactory.getLogger(SysFileService.class);

    private static ApplicationContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.debug("request --- ip【{}】url【】time【】", request.getRemoteHost(), request.getRequestURI(), LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
