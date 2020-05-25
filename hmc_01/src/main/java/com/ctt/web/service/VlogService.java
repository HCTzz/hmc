package com.ctt.web.service;

import com.alibaba.fastjson.JSONObject;
import com.ctt.web.bean.Vlog;
import com.ctt.web.mapper.VlogMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author HHF
 * @Description
 * @create 2020-04-17 下午 1:52
 */
@Service
public class VlogService extends BaseService {

    @Autowired
    private VlogMapper vlogMapper;

    public JSONObject getPageList(Integer page, Integer limit, String searchName) {
        JSONObject js = new JSONObject();
        Page<Object> p = null;
        if (page != null) {
            p = PageHelper.startPage(page, limit, "createTime desc");
        }
        List<Vlog> pageList = vlogMapper.getPageList(searchName);
        js.put("list", pageList);
        if (page != null) {
            js.put("total", ((Page) pageList).getTotal());
            js.put("pages", p.getPages());
        }
        return js;
    }

    public Vlog create(Vlog vlog) {
        vlog.setId(nextId());
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        vlog.setCreateTime(format);
        vlog.setUpdateTime(format);
        vlog.setDeleteStatus(Vlog.DeleteStatus.enable.getCode());
        vlogMapper.create(vlog);
        return vlog;
    }

    @Transactional
    public void update(Vlog vlog) {
        vlogMapper.update(vlog);
    }

    public void delete(Vlog vlog) {
        vlogMapper.delete(vlog);
    }

    public Vlog getVlog(String id) {
        return vlogMapper.getVlog(id);
    }

}
