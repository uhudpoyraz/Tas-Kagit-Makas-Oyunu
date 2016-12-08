package com.taskagitmakas.form;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.taskagitmakas.hog.Hog;
import com.taskagitmakas.hog.KNN;

import javax.swing.JSplitPane;

public class GameForm {


	public JFrame frame;
	private  ImageIcon image;
	private  JLabel imageLabel = new JLabel("image");
	private Boolean SizeCustom;
	private int Height, Width;
	private  BufferedImage imageFromCam;
	static CamRecorder vcam;
	private  Mat frameFromCam;
	static boolean isKeyPress = true;
	static boolean isCalibrated=false;
	public  KNN knn;
	public  Hog hog;
	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameForm window = new GameForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		
		 
	}*/

	/**
	 * Create the application.
	 */
	public GameForm() {

		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
			 
				knn=new KNN();
				hog=new Hog();
				 new Thread(new Runnable() {
				      public void run() {
				
				
						vcam = new CamRecorder();
						while(true){
							while (isKeyPress) {
								frameFromCam= vcam.startRecord();
								 imageFromCam=toBufferedImage(frameFromCam);
								
								if (imageFromCam != null) {
									image.setImage(imageFromCam);
									imageLabel.setIcon(image);
									imageLabel.updateUI();
								}
				
							}
							 
				
							while (!isKeyPress) {
								frameFromCam= vcam.train();
								 imageFromCam=toBufferedImage(frameFromCam);
								if (imageFromCam != null) {
									image.setImage(imageFromCam);
									imageLabel.setIcon(image);
									imageLabel.updateUI();
								}
				
							}
						
						}
						
				 
				      }
				    }).start();
			
				
			}
		});
	 

		frame.setBounds(100, 100, 1010, 787);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel userScreen = new JPanel();
		userScreen.setBounds(12, 48, 640, 480);
		userScreen.setToolTipText("");
		userScreen.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		userScreen.setLayout(null);
		frame.getContentPane().add(userScreen);

		JPanel computerScreen = new JPanel();
		computerScreen.setBounds(664, 48, 330, 480);
		computerScreen.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		frame.getContentPane().add(computerScreen);
		computerScreen.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		image = new ImageIcon();
		Height = userScreen.getHeight();
		Width = userScreen.getWidth();

		imageLabel.setBounds(0, 0, 640, 480);
		imageLabel.setText("");
		userScreen.add(imageLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 540, 960, 188);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblUserscoreLabel = new JLabel("Score :");
		lblUserscoreLabel.setBounds(180, 35, 49, 15);
		panel.add(lblUserscoreLabel);
		
		JLabel lblUserscore = new JLabel("0");
		lblUserscore.setBounds(264, 35, 8, 15);
		panel.add(lblUserscore);
		
		JButton btnNewButton = new JButton("Test");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 
				
			 
				 knn.testFromDescription(hog.getDescriptionFromMat(vcam.getImage()));
				 
			 
				
			}
		});
		btnNewButton.setBounds(449, 30, 106, 25);
		panel.add(btnNewButton);
		
		JLabel lblComputerScoreLabel = new JLabel("Score :");
		lblComputerScoreLabel.setBounds(748, 35, 49, 15);
		panel.add(lblComputerScoreLabel);
		
		JLabel lblComputerscore = new JLabel("0");
		lblComputerscore.setBounds(802, 35, 8, 15);
		panel.add(lblComputerscore);
		
		JButton btnKalibreEt = new JButton("Kalibre Et");
		btnKalibreEt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				isKeyPress = false;
			 
				 
			}
		});
		btnKalibreEt.setBounds(449, 82, 117, 25);
		panel.add(btnKalibreEt);
		
		JButton btnSfrla = new JButton("Sıfırla");
		btnSfrla.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				isKeyPress = true;
				
			}
		});
		btnSfrla.setBounds(449, 119, 117, 25);
		panel.add(btnSfrla);
		
		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(269, 21, 70, 15);
		frame.getContentPane().add(lblUser);
		
		JLabel lblComputer = new JLabel("Computer");
		lblComputer.setBounds(779, 21, 70, 15);
		frame.getContentPane().add(lblComputer);
		
		ButtonGroup radioButtonGroup=new ButtonGroup();
		frame.setVisible(true);
	}

	public void showImage(Mat img) {
		if (SizeCustom) {
			// Imgproc.resize(img, img, new Size(Height, Width));
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

	public static BufferedImage toBufferedImage(Mat m) {
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
