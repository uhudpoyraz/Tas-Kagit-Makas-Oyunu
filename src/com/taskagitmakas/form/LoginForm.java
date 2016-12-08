package com.taskagitmakas.form;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;

public class LoginForm {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm window = new LoginForm();
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
	public LoginForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 405, 103);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{96, 209, 0, 0};
		gridBagLayout.rowHeights = new int[]{40, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblAdSoyad = new JLabel("Adı Soyadı:");
		GridBagConstraints gbc_lblAdSoyad = new GridBagConstraints();
		gbc_lblAdSoyad.anchor = GridBagConstraints.EAST;
		gbc_lblAdSoyad.insets = new Insets(0, 0, 5, 5);
		gbc_lblAdSoyad.fill = GridBagConstraints.VERTICAL;
		gbc_lblAdSoyad.gridx = 0;
		gbc_lblAdSoyad.gridy = 1;
		frame.getContentPane().add(lblAdSoyad, gbc_lblAdSoyad);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnGiri = new JButton("GİRİŞ");
		GridBagConstraints gbc_btnGiri = new GridBagConstraints();
		gbc_btnGiri.insets = new Insets(0, 0, 5, 0);
		gbc_btnGiri.gridx = 2;
		gbc_btnGiri.gridy = 1;
		frame.getContentPane().add(btnGiri, gbc_btnGiri);
	}

}