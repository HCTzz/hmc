package com.ctt.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.web.bean.Vlog;
import com.ctt.web.service.VlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @Description
 * @auther HHF
 * @create 2020-04-17 下午 1:48
 */
@RestController
@RequestMapping("vlog")
public class VlogController {

    @Autowired
    private VlogService vlogService;

    @GetMapping("list")
    public WebResBean list(Integer page, Integer limit, String searchName) {
        JSONObject pageList = vlogService.getPageList(page, limit, searchName);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(pageList);
        return wsb;
    }

    @PostMapping("create")
    public WebResBean create(@NotNull Vlog vlog) {
        Vlog vlog1 = vlogService.create(vlog);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(vlog1);
        return wsb;
    }

    @PostMapping("update")
    public WebResBean update(@NotNull Vlog vlog) {
        vlogService.update(vlog);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        return wsb;
    }

    @PostMapping("delete")
    public WebResBean delete(@NotNull Vlog vlog) {
        vlogService.delete(vlog);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        return wsb;
    }

    @GetMapping("getVlog")
    public WebResBean getVlog(@RequestParam("id") String id) {
        Vlog vlog = vlogService.getVlog(id);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(vlog);
        return wsb;
    }

}
