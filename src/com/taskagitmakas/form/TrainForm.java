package com.taskagitmakas.form;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import com.taskagitmakas.hog.Hog;

import javax.swing.JRadioButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TrainForm {
 

		public JFrame frame;
		private static ImageIcon image;
		private static JLabel imageLabel = new JLabel("image");
		private Boolean SizeCustom;
		private int Height, Width;
		private static BufferedImage imageFromCam;
		static CamRecorder vcam;
		private static Mat frameFromCam;
		static boolean isKeyPress = true;
		static boolean isCalibrated=false;
		/**
		 * Launch the application.
		 */
	/*	public static void main(String[] args) {
	 
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						TrainForm window = new TrainForm();
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
		public TrainForm() {

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
		 

			frame.setBounds(100, 100, 900, 650);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);

			JPanel panel = new JPanel();
			panel.setBounds(0, 0, 678, 591);
			panel.setToolTipText("");
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel.setLayout(null);
			frame.getContentPane().add(panel);

			JPanel panel_1 = new JPanel();
			panel_1.setBounds(690, 0, 196, 591);
			panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			frame.getContentPane().add(panel_1);
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[] { 78, 0, 0, 0 };
			gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
			gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
			panel_1.setLayout(gbl_panel_1);
			
			JRadioButton classTypeRadioTas = new JRadioButton("Taş");
			GridBagConstraints gbc_classTypeRadioTas = new GridBagConstraints();
			gbc_classTypeRadioTas.anchor = GridBagConstraints.WEST;
			gbc_classTypeRadioTas.insets = new Insets(0, 0, 5, 5);
			gbc_classTypeRadioTas.gridx = 1;
			gbc_classTypeRadioTas.gridy = 2;
			panel_1.add(classTypeRadioTas, gbc_classTypeRadioTas);
			
			JRadioButton classTypeRadioKagit = new JRadioButton("Kagit");
			GridBagConstraints gbc_classTypeRadioKagit = new GridBagConstraints();
			gbc_classTypeRadioKagit.anchor = GridBagConstraints.WEST;
			gbc_classTypeRadioKagit.insets = new Insets(0, 0, 5, 5);
			gbc_classTypeRadioKagit.gridx = 1;
			gbc_classTypeRadioKagit.gridy = 3;
			panel_1.add(classTypeRadioKagit, gbc_classTypeRadioKagit);
			
			JRadioButton classTypeRadioMakas = new JRadioButton("Makas");
			GridBagConstraints gbc_classTypeRadioMakas = new GridBagConstraints();
			gbc_classTypeRadioMakas.anchor = GridBagConstraints.WEST;
			gbc_classTypeRadioMakas.insets = new Insets(0, 0, 5, 5);
			gbc_classTypeRadioMakas.gridx = 1;
			gbc_classTypeRadioMakas.gridy = 4;
			panel_1.add(classTypeRadioMakas, gbc_classTypeRadioMakas);

			JMenuBar menuBar = new JMenuBar();
			frame.setJMenuBar(menuBar);

			image = new ImageIcon();
			Height = panel.getHeight();
			Width = panel.getWidth();

			imageLabel.setBounds(12, 0, 666, 591);
			imageLabel.setText("");
			panel.add(imageLabel);
			
			ButtonGroup radioButtonGroup=new ButtonGroup();
			radioButtonGroup.add(classTypeRadioTas);
			radioButtonGroup.add(classTypeRadioKagit);
			radioButtonGroup.add(classTypeRadioMakas);
			
			JButton startCalibrationButton = new JButton("Kalibrasyon Sıfırla");
			startCalibrationButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					
					isKeyPress = true;
				}
			});
			
			JButton saveButton = new JButton("Kayit Et");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					 
					Hog hog=new Hog();
					int classType = 0;
					if(classTypeRadioTas.isSelected()){
						
						classType=1;
					}
					if(classTypeRadioKagit.isSelected()){
											
						classType=2;
										}
					if(classTypeRadioMakas.isSelected()){
						
						classType=3;
					}
					vcam.saveImage();
					Imgproc.resize(frameFromCam, frameFromCam, new Size(64, 48));
					hog.addFromMat(frameFromCam, classType);
			
					
					
					
					
					
				}
			});
			saveButton.setVisible(false);
			GridBagConstraints gbc_saveButton = new GridBagConstraints();
			gbc_saveButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_saveButton.insets = new Insets(0, 0, 5, 5);
			gbc_saveButton.gridx = 1;
			gbc_saveButton.gridy = 5;
			panel_1.add(saveButton, gbc_saveButton);
			GridBagConstraints gbc_startCalibrationButton = new GridBagConstraints();
			gbc_startCalibrationButton.insets = new Insets(0, 0, 5, 5);
			gbc_startCalibrationButton.gridx = 1;
			gbc_startCalibrationButton.gridy = 7;
			panel_1.add(startCalibrationButton, gbc_startCalibrationButton);
			
			JButton doCalibrationButton = new JButton("Kalibre Et");
			doCalibrationButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					isKeyPress = false;
					saveButton.setVisible(true);
					startCalibrationButton.setVisible(true);
				}
			});
			
			GridBagConstraints gbc_doCalibrationButton = new GridBagConstraints();
			gbc_doCalibrationButton.fill = GridBagConstraints.HORIZONTAL;
			gbc_doCalibrationButton.insets = new Insets(0, 0, 5, 5);
			gbc_doCalibrationButton.gridx = 1;
			gbc_doCalibrationButton.gridy = 8;
			panel_1.add(doCalibrationButton, gbc_doCalibrationButton);
			
			
			
			startCalibrationButton.setVisible(false);
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