package com.ctt.web.mapper;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-21 上午 9:43
 */
public interface VideoMapper {

    List<JSONObject> getVideoList(String searchName);

}
