package com.taskagitmakas.form;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.omg.IOP.Codec;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG2;

public class CamRecorder {

	private VideoCapture videoCapture;

	public JFrame Window;
	private ImageIcon image;
	private JLabel imageLable;
	private Boolean sizeCustom = false;
	private int Height, Width;
	private int hMin, sMin, vMin, hMax, sMax, vMax;

	private static final int SAMPLE_NUM = 7;
	
 

	private Point[][] samplePoints = null;
	private double[][] avgColor = null;
	private double[][] avgBackColor = null;
	private Mat background;
	private double[] channelsPixel = new double[4];
	private ArrayList<ArrayList<Double>> averChans = new ArrayList<ArrayList<Double>>();
	private BackgroundSubtractorMOG2 backgroundSubtractorMOG;
	private double[][] cLower = new double[SAMPLE_NUM][3];
	private double[][] cUpper = new double[SAMPLE_NUM][3];
	private double[][] cBackLower = new double[SAMPLE_NUM][3];
	private double[][] cBackUpper = new double[SAMPLE_NUM][3];

	private Scalar lowerBound = new Scalar(0, 0, 0);
	private Scalar upperBound = new Scalar(0, 0, 0);
	private int squareLen = 20;

	double data[] = new double[3];
	List<double[]> skinColors = new ArrayList<double[]>();

	public VideoCapture getVideoCapture() {
		return videoCapture;
	}

	public void setVideoCapture(VideoCapture videoCapture) {
		this.videoCapture = videoCapture;
	}

	public int gethMin() {
		return hMin;
	}

	public void sethMin(int hMin) {
		this.hMin = hMin;
	}

	public int getsMin() {
		return sMin;
	}

	public void setsMin(int sMin) {
		this.sMin = sMin;
	}

	public int getvMin() {
		return vMin;
	}

	public void setvMin(int vMin) {
		this.vMin = vMin;
	}

	public int gethMax() {
		return hMax;
	}

	public void sethMax(int hMax) {
		this.hMax = hMax;
	}

	public int getsMax() {
		return sMax;
	}

	public void setsMax(int sMax) {
		this.sMax = sMax;
	}

	public int getvMax() {
		return vMax;
	}

	public void setvMax(int vMax) {
		this.vMax = vMax;
	}

	public CamRecorder() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		videoCapture = new VideoCapture(0);
		videoCapture.set(10, 100);
		//videoCapture = new VideoCapture("/home/uhudpoyraz/Masaüstü/b.mp4");
		
		
		this.hMin = 0;
		this.hMax = 25;
		this.sMin = 48;
		this.sMax = 255;
		this.vMin = 80;
		this.vMax = 255;

		background = new Mat();

		videoCapture.read(background);
		Core.flip(background,background, 1);
		Imgproc.GaussianBlur(background, background, new Size(5, 5), 5, 5);
	 	Imgproc.cvtColor(background, background, Imgproc.COLOR_BGR2YCrCb);

		int cols, rows;

		cols = background.cols();
		rows = background.rows();

		System.out.println(cols);
		samplePoints = new Point[SAMPLE_NUM][2];
		samplePoints = new Point[SAMPLE_NUM][2];
		for (int i = 0; i < SAMPLE_NUM; i++) {
			for (int j = 0; j < 2; j++) {
				samplePoints[i][j] = new Point();
			}
		}
		samplePoints[0][0].x = cols / 2.5;
		samplePoints[0][0].y = rows / 2.5;

		samplePoints[1][0].x = cols * 4 / 10;
		samplePoints[1][0].y = rows * 5 / 10;

		samplePoints[2][0].x = cols * 7 / 14;
		samplePoints[2][0].y = rows * 5 / 14;

		samplePoints[3][0].x = cols / 2;
		samplePoints[3][0].y = rows * 7 / 14;

		samplePoints[4][0].x = cols / 2.7;
		samplePoints[4][0].y = rows * 7 / 12;

		samplePoints[5][0].x = cols * 4 / 9;
		samplePoints[5][0].y = rows * 3 / 5;

		samplePoints[6][0].x = cols * 5 / 10;
		samplePoints[6][0].y = rows * 3 / 5;

