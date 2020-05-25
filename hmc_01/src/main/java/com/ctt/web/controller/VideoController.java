package com.ctt.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.web.service.SysFileService;
import com.ctt.web.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 视频后端控制器
 *
 * @auther HHF
 * @create 2020-05-21 上午 9:38
 */
@RestController
@RequestMapping("video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private SysFileService sysFileService;

    @GetMapping("list")
    public WebResBean list(String searchName, Integer page, Integer limit) {
        JSONObject data = videoService.getVideoList(searchName, page, limit);
        WebResBean resBean = WebResBean.createResBean(SystemStatusEnum.E_20000);
        resBean.setData(data);
        return resBean;
    }

    @RequestMapping("priviewVideo")
    public void priviewVideo(String fileKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rangeString = request.getHeader("Range");
        long range = Long.valueOf(rangeString.substring(rangeString.indexOf("=") + 1, rangeString.indexOf("-")));
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            JSONObject file = sysFileService.getFileByID(fileKey);
            response.setHeader("Content-Type", "video/" + file.getString("fileExt"));
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getString("fileName") + "." + file.getString("fileExt"), "UTF-8"));
            int fileSize = file.getInteger("fileSize");
            response.setContentLength(fileSize);
            response.setHeader("Content-Range", String.valueOf(range + (fileSize-1)));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/9767057-1323779115364");
            String filePath = file.getString("filePath");
            Files.copy(Paths.get(filePath), outputStream);
            outputStream.flush();
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("updateVideo")
    public WebResBean updateVideo(@NotNull String fileName, @NotNull String fileKey) {
        sysFileService.updateFileName(fileName, fileKey);
        return WebResBean.createResBean(SystemStatusEnum.E_20000);
    }

}
