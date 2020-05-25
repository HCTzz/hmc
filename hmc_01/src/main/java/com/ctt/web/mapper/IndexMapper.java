package com.ctt.web.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.omg.PortableInterceptor.ServerIdHelper;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-24 上午 11:08
 */
public interface IndexMapper {

    @ResultType(JSONObject.class)
    @Select("select (select COUNT(fileKey) from sys_file where fileWidth is not null) videoCount,(select COUNT(id) from photo where pid != 0) photoCount,(select COUNT(id) from vlog where deleteStatus = 1)logCount")
    JSONObject getTopCountData();

    /**
     * @return
     */
    List<Map<String,Object>> getDayCountList();

}
