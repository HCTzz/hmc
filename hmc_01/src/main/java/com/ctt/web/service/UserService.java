package com.ctt.web.service;

import com.alibaba.fastjson.JSONObject;
import com.ctt.constant.UserStatusEnum;
import com.ctt.exception.AuthException;
import com.ctt.response.WebResBean;
import com.ctt.utils.EncryptUtil;
import com.ctt.web.bean.User;
import com.ctt.web.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.annotation.Resource;

/**
 * @Description用户操作类
 * @auther Administrator
 * @create 2020-03-05 上午 11:00
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public WebResBean login(String userName, String pwd) throws HttpRequestMethodNotSupportedException {
        JSONObject js = userMapper.findUserByUserNameAndPwd(userName, pwd);
        WebResBean rsb = new WebResBean();
        if (js == null) {
            throw  new AuthException(UserStatusEnum.S_5.getMessage());
        }
        rsb.setData(EncryptUtil.aesEncrypt(userName + ":" + pwd + ":" + EncryptUtil.getKey()));
        return rsb;
    }

    public WebResBean getInfo(String userName) {
        WebResBean rsb = new WebResBean();
        rsb.setData(userMapper.getInfo(userName));
        return rsb;
    }

    public boolean validUserName(String userName){
        User info = userMapper.getInfo(userName);
        if(info != null){
            return true;
        }
        return false;
    }

    public User findByUerName(String userName){
        return userMapper.getUserLogin(userName);
    }

}
