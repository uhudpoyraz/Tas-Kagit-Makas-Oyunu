package com.taskagitmakas.hog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

import com.taskagitmakas.dao.ImageDao;
import com.taskagitmakas.dao.ImageImp;
import com.taskagitmakas.entity.Image;
import com.taskagitmakas.form.Imshow;
import com.taskagitmakas.hog.KNN.ImageSamples;
import com.taskagitmakas.hog.KNN.DistanceComparator;
import com.taskagitmakas.hog.KNN.Result;

public class TestHog {
 
	public static void main(String[] args) {

		 

		KNN kn=new KNN();
		Image image=kn.imageService.get(1137);
		kn.run(image);
	
	}

}