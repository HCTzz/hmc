package com.ctt.web.config.security;

import com.ctt.utils.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Administrator
 * @Description
 * @create 2020-05-28 下午 3:55
 */
public class CustomBCryptPasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if(StringUtils.isBlank(rawPassword)){
            return false;
        }
        String decrypt = EncryptUtil.aesDecrypt(rawPassword.toString());
        return super.matches(decrypt,encodedPassword);
    }

    public static void main(String[] args) {
        System.out.println(new CustomBCryptPasswordEncoder().encode("111111"));
    }

}
