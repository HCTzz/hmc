package com.ctt.web.controller;

import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.utils.detect.DetectIdCardUtil;
import com.ctt.web.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author HHF
 * @Description
 * @create 2020-09-18 下午 3:06
 */
@RestController
@RequestMapping("detect")
public class DelectedController {

    @Autowired
    private SysFileService sysFileService;

    @RequestMapping("idcard")
    public WebResBean idcard(String imgs) throws Exception {
        String path = sysFileService.uploadImgWithBase64Str(imgs);
        String card = DetectIdCardUtil.idCard(path);
        WebResBean resBean = WebResBean.createResBean(SystemStatusEnum.E_20000);
        if(!StringUtils.isEmpty(card) && card.length() >= 15){
            int length = card.length();
            resBean.setData(card);
        }else{
            resBean.setCode(SystemStatusEnum.E_30000.getCode());
            resBean.setMessage("未检测到有效的身份证号码");
        }
//        Files.delete(Paths.get(path));
        return resBean;
    }

}
