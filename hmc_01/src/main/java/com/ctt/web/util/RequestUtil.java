package com.ctt.web.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HHF
 * @Description
 * @create 2021-01-09 下午 3:50
 */
public class RequestUtil {

    public static HttpServletRequest getRequest(){
        RequestAttributes localRequestAttributes = RequestContextHolder.getRequestAttributes();
        if ((localRequestAttributes instanceof ServletRequestAttributes)) {
            HttpServletRequest localHttpServletRequest = ((ServletRequestAttributes) localRequestAttributes)
                    .getRequest();
            if (localHttpServletRequest != null) {
                return localHttpServletRequest;
            }
        }
        return null;
    }
}
