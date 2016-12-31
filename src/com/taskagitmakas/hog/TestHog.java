package com.taskagitmakas.hog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
 

import com.taskagitmakas.dao.ImageDao;
import com.taskagitmakas.dao.ImageImp;
import com.taskagitmakas.entity.Image;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;




public class TestHog {

	
	public static ImageDao imageService=new ImageImp();
	public static List<Image> myTrainSet;

	
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public static void createWekaFile(){
		myTrainSet = new ArrayList<Image>();
		myTrainSet = imageService.all();	
		try{
		    PrintWriter writer = new PrintWriter("myDataSet.arff", "UTF-8");
		    writer.println("@relation handDescription");
		    writer.println("@attribute class {1, 2, 3}");
		    for(int i=0;i<myTrainSet.get(0).getRowCount();i++){
			    writer.println("@attribute feature"+i+" numeric");
 	
		    }
		    
		    writer.println("@data");
		   
		    for (Image image : myTrainSet) {
			    writer.println(image.getClassType()+","+image.getHogDescriptionVector().substring(0,image.getHogDescriptionVector().length()-1));
 
			} 
		    writer.close();
		} catch (IOException e) {
		   // do something
		}		
		
	}
 
	public static void main(String[] args) throws Exception {
		
		createWekaFile();
		BufferedReader datafile = readDataFile("myDataSet.arff");
 		Instances data = new Instances(datafile);
		data.setClassIndex(0);
  
		int testDataSize=3*20*1;
		int trainingDataSize=data.size()-testDataSize;
		
		
 		Instances trainingData = new Instances(data,0,trainingDataSize);
 		Instances testData = new Instances(data,trainingDataSize,testDataSize);
 		
		System.out.println("Trainin Data Size:"+trainingData.size());
		System.out.println("Test Data Size:"+testData.size());

 		
 		IBk ibk=new IBk();
 		ibk.buildClassifier(data);
		Evaluation evaluation=new Evaluation(data);
		evaluation.evaluateModel(ibk, testData);
		
		System.out.println(evaluation.toSummaryString("------------Result-------------",true));
		 System.out.println(evaluation.toMatrixString());
 
   
	}


}