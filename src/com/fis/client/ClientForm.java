package com.fis.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import java.awt.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPasswordField;
import javax.swing.JList;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class ClientForm {

	private JFrame frame;
	
	public JTextField textFieldMsg;
	public JTextField textFieldAcc;
	public JTextField textFieldHost;
	private JPasswordField passwordField;
	private List listUserOnline;
	
	private Client client = null;
	public static JTextPane txtPanelChat;
	
	private boolean checkLogin = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientForm window = new ClientForm();
					window.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Fis chat");
		
		frame.setBounds(100, 100, 724, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		txtPanelChat = new JTextPane();
		txtPanelChat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPanelChat.setBounds(10, 135, 386, 237);
		frame.getContentPane().add(txtPanelChat);
		
		listUserOnline = new List();
		
		listUserOnline.select(0);
		listUserOnline.setBounds(562, 8, 141, 354);
		frame.getContentPane().add(listUserOnline);
		//demo
		listUserOnline.add("DO", 1);
		listUserOnline.add("Thanh", 2);
		listUserOnline.add("Hung",3);
		
		
		textFieldMsg = new JTextField();
		textFieldMsg.setBounds(10, 379, 448, 30);
		frame.getContentPane().add(textFieldMsg);
		textFieldMsg.setColumns(10);
		
		JButton buttonSend = new JButton("Send");
		
		buttonSend.setBounds(468, 379, 81, 30);
		frame.getContentPane().add(buttonSend);
		
		textFieldAcc = new JTextField();
		textFieldAcc.setBounds(83, 56, 116, 25);
		frame.getContentPane().add(textFieldAcc);
		textFieldAcc.setColumns(10);
		
		textFieldHost = new JTextField();
		textFieldHost.setFont(new Font("Tahoma", Font.ITALIC, 13));
		textFieldHost.setText("localhost");
		textFieldHost.setBounds(83, 12, 222, 25);
		frame.getContentPane().add(textFieldHost);
		textFieldHost.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassword.setBounds(10, 98, 71, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblHost = new JLabel("Host:");
		lblHost.setFont(new Font("Tahoma", Font.ITALIC, 13));
		lblHost.setBounds(10, 14, 46, 14);
		frame.getContentPane().add(lblHost);
		
		JLabel lblAccount = new JLabel("Account:");
		lblAccount.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAccount.setBounds(10, 60, 63, 14);
		frame.getContentPane().add(lblAccount);
		
		JButton buttonLogin = new JButton("Login");
		buttonLogin.setBounds(223, 56, 81, 25);
		frame.getContentPane().add(buttonLogin);
		
		JButton buttonConnect = new JButton("Connect");
		buttonConnect.setBounds(315, 12, 81, 26);
		frame.getContentPane().add(buttonConnect);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Spider man\\Desktop\\aspect4.jpg"));
		lblNewLabel.setBounds(402, 8, 162, 121);
		frame.getContentPane().add(lblNewLabel);
		
		JButton buttonLogout = new JButton("Logout");
		buttonLogout.setBounds(223, 95, 81, 23);
		frame.getContentPane().add(buttonLogout);
		
		passwordField = new JPasswordField();
		passwordField.setToolTipText("");
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		passwordField.setBounds(83, 92, 116, 25);
		frame.getContentPane().add(passwordField);
		
		JLabel lblGroup = new JLabel("Group");
		lblGroup.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGroup.setBounds(406, 135, 71, 25);
		frame.getContentPane().add(lblGroup);
		
		JRadioButton rdbtnSelectGroup = new JRadioButton("select group");
		rdbtnSelectGroup.setBounds(406, 343, 109, 23);
		frame.getContentPane().add(rdbtnSelectGroup);
		
		JTextPane textPaneGroupChat = new JTextPane();
		textPaneGroupChat.setBounds(406, 160, 150, 176);
		frame.getContentPane().add(textPaneGroupChat);
		
		JButton btnAddToChat = new JButton("Add to group");
		btnAddToChat.setBounds(562, 376, 141, 30);
		frame.getContentPane().add(btnAddToChat);
		
		buttonConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				client = new Client(textFieldHost.getText());
				client.start();
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int keep = JOptionPane.showConfirmDialog(
					    null,
					    "Would you like close application?",
					    "Shutdown",
					    JOptionPane.YES_NO_OPTION);
				if(keep == 0){
					if(client != null){
						client.close();
					}
					System.exit(1);
				}
				else{
					
				}
			}
		});
		
		/********send message**********/
		buttonSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(checkLogin){
					
				}
				String msg = "{\"\":\"\","
						+ " \"\": \"\","
						+ " \"\":\"\"  }";
				for(int i  = 0; i< listUserOnline.getItemCount() ; ++i){
					
				}
			}
		});
		/**** login button ***/
		buttonLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!checkLogin){
					String msg = "{\"type\": \"acc\","
							+ " \"acc\": \""+ textFieldAcc.getText() +" \", "
							+ " \"pass\":\""+ passwordField.getText()+"\"  }";
					client.send(msg);
				}
				
			}
		});
		/*****change selected item in list online*****/
		listUserOnline.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
			}
		});
	}
}
