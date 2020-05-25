package com.ctt.constant;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description
 * @auther Administrator
 * @create 2020-03-08 下午 1:57
 */
public enum FileTypeEnum {

    word_03("doc", "el-icon-word"),
    word_07("docx", "el-icon-word"),
    excel_03("xlsx", "el-icon-excel"),
    excel_07("xls", "el-icon-excel"),
    cad("cad", "el-icon-cad"),
    zip("zip", "el-icon-zip"),
    txt("txt", "el-icon-txt"),
    other("other", "el-icon-file-manager"),
    folder("folder", "el-icon-files"),
    pdf("pdf", "el-icon-pdf"),
    img("ing", "el-icon-imgv");

    private String ext;

    private String icon;

    FileTypeEnum(String ext, String icon) {
        this.ext = ext;
        this.icon = icon;
    }

    public String getIcon(String ext) {
        FileTypeEnum[] enums = values();
        for (FileTypeEnum e : enums) {
            if (e.ext.equals(ext)) {
                return e.icon;
            }
        }
        return FileTypeEnum.other.icon;
    }

    public String getExt() {
        return this.ext;
    }

    public String getIcon() {
        return this.icon;
    }

    public static void main(String[] args) {
        JSONObject js = new JSONObject();
        String s = "123";
        js.put("s", s);
        s = "1234";
        System.out.println(js);
    }
}
