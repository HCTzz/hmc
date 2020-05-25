package com.ctt.web.enums;

/**
 * @Description
 * @auther Administrator
 * @create 2020-03-26 下午 3:48
 */
public enum FileType {

    JPG("图片", "img"),
    jpg("图片", "img"),
    JPEG("图片", "img"),
    jpeg("图片", "img"),
    PNG("图片", "img"),
    png("图片", "img"),
    mp4("视频", "video"),
    MP4("视频", "video"),
    rmvb("视频", "video"),
    RMVB("视频", "video"),
    flv("视频", "video"),
    FLV("视频", "video");

    public String typeName;

    public String fileAttach;

    FileType(String typeName, String fileAttach) {
        this.typeName = typeName;
        this.fileAttach = fileAttach;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFileAttach() {
        return fileAttach;
    }

    public void setFileAttach(String fileAttach) {
        this.fileAttach = fileAttach;
    }

    public static void main(String[] args) {
        FileType fileType = FileType.valueOf("mp4");
        System.out.println(fileType.getFileAttach());
    }
}
