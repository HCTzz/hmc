package com.ctt.web.controller;

import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.utils.EncryptUtil;
import com.ctt.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @auther Administrator
 * @create 2020-03-05 上午 10:22
 */
@RestController
@RequestMapping("user")
public class LoinController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public WebResBean login(String username, String password) throws HttpRequestMethodNotSupportedException {
        username = EncryptUtil.aesDecrypt(username);
        WebResBean rsb = userService.login(username, password);
        rsb.setCode(SystemStatusEnum.E_20000.getCode());
        return rsb;
    }

    /**
     * 查询当前登录用户的信息
     */
    @GetMapping("/info")
    public WebResBean info(String token) {
        WebResBean rsb = userService.getInfo(token);
        rsb.setCode(SystemStatusEnum.E_20000.getCode());
        return rsb;
    }

    @PostMapping("logout")
    public WebResBean logout(String token) {
        System.out.println("token : " + token);
        WebResBean rsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        return rsb;
    }


}
