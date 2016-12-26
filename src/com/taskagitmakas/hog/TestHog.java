package com.taskagitmakas.hog;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import com.taskagitmakas.entity.Image;


public class TestHog {
 
	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
 
		for(int i=3100;i<4000;i++){
			
				System.out.println("@attribute feature"+i+" numeric");
			
		}
		
		/*Hog hog=new Hog();
		Imshow im=new Imshow("a");*/
		
	
		/*for(int i=0;i<16;i++){
	
 		 
		 hog.addFromImageFile("MAKAS/"+i+".png", 3);
		
		}*/
	}

}