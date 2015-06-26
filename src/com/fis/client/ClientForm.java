package com.fis.client;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.List;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPasswordField;

import com.fis.client.chatgroup.GroupChatForm;
import com.fis.client.message.OnlineAnalyst;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientForm {

	private JFrame frame;	
	private List listGroup;
	
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
		textFieldAcc.setText("account");
		passwordField.setText("123456");
		lGroup = new LinkedList<GroupChatForm>();
		
		JButton btnNewButton = new JButton("Create group");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				GroupChatForm gChat = new GroupChatForm();
				lGroup.add(gChat);
				gChat.frame.setVisible(true);
				System.out.println(lGroup.size());
			}
		});
		btnNewButton.setBounds(354, 14, 116, 29);
		frame.getContentPane().add(btnNewButton);
		client = new Client(textFieldHost.getText());
		client.start();
	}
	
	public static java.util.List<GroupChatForm> lGroup;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Fis chat");
		
		frame.setBounds(100, 100, 628, 459);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		listUserOnline = new List();
		listUserOnline.setFont(new Font("Dialog", Font.BOLD, 14));
		listUserOnline.setForeground(Color.RED);
		
		listUserOnline.select(0);
		listUserOnline.setBounds(483, 8, 124, 360);
		frame.getContentPane().add(listUserOnline);
		
		
		textFieldMsg = new JTextField();
		textFieldMsg.setBounds(10, 379, 334, 30);
		frame.getContentPane().add(textFieldMsg);
		textFieldMsg.setColumns(10);
		
		JButton buttonSend = new JButton("Send");
		
		buttonSend.setBounds(354, 379, 81, 30);
		frame.getContentPane().add(buttonSend);
		
		textFieldAcc = new JTextField();
		textFieldAcc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldAcc.setText("");
			}
		});
		textFieldAcc.setBounds(83, 56, 116, 25);
		frame.getContentPane().add(textFieldAcc);
		textFieldAcc.setColumns(10);
		
		textFieldHost = new JTextField();
		textFieldHost.setFont(new Font("Tahoma", Font.ITALIC, 13));
		textFieldHost.setText("localhost");
		textFieldHost.setBounds(83, 12, 256, 25);
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
		
		JButton buttonLogout = new JButton("Logout");
		buttonLogout.setBounds(223, 95, 81, 23);
		frame.getContentPane().add(buttonLogout);
		
		passwordField = new JPasswordField();
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				passwordField.setText("");
			}
		});
		passwordField.setToolTipText("");
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		passwordField.setBounds(83, 92, 116, 25);
		frame.getContentPane().add(passwordField);
		
		JLabel lblGroup = new JLabel("Send Multi");
		lblGroup.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblGroup.setBounds(364, 54, 96, 25);
		frame.getContentPane().add(lblGroup);
		
		JButton btnAddToChat = new JButton("Add");
		
		btnAddToChat.setBounds(508, 379, 71, 30);
		frame.getContentPane().add(btnAddToChat);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 129, 334, 239);
		frame.getContentPane().add(scrollPane);
		
		textAreaChat = new JTextArea();
		textAreaChat.setLineWrap(true);
		textAreaChat.setEditable(false);
		scrollPane.setViewportView(textAreaChat);
		textAreaChat.setRows(500);
		
		listGroup = new List();
		listGroup.setFont(new Font("Dialog", Font.BOLD, 14));
		listGroup.setForeground(new Color(0, 102, 51));
		listGroup.setBounds(354, 77, 116, 226);
		frame.getContentPane().add(listGroup);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				listGroup.remove(userSelect);
			}
		});
		btnRemove.setBounds(354, 309, 116, 23);
		frame.getContentPane().add(btnRemove);
		
		JButton btnClearGroup = new JButton("Clear");
		btnClearGroup.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				listGroup.clear();
			}
		});
		btnClearGroup.setBounds(354, 345, 116, 23);
		frame.getContentPane().add(btnClearGroup);
		
		/*****Exit out application*****/
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int keep = JOptionPane.showConfirmDialog(
					    null,
					    "Would you like close application?",
					    "Shutdown",
					    JOptionPane.YES_NO_OPTION);
				if(keep == 0){
					client.close();
					System.exit(1);
				}
			}
		});
		
		/********send message**********/
		buttonSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(checkLogin){
					String msg = textFieldMsg.getText();
					if(msg.indexOf("@") == 0){
						String msgSend = getMsgMan(msg);
						if(!msgSend.equals("")){
							client.send(msgSend);
						}
					}
					else if(msg.indexOf("$") == 0){
						String msgSend = getMsgGroup(msg);
						client.send(msgSend);
					}
					else{
						String msgSend = getMsgAll(msg);
						client.send(msgSend);
					}
				}
				textFieldMsg.setText("");
			}
		});
		/**** login button ***/
		buttonLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!checkLogin){
					if(!textFieldAcc.getText().equals("")&&
							!passwordField.getText().equals("")){
						String msg = "{\"type\": \"acc\","
								+ " \"acc\": \""+ textFieldAcc.getText() +"\", "
								+ " \"pass\":\""+ passwordField.getText()+"\"  }";
						System.out.println(msg);
						client.send(msg);
					}
					else{
						textAreaChat.append("*********check again account or password !!!***\n");
					}
				}
			}
		});
		/*****change selected item in list online*****/
		listUserOnline.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int id = Integer.parseInt(e.getItem().toString());
				if(OnlineAnalyst.listOnl.size() != 0 ){
					userSelect = OnlineAnalyst.listOnl.get(id);
				}
			}
		});
		/***Add user to group chat**/
		btnAddToChat.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(userSelect != null){
					System.out.println(userSelect);
					listGroup.add(userSelect);
				}
			}
		});
		/****Logout event*****/
		buttonLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				checkLogin = false;
				textAreaChat.setText("\n\n***********You Logged !**********\n");
			}
		});
	}
	
	public String getMsgAll(String msg){
		textAreaChat.append("<Me>"+msg+"\n");
		msg = "{ \"type\": \"msg\", \"name\" : \""+textFieldAcc.getText()
				+"\", \"content\": \" "+msg+"\", \"recv\": [] }";
		//textFieldMsg.setText("");
		return msg;
	}
	
	public String getMsgGroup(String msg){
		msg = msg.trim();
		textAreaChat.append("<Me>"+msg+"\n");
		String message = "{ \"type\": \"msg\", \"name\" : \""+textFieldAcc.getText()
				+"\", \"content\": \" "+msg +"\", \"recv\": [";
		int size  = listGroup.countItems();
		for(int i = 0; i< size;++i){
			if(i != size - 1){
				message += "{ \"rname\" : \""+listGroup.getItem(i)+"\" },";
			}
			else{
				message += "{ \"rname\" : \""+listGroup.getItem(i)+"\" }";
			}
		}
		message += " ] }";
		//textFieldMsg.setText("");
		return message;
	}
	
	
	
	public String getMsgMan(String msg){
		String message = "";
		int endName = msg.indexOf(" ");
		textAreaChat.append("<Me>"+msg+"\n");
		String name = msg.substring(1, endName);
		for(Map.Entry<Integer, String> entry : OnlineAnalyst.listOnl.entrySet()){
			if(name.equals(entry.getValue())){
				message = "{ \"type\": \"msg\", \"name\" : \""+textFieldAcc.getText()+"\", \"content\": \" "+ 
				msg.substring(endName).trim() +"\", \"recv\": [{ \"rname\" : \""+name.trim()+"\" }] }";
			}
		}
		//textFieldMsg.setText("");
		return message;
	}
	
	public static JTextField textFieldAcc;
	public JTextField textFieldHost;
	private JPasswordField passwordField;
	
	public static Client client = null;
	private String userSelect = null;
	
	public static List listUserOnline;
	public static JTextArea textAreaChat;
	public JTextField textFieldMsg;
	
	public static boolean checkLogin = false;
}




