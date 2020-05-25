package com.ctt.web.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Description
 * @auther Administrator
 * @create 2020-03-06 下午 2:16
 */
public interface SysFileMapper {

    public List<JSONObject> getFileList(@Param("fileKey") String fileKey, @Param("filePkey") String filePkey);

    public void saveFile(@Param("json") JSONObject json);

    JSONObject getFileByID(@Param("fileKey") String fileKey);

    void delteFileById(@Param("fileKey") String fileKey);

    @Update("update sys_file set fileName = #{fileName} where fileKey = #{fileKey} ")
    void updateFileName(@Param("fileName") String fileName, @Param("fileKey") String fileKey);
}
