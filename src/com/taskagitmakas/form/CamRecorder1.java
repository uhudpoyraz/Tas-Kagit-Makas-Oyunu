package com.taskagitmakas.form;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

 import org.omg.IOP.Codec;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
public class CamRecorder1 {

	private VideoCapture videoCapture;

	public JFrame Window;
	private ImageIcon image;
	private JLabel imageLable;
	private Boolean sizeCustom = false;
	private int Height, Width;
	private int hMin, sMin, vMin, hMax, sMax, vMax;

	private static final int SAMPLE_NUM = 1;
	private Imshow im;
 

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

	private Scalar handColor;
    private Scalar minHSV;
    private Scalar maxHSV;
    private Mat frame, frame2;
    private Point palmCenter;
    private List<Point> fingers;
    private TermCriteria termCriteria;
    private List<Rect> allRoi;
    private List<Mat> allRoiHist;
    private MatOfFloat ranges;
    private MatOfInt channels;
    private Mat dstBackProject;
    private MatOfPoint palmContour;
    private MatOfPoint hullPoints;
    private MatOfInt hull;
    private Mat hierarchy;
    private Mat touchedMat;
    private MatOfInt4 convexityDefects;
    private Mat nonZero;
    private Mat nonZeroRow;
    private List<MatOfPoint> contours;
    private Mat ycc;
    private List<MatOfPoint> mContours = new ArrayList<MatOfPoint>();
    private static double mMinContourArea = 0.1;
    private int speedTime = 0;
private int speedFingers = 0;
	
	
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

	public CamRecorder1() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		videoCapture = new VideoCapture(0);
 		//videoCapture = new VideoCapture("/home/uhudpoyraz/Masaüstü/b.mp4");
		im=new Imshow("Gray");im.Window.setResizable(true);
		
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
	 //	Imgproc.cvtColor(background, background, Imgproc.COLOR_BGR2YCrCb);

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



		for (int i = 0; i < SAMPLE_NUM; i++) {
			samplePoints[i][1].x = samplePoints[i][0].x + squareLen;
			samplePoints[i][1].y = samplePoints[i][0].y + squareLen;
		}
		
		
		avgColor = new double[SAMPLE_NUM][3];
		avgBackColor = new double[SAMPLE_NUM][3];
		 

		
 	        ycc = new Mat(rows, cols, CvType.CV_8UC3);
	        handColor = new Scalar(255);
	        minHSV = new Scalar(3);
	        maxHSV = new Scalar(3);
	        frame = new Mat();
	        termCriteria = new TermCriteria(TermCriteria.COUNT | TermCriteria.EPS, 10, 1);
	        allRoi = new ArrayList<>();
	        allRoiHist = new ArrayList<>();
	        ranges = new MatOfFloat(0, 180);
	        channels = new MatOfInt(0);
	        dstBackProject = new Mat();
	        palmContour = new MatOfPoint();
	        hullPoints = new MatOfPoint();
	        hull = new MatOfInt();
	        hierarchy  = new Mat();
	        touchedMat = new Mat();
	        convexityDefects = new MatOfInt4();
	        nonZero = new Mat();
	        frame2 = new Mat();
	        nonZeroRow = new Mat();
	        contours = new ArrayList<>();
	palmCenter = new Point(-1, -1);
		
		
	 
	}
 
	private int LearningTime=0;
	 public BufferedImage startRecord() {
			Mat m = new Mat();
			Mat c = new Mat();

			this.videoCapture.read(m);
 			Core.flip(m, ycc, 1);
			 
	 
		 	 
			for (int i = 0; i < SAMPLE_NUM; i++) {

				//Core.putText(m, Integer.toString(i),new Point(samplePoints[i][0].x + squareLen / 2, samplePoints[i][0].y + squareLen / 2)),Core.FONT_HERSHEY_SIMPLEX, 0.7, new Scalar(0, 0, 255));
				//Core.rectangle(ycc, samplePoints[i][0], samplePoints[i][1], new Scalar(47, 255, 6), 1);
			}
	 
			 for (int i = 0; i < SAMPLE_NUM; i++) {
				// System.out.println("");
				for (int j = 0; j < 3; j++) {
					avgColor[i][j] = (ycc.get((int) (samplePoints[i][0].x + squareLen / 2),
							(int) (samplePoints[i][0].y + squareLen / 2)))[j];
					//System.out.print((m.get((int) (samplePoints[i][0].x + squareLen / 2),(int) (samplePoints[i][0].y + squareLen / 2)))[j]+" ");

				}
			} 
			 
			 	frame = ycc.clone();
	            Imgproc.GaussianBlur(frame, frame, new Size(9, 9), 5);

	            int rows = frame.rows();
	            int cols = frame.cols();

	            palmCenter.x = (samplePoints[0][0].x + squareLen / 2);
	            palmCenter.y = (samplePoints[0][0].y + squareLen / 2);

	            getAvgHSV(frame);

	            
			 
			 
			 
			 
			return toBufferedImage(frame);

	 }
	 
	 
	 double iThreshold=0;
	 public BufferedImage filterSkinColor() {

		Mat m = new Mat();
		Mat c = new Mat();
		Mat b = new Mat();

		this.videoCapture.read(m);
 		Core.flip(m, ycc, 1);
 		// clone frame beacuse original frame needed for display
        frame = ycc.clone();

		// remove noise and convert to binary in HSV range determined by user input
        Imgproc.GaussianBlur(frame, frame, new Size(9, 9), 5);
       
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2HSV_FULL);
         Core.inRange(frame, minHSV, maxHSV, frame);
        
     
