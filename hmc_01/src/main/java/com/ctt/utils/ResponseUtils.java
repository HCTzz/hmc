package com.ctt.utils;

import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-29 上午 11:08
 */
public class ResponseUtils {

    public static void out(HttpServletRequest request, HttpServletResponse response, WebResBean resBean) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        StringBuffer sb = new StringBuffer();
        sb.append(resBean.toString());
        out.write(sb.toString());
        out.flush();
        out.close();
    }

}
