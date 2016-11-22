package com.taskagitmakas.hog;

import java.io.IOException;

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

public class TestHog {

	public static void main(String[] args) throws IOException, DocumentException {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Hog hog = new Hog("Set1/Kagit0/", new Size(8, 8), new Size(4, 4), new Size(4, 4), new Size(0, 0), new Size(0, 0),9);
		ImageDao imageDao=new ImageImp();
		
		
 
 /*
		
 
		for (int i = 0; i < 19; i++) {
			for (int j = 10; j < 20; j++) {
				String path = i + "/" + j + ".jpg";
				hog.add1	(path);
				// System.out.println(path);
			}

		}

		System.out.println(hog.descriptorsList.size());
 		for (int i = 0; i < hog.descriptorsList.size(); i++) {
			System.out.println(i + " İşleniyor...");

			String myDescription = "";

			for (int j = 0; j < hog.descriptorsList.get(0).rows(); j++) {

				myDescription = myDescription + hog.descriptorsList.get(i).get(j, 0)[0] + ",";

			}

			Image image=new Image();
			image.setRowCount(hog.descriptorsList.get(0).rows());
			image.setColCount(hog.descriptorsList.get(0).cols());
			image.setHogDescriptionVector(myDescription);
			image.setClassType(2);
			
			imageDao.insert(image);

		}
	 */	
		hog.createSVM();
		 
		System.out.println("Bitti");
 

		
	}

}