package com.ctt.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ctt.component.Log;
import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.web.bean.Photo;
import com.ctt.web.service.PhotoService;
import com.ctt.web.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-05 上午 11:30
 */
@RestController
@RequestMapping("photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    /**
     * 照片/相册列表
     *
     * @param pid        父级ID
     * @param searchName 搜索名称
     * @param hasOwer    返回值是否包含上级本身记录
     * @return
     */
    private final InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

    @PostMapping("photoList")
    @Log(descr = "图片列表接口")
    public WebResBean photoList(String pid, String searchName, String hasOwer, Integer page, Integer limit, Authentication authentication) throws IOException {
//        Photo photo = new Photo();
//        photo.setId("12");
//        photoService.updatePhoto(photo);
        JSONObject photoList = photoService.getPhotoList(pid, page, limit, searchName, hasOwer);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(photoList);
        return wsb;
    }

    /**
     * 新增照片/相册记录
     *
     * @param photo
     * @return
     */
    @PostMapping("addPhoto")
    @Log(descr = "添加图片")
    public WebResBean addPhoto(Photo photo) {
        Photo photo1 = photoService.addPhoto(photo);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(photo1);
        return wsb;
    }

    /**
     * 批量增加图片
     *
     * @param photos
     * @return
     */
    @PostMapping(value = "batchAddPhoto")
    public WebResBean batchAddPhoto(@RequestBody @NotNull List<Photo> photos) {
        List<Photo> photo = photoService.batchAddPhoto(photos);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(photo);
        return wsb;
    }

    /**
     * 更新图片
     *
     * @param photo
     * @return
     */
    @PostMapping("updatePhoto")
    @Log(descr = "更新图片")
    public WebResBean updatePhoto(Photo photo) {
        Photo photo1 = photoService.updatePhoto(photo);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(photo1);
        return wsb;
    }

    /**
     * 删除图片/相册
     *
     * @param id
     * @return
     * @throws IOException
     */
    @PostMapping("deletePhoto")
    @Log(descr = "删除图片")
    public WebResBean deletePhoto(String id) throws IOException {
        photoService.deletePhoto(id);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        return wsb;
    }

}
