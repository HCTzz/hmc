package com.ctt.web.service;

import com.alibaba.fastjson.JSONObject;
import com.ctt.web.bean.Photo;
import com.ctt.web.mapper.PhotoMapper;
import com.ctt.web.mapper.SysFileMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-05 上午 11:32
 */
@Service
public class PhotoService extends BaseService {

    private final String rootKey = "0";

    @Autowired
    private PhotoMapper photoMapper;

    @Autowired
    private SysFileMapper sysFileMapper;

    public JSONObject getPhotoList(String pid, Integer page, Integer limit, String searchName, String hasOwer) {
        JSONObject js = new JSONObject();
        Page<Object> pageObj = null;
        if (page != null) {
            pageObj = PageHelper.startPage(page, limit, "createTime desc");
        }

        List<Photo> pageList = photoMapper.getPhotoList(pid, searchName);
        js.put("list", pageList);
        if (page != null) {
            js.put("pages", pageObj.getPages());
        }
        if ("y".equals(hasOwer)) {
            Photo photo = photoMapper.getPhotoById(pid);
            js.put("photo", photo);
        }
        return js;
    }

    public Photo addPhoto(Photo photo) {
        photo.setId(nextId());
        if (StringUtils.isEmpty(photo.getPid())) {
            photo.setPid(rootKey);
        }
        photoMapper.addPhoto(photo);
        return photo;
    }

    public List<Photo> batchAddPhoto(List<Photo> photos) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        photos.forEach(e -> {
            e.setId(nextId());
            e.setCreateTime(now);
            e.setUpdateTime(now);
        });
        photoMapper.batchAddPhoto(photos);
        return photos;
    }

    public Photo updatePhoto(Photo photo) {
        photoMapper.updatePhoto(photo);
        return photo;
    }

    public void deletePhoto(String id) throws IOException {
        Photo photo = photoMapper.getPhotoById(id);
        if (!StringUtils.isEmpty(photo.getFileId())) {
            JSONObject file = sysFileMapper.getFileByID(photo.getFileId());
            sysFileMapper.delteFileById(photo.getFileId());
            Files.delete(Paths.get(file.getString("filePath")));
        }
        photoMapper.deletePhoto(id);
    }

}
