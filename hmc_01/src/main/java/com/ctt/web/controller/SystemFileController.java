package com.ctt.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.ctt.constant.SystemStatusEnum;
import com.ctt.response.WebResBean;
import com.ctt.web.service.SysFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.EncoderException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 文件管理模块
 *
 * @Description
 * @auther Administrator
 * @create 2020-03-06 下午 1:45
 */
@Api(description = "用户操作接口")
@Controller
@RequestMapping("sysFile")
public class SystemFileController extends BaseController {

    @Autowired
    private SysFileService sysFileService;

    @ApiOperation(value = "获取otp", notes = "通过手机号获取OTP验证码")
    @ApiImplicitParam(name = "telephone", value = "电话号码", paramType = "query", required = true, dataType = "Integer")
    @GetMapping("filelist")
    @ResponseBody
    public WebResBean filelist(String filePkey, String fileKey) {
        List<JSONObject> list = sysFileService.getFileList(fileKey, filePkey);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(list);
        return wsb;
    }

    @PostMapping("addFolder")//String filePkey,String fileName
    @ResponseBody
    public WebResBean addFolder(@RequestBody JSONObject json) {
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(sysFileService.addFolder(json.getString("filePkey"), json.getString("fileName")));
        return wsb;
    }

    @GetMapping("getFile")
    @ResponseBody
    public WebResBean getFile(String fileKey) {
        JSONObject json = sysFileService.getFileByID(fileKey);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(json);
        return wsb;
    }

    @RequestMapping("fileUpload")
    @ResponseBody
    public WebResBean fileUpload(@RequestParam("file") MultipartFile file) throws IOException, EncoderException {
        JSONObject json = sysFileService.uploadFile(file);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        wsb.setData(json);
        return wsb;
    }

    @DeleteMapping("deleteFile")
    @ResponseBody
    public WebResBean deleteFile(String fileKey) throws IOException {
        sysFileService.deleteFile(fileKey);
        WebResBean wsb = WebResBean.createResBean(SystemStatusEnum.E_20000);
        return wsb;
    }


    @RequestMapping("priviewImg")
    @ResponseBody
    public void priivewImg(String fileKey, HttpServletResponse response) throws IOException {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            JSONObject file = sysFileService.getFileByID(fileKey);
            String coverPath = file.getString("coverPath");
            if (StringUtils.isBlank(coverPath)) {
                coverPath = file.getString("filePath");
            }
            Files.copy(Paths.get(coverPath), outputStream);
            outputStream.flush();
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping("downloadFile")
    public void downloadFile(String fileKey, HttpServletResponse response) throws IOException {
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            JSONObject file = sysFileService.getFileByID(fileKey);
            String filePath = file.getString("filePath");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String((file.getString("fileName") + "." + file.getString("fileExt")).getBytes("UTF-8"), "ISO-8859-1"));
            Files.copy(Paths.get(filePath), outputStream);
            outputStream.flush();
        } catch (Exception e) {
            throw e;
        }
    }

}
