package com.taskagitmakas.form;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class CamRecorder {

	private VideoCapture videoCapture;

	public JFrame Window;
	private ImageIcon image;
	private JLabel imageLable;
	private Boolean sizeCustom = false;
	private int Height, Width;
	private int hMin, sMin, vMin, hMax, sMax, vMax;

	List<Point> leftPoint = new ArrayList<Point>();
	List<Point> rightPoint = new ArrayList<Point>();

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
		videoCapture = new VideoCapture(0);
		this.hMin = 0;
		this.hMax = 25;
		this.sMin = 48;
		this.sMax = 255;
		this.vMin = 80;
		this.vMax = 255;

		/*
		 * Ekranda çizilecek olan karelerin köşeleri left-right çifti bir tane
		 * kare etmektedir.
		 */
		leftPoint.add(new Point(285, 100));
		rightPoint.add(new Point(295, 110));
		leftPoint.add(new Point(385, 145));
		rightPoint.add(new Point(395, 155));
		leftPoint.add(new Point(320, 80));
		rightPoint.add(new Point(330, 90));
		leftPoint.add(new Point(295, 255));
		rightPoint.add(new Point(305, 265));
		leftPoint.add(new Point(335, 305));
		rightPoint.add(new Point(345, 315));
		leftPoint.add(new Point(395, 295));
		rightPoint.add(new Point(405, 305));

	}

	public BufferedImage startRecord() {

		Mat m = new Mat();
		Mat c = new Mat();

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		this.videoCapture.read(m);
	 	Imgproc.blur(m, m, new Size(3, 3));
		 Imgproc.cvtColor(m,m, Imgproc.COLOR_RGB2HSV);
		Core.flip(m, m, 1); //resmi ters ceviriyor yani mirror efektini ortadan kaldırıyor
		skinColors.clear(); //renkleri tuttugumuz arraylist'i temizliyor
		
		
		for (int i = 0; i < leftPoint.size(); i++) {
			//ekrana daha önce bellirledigimiz noktalara dikdörtgenler ciziyor
			Core.rectangle(m, leftPoint.get(i), rightPoint.get(i), new Scalar(47, 255, 6));
			//Dikdörtgenlerin tam ortasındaki renk degerini alıp skinn
			data = m.get((int) leftPoint.get(i).x + 5, (int) leftPoint.get(i).y + 5);
			skinColors.add(data);
		}

		return toBufferedImage(m);

	}

	public BufferedImage filterSkinColor() {
		Mat m = new Mat();
		this.videoCapture.read(m);
		Imgproc.blur(m, m, new Size(3, 3));
		Core.inRange(m, new Scalar(skinColors.get(0)[0], skinColors.get(0)[1], skinColors.get(0)[2]),
				new Scalar(skinColors.get(0)[0]+20 , 255, 255), m);
		System.out.println("in filterSkinColor");
		System.out.println(skinColors.get(0)[0] + " " + skinColors.get(0)[1] + " " + skinColors.get(0)[2]);
		System.out.println(skinColors.get(1)[0] + " " + skinColors.get(2)[1] + " " + skinColors.get(3)[2]);
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

}
