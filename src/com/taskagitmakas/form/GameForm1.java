package com.taskagitmakas.form;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.opencv.core.Mat;

import com.taskagitmakas.entity.Image;
import com.taskagitmakas.hog.KNN;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class GameForm1 {

	
	
	private static ImageIcon image;
	private static JLabel imageLabel = new JLabel("image");
	private Boolean SizeCustom;
	private int Height, Width;
	private static BufferedImage imageFromCam;
	static CamRecorder vcam;
	private Mat frameFromCam;
	static boolean isKeyPress = true;
	static boolean isCalibrated=false;
	private JFrame frame;
	public KNN knn;

	/**
	 * Launch the application.
	 */
	
	/*public static void main(String[] args) {
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
	public GameForm1() {
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
				 new Thread(new Runnable() {
				      public void run() {
				
				
						vcam = new CamRecorder();
						while(true){
						 
								frameFromCam= vcam.startRecord();
								 imageFromCam=toBufferedImage(frameFromCam);
								 
						 
								if (imageFromCam != null) {
									image.setImage(imageFromCam);
									imageLabel.setIcon(image);
									imageLabel.updateUI();
								}
				
						}
						
				 
				      }
				    }).start();
			
				
			}
		});
	 
		frame.setBounds(100, 100, 993, 741);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblTakatmakasoyunu = new JLabel("TAŞ-KAĞIT-MAKAS-OYUNU");
		JLabel lblOyuncu = new JLabel("OYUNCU");
		JLabel lblBilgisayar = new JLabel("BİLGİSAYAR");
		JButton btnOyunaBala = new JButton("OYUNA BAŞLA");
		JLabel lblSkor = new JLabel("SKOR:");
		JLabel label = new JLabel("SKOR:");
		JLabel skrOyuncu = new JLabel("0");
		JLabel skrBilg = new JLabel("0");
		JButton btnCek = new JButton("TEST");
		btnCek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			 
				
				
				
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(254)
							.addComponent(lblTakatmakasoyunu))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(29)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(350, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(123)
					.addComponent(lblOyuncu)
					.addPreferredGap(ComponentPlacement.RELATED, 545, Short.MAX_VALUE)
					.addComponent(lblBilgisayar)
					.addGap(186))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(116)
					.addComponent(lblSkor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(skrOyuncu)
					.addGap(82)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(btnCek))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnOyunaBala)
							.addGap(26)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(skrBilg)))
					.addContainerGap(517, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblTakatmakasoyunu)
					.addGap(14)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOyuncu)
						.addComponent(lblBilgisayar))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(lblSkor)
						.addComponent(skrOyuncu)
						.addComponent(skrBilg)
						.addComponent(btnOyunaBala))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCek)
					.addContainerGap(291, Short.MAX_VALUE))
		);
		
		JLabel imageLabel2 = new JLabel("İMAGE LABEL");
		panel_1.add(imageLabel2);
		
		JLabel geriSayim = new JLabel("3 2 1");
		geriSayim.setForeground(Color.GREEN);
		geriSayim.setFont(new Font("Dialog", Font.BOLD, 36));
		
		
		
	 
	 
 
		imageLabel.setBounds(12, 0, 666, 591);
		imageLabel.setText("asdad");
		panel.add(imageLabel);
		frame.getContentPane().setLayout(groupLayout);
		frame.setVisible(true);
	}

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