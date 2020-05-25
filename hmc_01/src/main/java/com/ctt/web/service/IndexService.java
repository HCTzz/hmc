package com.ctt.web.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctt.web.mapper.IndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-24 上午 11:07
 */
@Service
public class IndexService {

    @Autowired
    IndexMapper indexMapper;

    public JSONObject getTopCountData(){
        return indexMapper.getTopCountData();
    }

    public JSONObject getLineChartData(){
        JSONObject object = new JSONObject();
        List<Map<String, Object>> countList = indexMapper.getDayCountList();
        ArrayList<Object> log = new ArrayList<>(8);
        ArrayList<Object> photo = new ArrayList<>(8);
        ArrayList<Object> video = new ArrayList<>(8);
        ArrayList<Object> day = new ArrayList<>(8);
        countList.forEach(m -> {
            log.add(m.get("videoCount"));
            photo.add(m.get("photoCount"));
            video.add(m.get("logCount"));
            day.add(m.get("day"));
        });
        object.put("log",log);
        object.put("photo",photo);
        object.put("video",video);
        object.put("day",day);
        return  object;
    }

}
