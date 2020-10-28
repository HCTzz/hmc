package com.ctt.utils.detect;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class OCRUtil {
    /**
     * 识别图片信息
     * @param img
     * @return
     */
    public static String getImageMessage(BufferedImage img,String language,boolean hasLanguage){

        String result="end";
        try{
            ITesseract instance = new Tesseract();
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
            if(hasLanguage){
            	 instance.setLanguage(language);
            }
            instance.setDatapath(tessDataFolder.getAbsolutePath());
            instance.setTessVariable("digits", "0123456789X");
            instance.setTessVariable("user_defined_dpi", "300");
            instance.setTessVariable("fonts_dir", tessDataFolder.getAbsolutePath()+File.separator+"fonts");
            result = instance.doOCR(img);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getImageMessage(ImageIO.read(ResourceUtils.getFile("classpath:test/6.jpg")), "chi_sim", true));
    }

}