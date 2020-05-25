package com.ctt.web.service;

import com.alibaba.fastjson.JSONObject;
import com.ctt.web.mapper.VideoMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-21 上午 9:40
 */
@Service
public class VideoService {

    @Autowired
    private VideoMapper videoMapper;

    public JSONObject getVideoList(String searchName, Integer page, Integer limit) {
        JSONObject json = new JSONObject();
        Page<Object> startPage = null;
        if (page != null) {
            startPage = PageHelper.startPage(page, limit, "createTime desc");
        }
        List<JSONObject> videoList = videoMapper.getVideoList(searchName);
        json.put("data", videoList);
        json.put("pages", startPage != null ? startPage.getPages() : 0);
        json.put("count", startPage != null ? startPage.getTotal() : 0);
        return json;
    }

}