//        Point palm = getDistanceTransformCenter(frame);

		// get all possible contours and then determine palm contour
      Mat frameGray=new Mat();
      frame.copyTo(frameGray);
      contours =  getAllContours(frame);
	       
      im.showImage(frameGray);
      Imgproc.drawContours(ycc, contours, -1, new Scalar (0, 255, 0), 3);
	      
	      
			return toBufferedImage(ycc); 
 

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

	  protected List<Integer> getDefects(List<Integer> defectIndicesOld){
	        int thresh = 800;
	        int prevDepth = 0;
	        List<Integer> defectIndices = new ArrayList<Integer>();
	        for(int i = 0; i<defectIndicesOld.size(); i+=4) {
	            int curDepth = defectIndicesOld.get(i+3);
	            if(curDepth > thresh)
	                defectIndices.addAll(defectIndicesOld.subList(i, i+4));
	        }
	        return defectIndices;
	    }

		
	 

		
		/**
		 * Method to compute and assign histogram of given Region of Interest.
		 */
	    protected void assignRoiHist(Point point, Mat frame, Rect roi, Mat roiHist){
	        int halfSide = 10;
	        roi.x = ((int) point.x - halfSide > 0)? (int) point.x - halfSide : 0;
	        roi.y = ((int) point.y - halfSide > 0)? (int) point.y - halfSide : 0;
	        roi.width = (2 * halfSide < frame.width())? 2 * halfSide : frame.width();
	        roi.height = (2 * halfSide < frame.height())? 2 * halfSide : frame.height();

	        //Log.e("roi", roi.x+" "+roi.y+" "+roi.width+" "+roi.height);

	        Mat submat = frame.submat(roi);
	        Mat mask = new Mat();
	        MatOfInt histSize = new MatOfInt(180);

	        Imgproc.cvtColor(submat, submat, Imgproc.COLOR_RGB2HSV_FULL);
	        Core.inRange(submat, minHSV, maxHSV, mask);
	        List<Mat> tempMatList = new ArrayList();
	        tempMatList.add(submat);
	        Imgproc.calcHist(tempMatList, channels, mask, roiHist, histSize, ranges);
	        Core.normalize(roiHist, roiHist, 0, 255, Core.NORM_MINMAX);

	        submat.release();
	        mask.release();
	        histSize.release();
	    }

		
		/**
		 * Method to compute and return strongest point of distance transform.
		 * For a binary image with palm in white, strongest point will be the palm center.
		 */
	    protected Point getDistanceTransformCenter(Mat frame){

	        Imgproc.distanceTransform(frame, frame, Imgproc.CV_DIST_L2, 3);
	        frame.convertTo(frame, CvType.CV_8UC1);
	        Core.normalize(frame, frame, 0, 255, Core.NORM_MINMAX);
	        Imgproc.threshold(frame, frame, 254, 255, Imgproc.THRESH_TOZERO);
	        Core.findNonZero(frame, nonZero);

	        // have to manually loop through matrix to calculate sums
	        int sumx = 0, sumy = 0;
	        for(int i=0; i<nonZero.rows(); i++) {
	            sumx += nonZero.get(i, 0)[0];
	            sumy += nonZero.get(i, 0)[1];
	        }
	        sumx /= nonZero.rows();
	        sumy /= nonZero.rows();

	        return new Point(sumx, sumy);
	    }

		
		/**
		 * Method to get number of fingers being help up in palm image
		 */
	    protected List<Point> getFingersTips(List<Point> hullPoints, int rows){
	        // group into clusters and find distance between each cluster. distance should approx be same
	        double betwFingersThresh = 80;
	        double distFromCenterThresh = 80;
	        double thresh = 80;
	        List<Point> fingerTips  = new ArrayList<>();
	        for(int i=0; i<hullPoints.size(); i++){
	            Point point = hullPoints.get(i);
	            if(rows - point.y < thresh)
	                continue;
	            if(fingerTips.size() == 0){
	                fingerTips.add(point);
	                continue;
	            }
	            Point prev = fingerTips.get(fingerTips.size() - 1);
	            double euclDist = getEuclDistance(prev, point);
				
	            if(getEuclDistance(prev, point) > thresh/2 &&
	                    getEuclDistance(palmCenter, point) > thresh)
	                fingerTips.add(point);
					
	            if(fingerTips.size() == 5)  // prevent detection of point after thumb
	                break;
	        }
	        return fingerTips;
	    }

		
		/**
		 * Method to get eucledean distance between two points.
		 */
	    protected double getEuclDistance(Point one, Point two){
	        return Math.sqrt(Math.pow((two.x - one.x), 2)
	                + Math.pow((two.y - one.y), 2));
	    }

		
		/**
		 * Method to get convex hull points.
		 */
	    protected List<Point> getConvexHullPoints(MatOfPoint contour){
	        Imgproc.convexHull(contour, hull);
	        List<Point> hullPoints = new ArrayList<>();
	        for(int j=0; j < hull.toList().size(); j++){
	            hullPoints.add(contour.toList().get(hull.toList().get(j)));
	        }
	        return hullPoints;
	    }

		
		/**
		 * Method to get contour of palm. Computed by the 
		 * knowledge that palm center has to lie inside it.
		 */
	    protected int getPalmContour(List<MatOfPoint> contours){

	        Rect roi;
	        int indexOfMaxContour = -1;
	        for (int i = 0; i < contours.size(); i++) {
	            roi = Imgproc.boundingRect(contours.get(i));
	            if(roi.contains(palmCenter))
	                return i;
	        }
	        return indexOfMaxContour;
	    }

		
		/**
		 * Method to get all possible contours in binary image frame.
		 */
	    protected List<MatOfPoint> getAllContours(Mat frame){
	        frame2 = frame.clone();
	        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
	        Imgproc.findContours(frame2, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
	        return contours;
	    }

		
		/**
		 * Method to assign average HSV value of palm
		 */
	    protected void getAvgHSV(Mat frame){
			
	        // consider square patch around touched pixel
	        int x = (int) palmCenter.x;
	        int y = (int) palmCenter.y;
	        int rows = frame.rows();
	        int cols = frame.cols();

	        Rect touchedSquare = new Rect();
	        int squareSide = 20;

	        touchedSquare.x = (x > squareSide) ? x - squareSide : 0;
	        touchedSquare.y = (y > squareSide) ? y - squareSide : 0;

	        touchedSquare.width = (x + squareSide < cols) ?
	                x + squareSide - touchedSquare.x : cols - touchedSquare.x;
	        touchedSquare.height = (y + squareSide < rows) ?
	                y + squareSide - touchedSquare.y : rows - touchedSquare.y;

			 

	        touchedMat = frame.submat(touchedSquare);

	        // convert patch to HSV and get average values
	        Imgproc.cvtColor(touchedMat, touchedMat, Imgproc.COLOR_RGB2HSV_FULL);

	        Scalar sumHSV = Core.sumElems(touchedMat);
	        int total = touchedSquare.width * touchedSquare.height;
	        double avgHSV[] = {sumHSV.val[0] / total, sumHSV.val[1] / total, sumHSV.val[2] / total};
	        assignHSV(avgHSV);
	    }

		/**
		 * Method to assign range of HSV values of palm
		 */
	    protected void assignHSV(double avgHSV[]){
	        minHSV.val[0] = (avgHSV[0] > 10) ? avgHSV[0] - 10 : 0;
	        maxHSV.val[0] = (avgHSV[0] < 245) ? avgHSV[0] + 10 : 255;

	        minHSV.val[1] = (avgHSV[1] > 130) ? avgHSV[1] - 100 : 30;
	        maxHSV.val[1] = (avgHSV[1] < 155) ? avgHSV[1] + 100 : 255;

	        minHSV.val[2] = (avgHSV[2] > 130) ? avgHSV[2] - 100 : 30;
	        maxHSV.val[2] = (avgHSV[2] < 155) ? avgHSV[2] + 100 : 255;

	     

	   
	        
	        System.out.println("HSV Ortalama: "+  avgHSV[0]+", "+avgHSV[1]+", "+avgHSV[2]);
	        System.out.println("HSV Min: "+  minHSV.val[0]+", "+minHSV.val[1]+", "+minHSV.val[2]);

	        System.out.println("HSV Max: "+  maxHSV.val[0]+", "+maxHSV.val[1]+", "+maxHSV.val[2]);

	    }

	    protected Mat downSample(Mat ycc, int n){
	        // TODO: erode then dilate

	        for (int i=0; i<n; i++)
	            Imgproc.pyrDown(ycc, ycc);
	        return ycc;
	    }

		/**
		 * Method to set scale factors for coordinate translation
		 */
 
}
