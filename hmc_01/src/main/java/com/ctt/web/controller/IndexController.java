package com.ctt.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面跳转
 *
 * @author yudian-it
 * @date 2017/12/27
 */
@RestController
public class IndexController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String go2Index() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:/index";
    }

    @Autowired
    private IndexService indexService;

    @GetMapping("topCount")
    public WebResBean topCount(){
        JSONObject topCountData = indexService.getTopCountData();
        WebResBean resBean = WebResBean.createResBean(SystemStatusEnum.E_20000);
        resBean.setData(topCountData);
        return resBean;
    }

    @GetMapping("lineChart")
    public WebResBean lineChart(){
        JSONObject lineChartData = indexService.getLineChartData();
        WebResBean resBean = WebResBean.createResBean(SystemStatusEnum.E_20000);
        resBean.setData(lineChartData);
        return  resBean;

    }

}
