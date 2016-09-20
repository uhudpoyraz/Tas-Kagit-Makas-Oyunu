package com.taskagitmakas.form;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JMenuBar;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;



public class MainForm {

	public JFrame frame;
	private  static ImageIcon image;
	private  static JLabel 	imageLabel=new JLabel("image");
 	private Boolean SizeCustom;
	private int Height, Width;
	private static BufferedImage imageFromCam;
	static CamRecorder vcam;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		vcam=new CamRecorder();

		  
		while(true){
			imageFromCam=vcam.startRecord();
			if(imageFromCam!=null){
	    	image.setImage(imageFromCam);
			imageLabel.setIcon(image);
			imageLabel.updateUI();
			}
	    }
			 
		
	}

	/**
	 * Create the application.
	 */
	public MainForm() {


		initialize();			  	 
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
	
		frame.setBounds(100, 100, 617, 421);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 420, 374);
		panel.setToolTipText("");
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setLayout(null);
		frame.getContentPane().add(panel);
	
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(419, 0, 196, 374);
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{78, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblH = new JLabel("Hmin");
		GridBagConstraints gbc_lblH = new GridBagConstraints();
		gbc_lblH.insets = new Insets(0, 0, 5, 5);
		gbc_lblH.gridx = 0;
		gbc_lblH.gridy = 1;
		panel_1.add(lblH, gbc_lblH);
		
		JSpinner hMinSpinner = new JSpinner();
		hMinSpinner.addChangeListener(new ChangeListener() {

	      
			@Override
			public void stateChanged(ChangeEvent e) {
				vcam.sethMin(Integer.parseInt(hMinSpinner.getValue().toString()));
		         				
			}
	    });
		hMinSpinner.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		GridBagConstraints gbc_hMinSpinner = new GridBagConstraints();
		gbc_hMinSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_hMinSpinner.gridx = 1;
		gbc_hMinSpinner.gridy = 1;
		panel_1.add(hMinSpinner, gbc_hMinSpinner);
		
		JLabel lblS = new JLabel("Hmax");
		GridBagConstraints gbc_lblS = new GridBagConstraints();
		gbc_lblS.insets = new Insets(0, 0, 5, 5);
		gbc_lblS.gridx = 0;
		gbc_lblS.gridy = 2;
		panel_1.add(lblS, gbc_lblS);
		
		JSpinner hMaxSpinner = new JSpinner();
		hMaxSpinner.addChangeListener(new ChangeListener() {

		      
			@Override
			public void stateChanged(ChangeEvent e) {
				vcam.sethMax(Integer.parseInt(hMaxSpinner.getValue().toString()));
		         				
			}
	    });
		hMaxSpinner.setModel(new SpinnerNumberModel(48, 0, 255, 1));
		GridBagConstraints gbc_hMaxSpinner = new GridBagConstraints();
		gbc_hMaxSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_hMaxSpinner.gridx = 1;
		gbc_hMaxSpinner.gridy = 2;
		panel_1.add(hMaxSpinner, gbc_hMaxSpinner);
		
		JLabel lblV = new JLabel("Smin");
		GridBagConstraints gbc_lblV = new GridBagConstraints();
		gbc_lblV.insets = new Insets(0, 0, 5, 5);
		gbc_lblV.gridx = 0;
		gbc_lblV.gridy = 3;
		panel_1.add(lblV, gbc_lblV);
		
		JSpinner sMinSpinner = new JSpinner();
		sMinSpinner.addChangeListener(new ChangeListener() {

		      
			@Override
			public void stateChanged(ChangeEvent e) {
				vcam.setsMin(Integer.parseInt(sMinSpinner.getValue().toString()));
		         				
			}
	    });
		sMinSpinner.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		GridBagConstraints gbc_sMinSpinner = new GridBagConstraints();
		gbc_sMinSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_sMinSpinner.gridx = 1;
		gbc_sMinSpinner.gridy = 3;
		panel_1.add(sMinSpinner, gbc_sMinSpinner);
		
		JLabel lblSmax = new JLabel("Smax");
		GridBagConstraints gbc_lblSmax = new GridBagConstraints();
		gbc_lblSmax.insets = new Insets(0, 0, 5, 5);
		gbc_lblSmax.gridx = 0;
		gbc_lblSmax.gridy = 4;
		panel_1.add(lblSmax, gbc_lblSmax);
		
		JSpinner sMaxSpinner = new JSpinner();
		sMaxSpinner.addChangeListener(new ChangeListener() {

		      
			@Override
			public void stateChanged(ChangeEvent e) {
				vcam.setsMax(Integer.parseInt(sMaxSpinner.getValue().toString()));
		         				
			}
	    });
		sMaxSpinner.setModel(new SpinnerNumberModel(255, 0, 255, 1));
		GridBagConstraints gbc_sMaxSpinner = new GridBagConstraints();
		gbc_sMaxSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_sMaxSpinner.gridx = 1;
		gbc_sMaxSpinner.gridy = 4;
		panel_1.add(sMaxSpinner, gbc_sMaxSpinner);
		
		JLabel lblVmin = new JLabel("Vmin");
		GridBagConstraints gbc_lblVmin = new GridBagConstraints();
		gbc_lblVmin.insets = new Insets(0, 0, 5, 5);
		gbc_lblVmin.gridx = 0;
		gbc_lblVmin.gridy = 5;
		panel_1.add(lblVmin, gbc_lblVmin);
		
		JSpinner vMinSpinner = new JSpinner();
		vMinSpinner.addChangeListener(new ChangeListener() {

		      
			@Override
			public void stateChanged(ChangeEvent e) {
				vcam.setvMin(Integer.parseInt(vMinSpinner.getValue().toString()));
		         				
			}
	    });
		vMinSpinner.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		GridBagConstraints gbc_vMinSpinner = new GridBagConstraints();
		gbc_vMinSpinner.insets = new Insets(0, 0, 5, 0);
		gbc_vMinSpinner.gridx = 1;
		gbc_vMinSpinner.gridy = 5;
		panel_1.add(vMinSpinner, gbc_vMinSpinner);
		
		JLabel lblVmax = new JLabel("Vmax");
		GridBagConstraints gbc_lblVmax = new GridBagConstraints();
		gbc_lblVmax.insets = new Insets(0, 0, 0, 5);
		gbc_lblVmax.gridx = 0;
		gbc_lblVmax.gridy = 6;
		panel_1.add(lblVmax, gbc_lblVmax);
		
		JSpinner vMaxSpinner = new JSpinner();
		vMaxSpinner.addChangeListener(new ChangeListener() {

		      
			@Override
			public void stateChanged(ChangeEvent e) {
				vcam.setvMax(Integer.parseInt(vMaxSpinner.getValue().toString()));
		         				
			}
	    });
		vMaxSpinner.setModel(new SpinnerNumberModel(255, 0, 255, 1));
		GridBagConstraints gbc_vMaxSpinner = new GridBagConstraints();
		gbc_vMaxSpinner.gridx = 1;
		gbc_vMaxSpinner.gridy = 6;
		panel_1.add(vMaxSpinner, gbc_vMaxSpinner);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		
		image=new ImageIcon();
		Height=panel.getHeight();
	 	Width=panel.getWidth(); 
	 
		imageLabel.setBounds(0, 0, Width, Height);
		imageLabel.setText("");
		 
		panel.add(imageLabel);
 	}
	
	public void showImage(Mat img) {
		 if (SizeCustom) {
		//	Imgproc.resize(img, img, new Size(Height, Width));
		}
		// Highgui.imencode(".jpg", img, matOfByte);
		// byte[] byteArray = matOfByte.toArray();
		BufferedImage bufImage = null;
		try {
			// InputStream in = new ByteArrayInputStream(byteArray);
			// bufImage = ImageIO.read(in);
			bufImage = toBufferedImage(img);
		

			image.setImage(bufImage);
			imageLabel.setIcon(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CREDITS TO DANIEL: http://danielbaggio.blogspot.com.br/ for the improved
	// version !

	public BufferedImage toBufferedImage(Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		 
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster()
				.getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		return image;

	}

	
}
