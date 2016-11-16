package com.uhudpoyraz.hog;


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

public class TestHog {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		Size blockSize=new Size(16,16);
		Size cellSize=new Size(16,16);
		Size _blockStride=new Size(8,8);
	    Size winStride=new Size(32,32);
	    Size padding=new Size(0,0);  
		
	    
		Mat image1=new Mat();
		image1=Highgui.imread("2.jpg");
		MatOfFloat descriptors1 = new MatOfFloat();
		HOGDescriptor hog1=new HOGDescriptor(image1.size(),blockSize, _blockStride, cellSize, 9);
		
	    MatOfPoint locations1=new MatOfPoint(); ////an empty vector of locations, so perform full search        
	    hog1.compute(image1 , descriptors1, winStride, padding, locations1);
	    Mat Hogfeat1=new Mat();
	    Hogfeat1.create(descriptors1.size(),CvType.CV_32FC1);
	    for(int i=0;i< descriptors1.rows();i++)
		{
		   Hogfeat1.put(i, 0, descriptors1.get(i,0));
		
		} 
	    
	    
	    Mat image2=new Mat();
		image2=Highgui.imread("3.jpg");
		MatOfFloat descriptors2 = new MatOfFloat();
		HOGDescriptor hog2=new HOGDescriptor(image1.size(),blockSize, _blockStride, cellSize, 9);
	    MatOfPoint locations2=new MatOfPoint(); ////an empty vector of locations, so perform full search        
	    hog1.compute(image2 , descriptors2, winStride, padding, locations2);
	    Mat Hogfeat2=new Mat();
	    Hogfeat2.create(descriptors2.size(),CvType.CV_32FC1);
	    for(int i=0;i< descriptors2.rows();i++)
	 		{
	 		   Hogfeat2.put(i, 0, descriptors2.get(i,0));
	 		
	 		} 
	    
	    
	    
	    System.out.println("Descriptors cols:"+descriptors1.cols());
	    System.out.println("Descriptors rows:"+descriptors1.rows());

	   
	    
	    
	 

	    //This is for comparing the HOG features of two images without using any SVM 
	    //(It is not an efficient way but useful when you want to compare only few or two images)
	    //Simple distance
	    //Consider you have two hog feature vectors for two images Hogfeat1 and Hogfeat2 and those are same size.
	    double distance=0;
	    
	    for(int i=0;i<Hogfeat1.rows();i++)
	    {
	       distance += Math.abs((float)(Hogfeat1.get(i,0)[0] - (float)Hogfeat2.get(i,0)[0]));
	    }
	    System.out.println("Mesafe:"+distance);
	   // if(distance < Threshold)
	    
	  
	    MatOfPoint foundLocations=new MatOfPoint();
	    MatOfDouble weights=new MatOfDouble();
		System.out.println(weights.size());

		hog1.detect(image2, foundLocations, weights); 	 
		System.out.println(weights.size());

		System.out.println(weights.get(0,0));


	}

}



/*VideoCapture videoCapture=new VideoCapture(0);
		Imshow im = new Imshow("Video Preview");
		im.Window.setResizable(true);
		Mat image=new Mat();
		 while(true){
			videoCapture.read(image);
			im.showImage(image);
		 	Highgui.imwrite("3_1.jpg", image);

			} */