		for (int i = 0; i < SAMPLE_NUM; i++) {
			samplePoints[i][1].x = samplePoints[i][0].x + squareLen;
			samplePoints[i][1].y = samplePoints[i][0].y + squareLen;
		}
		
		
		avgColor = new double[SAMPLE_NUM][3];
		avgBackColor = new double[SAMPLE_NUM][3];
		backgroundSubtractorMOG=new BackgroundSubtractorMOG2();
	   
	 
	}
 
	
	 public BufferedImage startRecord() {

		Mat m = new Mat();
		Mat c = new Mat();

		this.videoCapture.read(m);
		Imgproc.cvtColor(m, m, Imgproc.COLOR_RGB2HSV);
		Core.flip(m, m, 1);
		Imgproc.GaussianBlur(m, m, new Size(5, 5), 5, 5);
	 	
 
	 	 
		for (int i = 0; i < SAMPLE_NUM; i++) {

			//Core.putText(m, Integer.toString(i),new Point(samplePoints[i][0].x + squareLen / 2, samplePoints[i][0].y + squareLen / 2)),Core.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 0, 255));
			Core.rectangle(m, samplePoints[i][0], samplePoints[i][1], new Scalar(47, 255, 6), 1);
		}
 
		 for (int i = 0; i < SAMPLE_NUM; i++) {
			// System.out.println("");
			for (int j = 0; j < 3; j++) {
				avgColor[i][j] = (m.get((int) (samplePoints[i][0].x + squareLen / 2),
						(int) (samplePoints[i][0].y + squareLen / 2)))[j];
				//System.out.print((m.get((int) (samplePoints[i][0].x + squareLen / 2),(int) (samplePoints[i][0].y + squareLen / 2)))[j]+" ");

			}
		} 
		
  	 
		return toBufferedImage(m);

	} 
 
	public BufferedImage filterSkinColor() {
		Mat m = new Mat();
		Mat c = new Mat();

		this.videoCapture.read(m);
	 	Imgproc.cvtColor(m, m, Imgproc.COLOR_RGB2HSV); 
		Core.flip(m, m, 1);
		 Imgproc.GaussianBlur(m, m, new Size(5, 5), 5, 5);
	
		
		double minColor[] = findMaxColor(avgColor, 1);
		double maxColor[] = findMaxColor(avgColor, 0);

		
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9));
		Imgproc.dilate(m, m, element);
		Imgproc.erode(m, m, element);
		
		
		Core.inRange(m, new Scalar(minColor[0]-5, minColor[1]-5, minColor[2]-5),new Scalar((maxColor[0]), maxColor[1], maxColor[2]+5), c);
		
		 List<MatOfPoint> contours=new ArrayList<MatOfPoint>();
			Mat hierarchy=new Mat();
			Imgproc.findContours(c, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
			
			for(int i=0;i<contours.size();i++){
				
				double area = Imgproc.contourArea(contours.get(i));
				
					if(area>1000){
						
						 Imgproc.drawContours(m, contours, i,new Scalar (0, 255, 0), 3);
					}
				
				
				
			}
		
		
		System.out.println("in filterSkinColor");
		System.out.println(minColor[0] + " " + minColor[1] + " " + minColor[2]);

		System.out.println((maxColor[0]) + " " + (maxColor[1]) + " " + (maxColor[2]));

		 
		return toBufferedImage(m);

	}

	public BufferedImage toBufferedImage(Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}

		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

		System.arraycopy(b, 0, targetPixels, 0, b.length);

		return image;

	}

	public void closeCam() {

		this.videoCapture.release();
	}

	public boolean R1(int R, int G, int B) {
		boolean e1 = (R > 95) && (G > 40) && (B > 20)
				&& ((Math.max(R, Math.max(G, B)) - Math.min(R, Math.min(G, B))) > 15) && (Math.abs(R - G) > 15)
				&& (R > G) && (R > B);
		boolean e2 = (R > 220) && (G > 210) && (B > 170) && (Math.abs(R - G) <= 15) && (R > B) && (G > B);
		return (e1 || e2);
	}

	public boolean R2(float Y, float Cr, float Cb) {
		boolean e3 = Cr <= 1.5862 * Cb + 20;
		boolean e4 = Cr >= 0.3448 * Cb + 76.2069;
		boolean e5 = Cr >= -4.5652 * Cb + 234.5652;
		boolean e6 = Cr <= -1.15 * Cb + 301.75;
		boolean e7 = Cr <= -2.2857 * Cb + 432.85;
		return e3 && e4 && e5 && e6 && e7;
	}

	public boolean R3(float H, float S, float V) {
		return (H < 25) || (H > 230);
	}

	/*
	 * 0 maxColorValue otherwise min colorValue
	 * 
	 */
	public double[] findMaxColor(double[][] avgColor, int type) {

		double value[] = { 0.0, 0.0, 0.0 };
		if (type != 0) {

			value[0] = 999.0;
			value[1] = 999.0;
			value[2] = 999.0;
		}

		for (int i = 0; i < SAMPLE_NUM; i++) {

			for (int j = 0; j < 3; j++) {

				if (type == 0) {

					if (avgColor[i][j] > value[j]) {

						value[j] = avgColor[i][j];

					}
				} else {

					if (avgColor[i][j] < value[j]) {

						value[j] = avgColor[i][j];

					}

				}

			}

		}

		return value;
	}

}
