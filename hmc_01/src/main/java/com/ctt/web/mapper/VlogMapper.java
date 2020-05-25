package com.ctt.web.mapper;

import com.ctt.web.bean.Vlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description
 * @auther HHF
 * @create 2020-04-17 下午 1:55
 */
public interface VlogMapper {

    /**
     * 获取分页数据
     *
     * @param searchName
     * @return
     */
    public List<Vlog> getPageList(@Param("searchName") String searchName);

    public void create(Vlog vlog);

    public void update(Vlog vlog);

    public void delete(Vlog vlog);

    public Vlog getVlog(String id);

}
