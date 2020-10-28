package com.ctt.utils.detect;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;


/**
 * 核心
 * @author eguid
 * 
 */
public class CoreFunc {
    

    public enum Direction {
        UNKNOWN, VERTICAL, HORIZONTAL
    }

	/**
	 * Get the Sobel Mat of input image!
	 *
	 * @param image
	 *            The input image.
	 * @return The Sobel Mat image of input image.
	 */
	public static Mat Sobel(Mat image) {
		if (image.empty()) {
			System.out.println("Please check the input image!");
			return image;
		}
		Mat gray = image.clone();
		if (3 == gray.channels()) {
			Imgproc.cvtColor(gray, gray, Imgproc.COLOR_BGR2GRAY);
		}
		Mat grad = new Mat();
		Mat grad_x = new Mat();
		Mat grad_y = new Mat();
		Mat abs_grad_x = new Mat();
		Mat abs_grad_y = new Mat();
		final int scharr_scale = 1;
		final int scharr_delta = 0;
		final int scharr_ddpeth = 3;
		Imgproc.Sobel(gray, grad_x, scharr_ddpeth, 1, 0, 3, scharr_scale, scharr_delta, Core.BORDER_DEFAULT);
		Imgproc.Sobel(gray, grad_y, scharr_ddpeth, 0, 1, 3, scharr_scale, scharr_delta, Core.BORDER_DEFAULT);
		Core.convertScaleAbs(grad_x, abs_grad_x);
		Core.convertScaleAbs(grad_y, abs_grad_y);
		Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 0, grad);
		return grad;
	}
    


}
