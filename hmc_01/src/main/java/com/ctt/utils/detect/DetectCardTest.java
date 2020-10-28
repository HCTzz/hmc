package com.ctt.utils.detect;

import com.google.common.base.CharMatcher;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author HHF
 * @Description
 * @create 2020-09-21 下午 3:57
 */
public class DetectCardTest {

    public static void main(String[] args) throws Exception {
        System.load(System.getProperty("java.library.path") + File.separator + "opencv_java341.dll");
        String path = "E:\\learn\\git\\repository\\server\\hmc\\hmc_01\\src\\main\\resources\\test\\2.png";
        File temp = new File("E:\\learn\\git\\repository\\server\\hmc\\hmc_01\\src\\main\\resources\\test\\5_temp.png");
        Thumbnails.of(path).size(350,230).toFile(temp);
        System.out.println(idCard(temp.getAbsolutePath()));
        HighGui.waitKey();
    }

    /**
     * 身份证号码识别
     */
    public static String idCard(String imagePath) throws Exception {
        // 原图
        Mat mat = Imgcodecs.imread(imagePath);
        return toCard(mat);
    }


    public static String toCard(Mat mat) throws IOException {
        Mat card = detectTextArea(mat);
        if(card == null){
            return "";
        }
        //5转为bufferImge
        BufferedImage nameBuffer = OpencvUtil.Mat2BufImg(card, ".png");
        String nameStr = OCRUtil.getImageMessage(nameBuffer, "num", true);
        String code = "";
        if (StringUtils.isNotBlank(nameStr)) {
            nameStr = nameStr.replace("\n", "");
            String codeX = CharMatcher.DIGIT.removeFrom(nameStr);
            code = CharMatcher.DIGIT.retainFrom(nameStr)
                    + (StringUtils.isNotBlank(codeX) ? ("X".equalsIgnoreCase(codeX.substring(0, 1)) ? "X" : "") : "");
        }
        System.out.println("识别后："+nameStr + " 处理后："+code);
        return code;
    }

    private static Mat detectTextArea(Mat src) {

        Mat srcMat = src.clone();
        Mat grayMat = new Mat();
        //1、图片处理
        //图片转灰度图
        Imgproc.cvtColor(srcMat, grayMat, Imgproc.COLOR_RGB2GRAY);
        //为了进行图片选择
        Mat reSrc = OpencvUtil.binary(grayMat);
        Imgproc.medianBlur(grayMat, reSrc, 1);

        // 高斯模糊 的原理(周边像素的平均值+正态分布的权重)
        Imgproc.GaussianBlur(grayMat, grayMat, new Size(7, 7), 0, 0, Core.BORDER_DEFAULT);
        // 因为边缘部分的像素值是与旁边像素明显有区别的，所以对图片局部求极值，就可以得到整幅图片的边缘信息了
        grayMat = CoreFunc.Sobel(grayMat);
        Imgproc.threshold(grayMat, grayMat, 0, 255, Imgproc.THRESH_OTSU + Imgproc.THRESH_BINARY);
        Imgproc.medianBlur(grayMat, grayMat, 13);
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 3));
        Imgproc.morphologyEx(grayMat, grayMat, Imgproc.MORPH_CLOSE, element);
        //轮廓提取
        List<MatOfPoint> contoursList = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(grayMat, contoursList, hierarchy, Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE);
        Rect rect = null;
        //CvBox2D box = null;
        for(int i = 0,total = contoursList.size(); i<total; i++){
            rect = Imgproc.boundingRect(contoursList.get(i));
            Point left = new Point(rect.x , rect.y);
            Point right = new Point(rect.x + rect.width, rect.y + rect.height);
            Imgproc.rectangle(src, left, right, new Scalar(255, 0, 255));
            HighGui.imshow("1",src);
        }
        return null;
    }

    private static int tryValue(int maxL,int oldL,int addL){
        int endV = 0;
        do{
            endV = addL;
            addL--;
        }while(maxL < oldL + addL);

        return endV;
    }


    private static int tryXy(int x,int down){
        int endV = x - down;
        while(endV < 0){
            endV++;
        }
        return endV;
    }

    public static String homePath(){
        String userDir = System.getProperty("user.dir");
        String separator = File.separator;
        userDir = userDir + separator + "src" + separator + "main" + separator + "opencv" + separator;
        return userDir;
    }

}
