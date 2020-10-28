package com.ctt.web.controller;

import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.utils.EncryptUtil;
import com.ctt.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterChainProxy;
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
    public WebResBean info(Authentication authentication) {
        String name = authentication.getName();
        System.out.println(name);
        WebResBean rsb = userService.getInfo(name);
        rsb.setCode(SystemStatusEnum.E_20000.getCode());
        return rsb;
    }

    @PostMapping("logout")
    public WebResBean logout() {
        WebResBean rsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        return rsb;
    }

    @GetMapping("session/invalid")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public WebResBean sessionInvalid() {
        return WebResBean.createResBean(SystemStatusEnum.E_20011);
    }


}
