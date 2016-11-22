package com.taskagitmakas.hog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.ws.soap.AddressingFeature.Responses;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Range;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.CvSVM;
import org.opencv.ml.CvSVMParams;
import org.opencv.objdetect.HOGDescriptor;

import com.taskagitmakas.dao.ImageDao;
import com.taskagitmakas.dao.ImageImp;
import com.taskagitmakas.entity.Image;

public class Hog {
	
	
	private String directoryPath;
	private Size blockSize;
	private Size cellSize;
	private Size blockStride;
    private Size winStride;
    private Size padding;
    private int bin;
	public List<MatOfFloat> descriptorsList;
	public List<MatOfPoint> locationsList;
	public ImageDao imageService;
	
	public Hog(String directoryPath,Size blockSize,Size cellSize,Size blockStride,Size winStride,Size padding,int bin){
		
		this.directoryPath=directoryPath;	
		this.blockSize=blockSize;
		this.cellSize=cellSize;
		this.blockStride=blockStride;
		this.winStride=winStride;
		this.padding=padding;
		this.bin=bin;
		descriptorsList=new ArrayList<MatOfFloat>();
		locationsList=new ArrayList<MatOfPoint>();
		imageService=new ImageImp();
	}
	
	
	public void add(String path){
		ImageDao imageDao=new ImageImp();
		//Imshow im=new Imshow("Egitim");
		
		Mat img=new Mat();
		img=Highgui.imread(directoryPath+path);
		
	//	System.out.println("Dosya Yolu: "+directoryPath+path);
		Imgproc.resize(img, img, new Size(64,48));
		Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);
		//im.showImage(image);

		MatOfFloat descriptor = new MatOfFloat();
		MatOfPoint location=new MatOfPoint();
		HOGDescriptor hogDescriptor=new HOGDescriptor( new Size(64,48),blockSize, blockStride, cellSize, bin);
		hogDescriptor.compute(img , descriptor, winStride, padding, location);
		
			String myDescription = "";

			for (int j = 0; j < descriptor.rows(); j++) {

				myDescription = myDescription + descriptor.get(j, 0)[0] + ",";

			}

			Image image=new Image();
			image.setRowCount(descriptor.rows());
			image.setColCount(descriptor.cols());
			image.setHogDescriptionVector(myDescription);
			image.setClassType(3);
			imageDao.insert(image);
	}
	
	
public void add1(String path){
		
		//Imshow im=new Imshow("Egitim");
		
		Mat image=new Mat();
		image=Highgui.imread(directoryPath+path);
		
	//	System.out.println("Dosya Yolu: "+directoryPath+path);
		Imgproc.resize(image, image, new Size(64,48));
		Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
		//im.showImage(image);

		MatOfFloat descriptor = new MatOfFloat();
		MatOfPoint location=new MatOfPoint();
		HOGDescriptor hogDescriptor=new HOGDescriptor( new Size(64,48),blockSize, blockStride, cellSize, bin);
		hogDescriptor.compute(image , descriptor, winStride, padding, location);
		descriptorsList.add(descriptor);
		locationsList.add(location);
	}
	
	
	
	
	public void createSVM() throws DocumentException{
		 List<Image> imageList=new ArrayList<Image>();
		 imageList=imageService.all();
		 Mat hogDescription=new Mat(new Size(imageList.size(),imageList.get(0).getRowCount()),CvType.CV_32FC1);
		 Mat label=new Mat(new Size(5940,1),CvType.CV_32FC1,new Scalar(-1.0));
		 
		 int j=0;
		 for(Image image:imageList){
			 
			String data[]=image.getHogDescriptionVector().split(",");
			  for(int i=0;i<image.getRowCount();i++){
				 
				  hogDescription.put(i, j, Double.parseDouble(data[i]));

				//  System.out.println(i+" "+j);
			 } 
			
			  label.put(0, j,image.getClassType());
			  j++;

		 }
 		
		 	System.out.println(hogDescription.size());
		 	System.out.println(label.size());

		 	CvSVM svm=new CvSVM();
		 	CvSVMParams params=new CvSVMParams();	 
		 	params.set_svm_type(svm.C_SVC);
			params.set_kernel_type(svm.LINEAR); 
		 	TermCriteria termCrit=new TermCriteria(TermCriteria.COUNT, 10000, 1e-6);
			params.set_term_crit(termCrit); 
		 	svm.train(hogDescription, label, new Mat(), new Mat(), params);
		 	svm.save("trainedSVM.xml");
	}
	
	

}
