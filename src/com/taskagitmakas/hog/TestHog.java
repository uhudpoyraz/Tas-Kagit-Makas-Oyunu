package com.taskagitmakas.hog;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.taskagitmakas.entity.Image;


public class TestHog {
 
	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


		KNN kn=new KNN();
		Hog hog=new Hog();
		Mat image =new Mat();
		image=Highgui.imread("image2.jpg");
		double[] a=hog.getDescriptionFromMat(image);
		
		System.out.println(a.length);
	
	}

}