package com.taskagitmakas.form;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
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
	private int hMin,sMin,vMin,hMax,sMax,vMax;

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
		this.hMin=0;
		this.hMax=25;
		this.sMin=48;
		this.sMax=255;
		this.vMin=80;
		this.vMax=255;
	

	}
	public BufferedImage startRecord() {

		Mat m = new Mat();
	
		 
	 
			this.videoCapture.read(m);
			Imgproc.blur(m, m, new Size(3, 3));
			Imgproc.cvtColor(m, m, Imgproc.COLOR_RGB2HSV);
			Core.inRange(m, new Scalar(hMin, sMin, vMin), new Scalar(hMax, sMax, vMax), m);
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

}
