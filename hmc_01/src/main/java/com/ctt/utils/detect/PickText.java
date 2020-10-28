package com.ctt.utils.detect;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HHF
 * @Description
 * @create 2020-09-22 上午 9:55
 */
public class PickText {


   public static Mat preprocess(org.opencv.core.Mat gray)
    {
//        HighGui.imshow("gray" , gray);
        //1.Sobel算子，x方向求梯度
        Mat sobel = new Mat();
        Imgproc.Sobel(gray, sobel, CvType.CV_8U, 0, 1);
//        HighGui.imshow("sobel" , sobel);
//        HighGui.waitKey();
        //2.二值化
        Mat binary = new Mat();
        Imgproc.threshold(sobel, binary, 0, 255, Imgproc.THRESH_OTSU + Imgproc.THRESH_BINARY);
//        HighGui.imshow("binary" , binary);
//        HighGui.waitKey();
        //3.膨胀和腐蚀操作核设定
        Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(30, 9));
        //控制高度设置可以控制上下行的膨胀程度，例如3比4的区分能力更强,但也会造成漏检
        Mat element2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 4));

        //4.膨胀一次，让轮廓突出
        Mat dilate1 = new Mat();
        Imgproc.dilate(binary, dilate1, element2);

        //5.腐蚀一次，去掉细节，表格线等。这里去掉的是竖直的线
        Mat erode1 = new Mat();
        Imgproc.erode(dilate1, erode1, element1);

        //6.再次膨胀，让轮廓明显一些
        Mat dilate2 = new Mat();
        Imgproc.dilate(erode1, dilate2, element2);
//        HighGui.imshow("element2" , element2);
//        HighGui.waitKey();
        //7.存储中间图片
//        HighGui.imshow("binary.jpg", binary);
//        HighGui.imshow("dilate1.jpg", dilate1);
//        HighGui.imshow("erode1.jpg", erode1);
//        HighGui.imshow("dilate2.jpg", dilate2);
        return dilate2;
    }


    public static List<Rect> findTextRegion(Mat img)
    {
        List<Rect> rects = new ArrayList<>();
        //1.查找轮廓
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(img, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE, new Point(0, 0));
        //2.筛选那些面积小的
        Rect rect = null;
        for (int i = 0; i < contours.size(); i++){
            double area = Imgproc.contourArea(contours.get(i));
            if (area < 100) {
                continue;
            }
            rect = null;
            Point left = new Point(rect.x , rect.y);
            Point right = new Point(rect.x + rect.width, rect.y + rect.height);
            MatOfPoint2f matOfPoint2f = new MatOfPoint2f(left, right);
            double epsilon = 0.001 * Imgproc.arcLength(matOfPoint2f, true);
            MatOfPoint2f approx = new MatOfPoint2f(left,right);
            Imgproc.approxPolyDP(matOfPoint2f, approx, epsilon, true);

            MatOfPoint2f min = new MatOfPoint2f(left,right);
            RotatedRect rect1 = Imgproc.minAreaRect(min);
            int m_width = rect1.boundingRect().width;
            int m_height = rect1.boundingRect().height;

            //筛选那些太细的矩形，留下扁的
            if (m_height > m_width * 1.2) {
                continue;
            }
//            rect = Imgproc.boundingRapproxPolyDPect(contours.get(i));
//            Point left = new Point(rect.x , rect.y);
//            Point right = new Point(rect.x + rect.width, rect.y + rect.height);
//            Imgproc.rectangle(img, left, right, new Scalar(255, 0, 255));
//            Mat mat = new Mat(img, rect);
//            //计算当前轮廓的面积
//            double area = Imgproc.contourArea(mat);
//            System.out.println("area : " + area);
//            //面积小于1000的全部筛选掉
//            if (area < 100) {
//                continue;
//            }

//            //轮廓近似，作用较小，approxPolyDP函数有待研究
//            MatOfPoint2f matOfPoint2f = new MatOfPoint2f(mat);
//            double epsilon = 0.001 * Imgproc.arcLength(matOfPoint2f, true);
//            MatOfPoint2f approx = new MatOfPoint2f(mat);
//            Imgproc.approxPolyDP(matOfPoint2f, approx, epsilon, true);
//
//            //找到最小矩形，该矩形可能有方向
//            MatOfPoint2f min = new MatOfPoint2f(mat);
//            RotatedRect rect1 = Imgproc.minAreaRect(min);
//
//            //计算高和宽
//            int m_width = rect1.boundingRect().width;
//            int m_height = rect1.boundingRect().height;
//
//            //筛选那些太细的矩形，留下扁的
//            if (m_height > m_width * 1.2) {
//                continue;
//            }
            //符合条件的rect添加到rects集合中
            rects.add(rect);
        }
        HighGui.imshow("img",img);
        HighGui.waitKey();
        return rects;
    }

    public static void detect(Mat img)
    {
        //1.转化成灰度图
        Mat gray = new Mat();
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);

        //2.形态学变换的预处理，得到可以查找矩形的轮廓
        Mat dilation = preprocess(gray);
//        HighGui.imshow("element2" , element2);
//        HighGui.waitKey();
        //3.查找和筛选文字区域
        List<Rect> rects = findTextRegion(dilation);

        //4.用绿线画出这些找到的轮廓
        for(Rect rect : rects) {
            Point left = new Point(rect.x , rect.y);
            Point right = new Point(rect.x + rect.width, rect.y + rect.height);
            Imgproc.rectangle(img, left, right, new Scalar(255, 0, 255));
        }
        //5.显示带轮廓的图像
        HighGui.imshow("img", img);
        HighGui.waitKey(0);
    }

    public static void main(String[] args) {
        System.load(System.getProperty("java.library.path") + File.separator + "opencv_java341.dll");
        detect(Imgcodecs.imread("E:\\learn\\git\\repository\\server\\hmc\\hmc_01\\src\\main\\resources\\test\\2.png"));
    }

}
