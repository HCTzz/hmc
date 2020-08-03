package com.ctt.utils;

import com.alibaba.fastjson.JSONObject;
import com.ctt.web.mapper.SysFileMapper;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ws.schild.jave.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yudian-it
 * @date 2017/11/13
 */
@Component
public class FileUtils {

    public static final String DEFAULT_CONVERTER_CHARSET = System.getProperty("sun.jnu.encoding");

    Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    SysFileMapper sysFileMapper;

    @Value("${rootFileKey}")
    private String rootFileKey;

    @Value(("${rootpath}"))
    private String rootpath;

    @PostConstruct
    public void initFileRoot() {
        System.out.println("rootpath : " + rootpath);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(rootFileKey)) {
            JSONObject j = sysFileMapper.getFileByID(rootFileKey);
            if (j != null) {
                return;
            }
            JSONObject json = new JSONObject();
            json.put("fileKey", 0);
            json.put("filePkey", -1);
            json.put("fileName", "/");
            json.put("fileLevel", 0);
            json.put("fileEnded", 0);
            json.put("fileProperty", 1);
            json.put("fileSize", 0);
            json.put("filePath", rootpath);
            sysFileMapper.saveFile(json);
            File f = new File(rootpath);
            if (!f.exists()) {
                f.mkdirs();
            }
            log.debug("初始化根目录完成");
        }
    }

    public static void createFolder(String filepath) {
        File f = new File(filepath);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * 从url中剥离出文件名
     *
     * @param url 格式如：http://keking.ufile.ucloud.com.cn/20171113164107_月度绩效表模板(新).xls?UCloudPublicKey=ucloudtangshd@weifenf.com14355492830001993909323&Expires=&Signature=I D1NOFtAJSPT16E6imv6JWuq0k=
     * @return
     */
    public String getFileNameFromURL(String url) {
        // 因为url的参数中可能会存在/的情况，所以直接url.lastIndexOf("/")会有问题
        // 所以先从？处将url截断，然后运用url.lastIndexOf("/")获取文件名
        String noQueryUrl = url.substring(0, url.indexOf("?") != -1 ? url.indexOf("?") : url.length());
        String fileName = noQueryUrl.substring(noQueryUrl.lastIndexOf("/") + 1);
        return fileName;
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return
     */
    public String getSuffixFromFileName(String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return suffix;
    }

    /**
     * 从路径中获取
     *
     * @param path 类似这种：C:\Users\yudian-it\Downloads
     * @return
     */
    public String getFileNameFromPath(String path) {
        return path.substring(path.lastIndexOf(File.separator) + 1);
    }

    public List<String> listPictureTypes() {
        List<String> list = Lists.newArrayList();
        list.add("jpg");
        list.add("jpeg");
        list.add("png");
        list.add("gif");
        list.add("bmp");
        list.add("ico");
        list.add("RAW");
        return list;
    }

    public List<String> listArchiveTypes() {
        List<String> list = Lists.newArrayList();
        list.add("rar");
        list.add("zip");
        list.add("jar");
        list.add("7-zip");
        list.add("tar");
        list.add("gzip");
        list.add("7z");
        return list;
    }

    public List<String> listOfficeTypes() {
        List<String> list = Lists.newArrayList();
        list.add("docx");
        list.add("doc");
        list.add("xls");
        list.add("xlsx");
        list.add("ppt");
        list.add("pptx");
        return list;
    }

    /**
     * 判断文件编码格式
     *
     * @param path
     * @return
     */
    public String getFileEncodeUTFGBK(String path) {
        String enc = Charset.forName("GBK").name();
        File file = new File(path);
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] b = new byte[3];
            in.read(b);
            in.close();
            if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
                enc = Charset.forName("UTF-8").name();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("文件编码格式为:" + enc);
        return enc;
    }



    /**
     * 获取文件后缀
     *
     * @param url
     * @return
     */
    private String suffixFromUrl(String url) {
        String nonPramStr = url.substring(0, url.indexOf("?") != -1 ? url.indexOf("?") : url.length());
        String fileName = nonPramStr.substring(nonPramStr.lastIndexOf("/") + 1);
        return suffixFromFileName(fileName);
    }

    private String suffixFromFileName(String fileName) {
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileType;
    }

    /**
     * 获取url中的参数
     *
     * @param url
     * @param name
     * @return
     */
    public String getUrlParameterReg(String url, String name) {
        Map<String, String> mapRequest = new HashMap();
        String strUrlParam = truncateUrlPage(url);
        if (strUrlParam == null) {
            return "";
        }
        //每个键值为一组
        String[] arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else if (!arrSplitEqual[0].equals("")) {
                //只有参数没有值，不加入
                mapRequest.put(arrSplitEqual[0], "");
            }
        }
        return mapRequest.get(name);
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private String truncateUrlPage(String strURL) {
        String strAllParam = null;
        strURL = strURL.trim();
        String[] arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 截取视频封面
     *
     * @param videoPath
     * @param imgPath
     * @param videoInfo
     * @throws EncoderException
     * @throws IOException
     */
    public void createVideoCover(String videoPath, String imgPath, MultimediaObject videoInfo) throws EncoderException, IOException {
        Assert.notNull(videoPath,() -> "videoPath can be not null");
        DefaultFFMPEGLocator locator = new DefaultFFMPEGLocator();
        String executablePath = locator.getFFMPEGExecutablePath();
        List<String> commands = new java.util.ArrayList<String>();
        File file = new File(imgPath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        commands.add(executablePath);
        commands.add("-i");
        commands.add(videoPath);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        commands.add("00:02");
        commands.add("-t");
        commands.add("0.001");
        commands.add("-s");
        MultimediaInfo info = videoInfo.getInfo();
        VideoSize size = info.getVideo().getSize();
        int height = size.getHeight();
        int width = size.getWidth();
        // 宽X高
        commands.add(width + "x" + height);
        commands.add(imgPath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            locator = null;
        }
    }

    public static void main(String[] args) {
        DefaultFFMPEGLocator locator = new DefaultFFMPEGLocator();
        String executablePath = locator.getFFMPEGExecutablePath();
        System.out.println(executablePath);
    }

}
