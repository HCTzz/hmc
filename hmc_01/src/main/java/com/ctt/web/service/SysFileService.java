package com.ctt.web.service;

import com.alibaba.fastjson.JSONObject;
import com.ctt.constant.FileTypeEnum;
import com.ctt.utils.FileUtils;
import com.ctt.utils.ThreadUtil;
import com.ctt.web.enums.FileType;
import com.ctt.web.mapper.SysFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Description
 * @auther Administrator
 * @create 2020-03-06 下午 2:16
 */
@Service
public class SysFileService extends BaseService {

    @Resource
    private SysFileMapper sysFileMapper;

    @Autowired
    private FileUtils fileUtils;

    @Value("${baseDir}")
    private String baseDir;

    @Value("${file.rootKey:0}")
    private String rootKey;

    @Value("${fileUploadPath}")
    private String fileUploadPath;

    public JSONObject getFileByID(String filekey) {
        return sysFileMapper.getFileByID(filekey);
    }

    public List<JSONObject> getFileList(String filekey, String filePkey) {
        List<JSONObject> list = sysFileMapper.getFileList(filekey, filePkey);
        return list;
    }

    public JSONObject addFolder(String filePkey, String fileName) {
        String path = "";
        JSONObject object = sysFileMapper.getFileByID(filePkey);
        String fileKey = nextId();
        String filepath = object.getString("filePath") + File.separator + fileKey;
        FileUtils.createFolder(filepath);
        JSONObject json = new JSONObject();
        json.put("fileKey", fileKey);
        json.put("filePkey", filePkey);
        json.put("fileName", fileName);
        json.put("fileLevel", 1);
        json.put("fileEnded", 0);
        json.put("fileProperty", 1);
        json.put("fileSize", 0);
        json.put("filePath", filepath);
        json.put("fileIcon", FileTypeEnum.folder.getIcon());
        sysFileMapper.saveFile(json);
        return json;
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public JSONObject uploadFile(MultipartFile file) throws IOException, EncoderException {
        Assert.notNull(file, "文件不能为空");
        String fileKey = nextId();
        String originalFilename = file.getOriginalFilename();
        int offset = originalFilename.lastIndexOf(".");
        String filename = originalFilename.substring(0, offset);
        String ext = originalFilename.substring(offset + 1, originalFilename.length());
        FileType fileType = FileType.valueOf(ext);
        String fileAttach = fileType.getFileAttach();
        String path = getFilePath(fileAttach, fileKey, ext);
//        Files.createDirectories(Paths.get(getDirPath(fileType.getFileAttach())));
        File dfile = new File(path);
        File parentFile = dfile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        file.transferTo(dfile);
        JSONObject json = new JSONObject();
        json.put("fileExt", ext);
        json.put("fileKey", fileKey);
        json.put("filePkey", rootKey);
        json.put("fileName", filename);
        json.put("fileLevel", 1);
        json.put("fileEnded", 1);
        json.put("fileProperty", 1);
        json.put("fileSize", file.getSize());
        json.put("filePath", path);
//      json.put("fileIcon", FileTypeEnum.valueOf(fileAttach).getIcon());
        if ("video".equals(fileAttach)) {
            MultimediaObject videoInfo = new MultimediaObject(new File(path));
            MultimediaInfo info = videoInfo.getInfo();
            VideoSize size = info.getVideo().getSize();
            int width = size.getWidth();
            int height = size.getHeight();
            json.put("fileWidth", width);
            json.put("fileHeight", height);
            json.put("fileDuration", info.getDuration());
            String imgFile = nextId();
            String coverPath = getFilePath("video/cover", imgFile, "jpg");
            json.put("coverPath", coverPath);
            CompletableFuture.runAsync(() -> {
                try {
                    fileUtils.createVideoCover(path, coverPath, videoInfo);
                } catch (EncoderException | IOException e) {
                    e.printStackTrace();
                }
            }, ThreadUtil.getExector());
        }
        sysFileMapper.saveFile(json);
        json.put("filePath", this.fileUploadPath + fileKey);
        return json;
    }

    public void deleteFile(@NotNull String fileKey) throws IOException {
        JSONObject file = sysFileMapper.getFileByID(fileKey);
        if (file != null) {
            String path = file.getString("filePath");
            Files.deleteIfExists(Paths.get(path));
        }
    }

    /**
     * 获取文件路径
     *
     * @param attach
     * @param fileKey
     * @param ext
     * @return
     */
    private String getFilePath(@NotNull String attach, @NotNull String fileKey, @NotNull String ext) {
        return new StringBuilder(baseDir).append(File.separator).append(attach).append(File.separator).append(fileKey).append(".").append(ext).toString();
    }

    /**
     * @param attach
     * @return
     */
    private String getDirPath(@NotNull String attach) {
        return new StringBuilder(baseDir).append(File.separator).append(attach).toString();
    }


    public void updateFileName(String fileName, String fileKey) {
        sysFileMapper.updateFileName(fileName, fileKey);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "http://192.168.1.226:8012/sysFile/priviewImg?path=E:\\learn\\file\\img\\435757585526820864.jpg";
        url = url.replaceAll("\\+", "%2B");
        final String encode = URLEncoder.encode(url, "UTF-8");
    }
}
