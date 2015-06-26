package com.fis.client.chatgroup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import java.awt.List;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.fis.client.ClientForm;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GroupChatForm {

	public JFrame frame;
	public JTextField textFieldNameGroup;
	public JTextArea textAreaChatGroup;
	public List listGroupChat;
	private JTextField textFieldAdduser;

	
	public static String nameGroup;
	private JTextField textFieldMsg;
	private JButton btnSendMSG;
	private JScrollPane scrollPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupChatForm window = new GroupChatForm();
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
	public GroupChatForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(ClientForm.textFieldAcc.getText());
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				ClientForm.client.send("{ \"type\" : \"group\", \"nameGroup\" : \""	
						+textFieldNameGroup.getText()+"\", \"typeGroup\" : \"change\", "
								+ "\"action\" : \"remove\", \"name\" : \""+
								ClientForm.textFieldAcc.getText()+"\", \"sender\":\""+
								ClientForm.textFieldAcc.getText()+"\" }");
			}
		});
		frame.setBounds(100, 100, 435, 359);
		frame.getContentPane().setLayout(null);
		
		textFieldNameGroup = new JTextField();
		textFieldNameGroup.setBounds(10, 11, 188, 29);
		frame.getContentPane().add(textFieldNameGroup);
		textFieldNameGroup.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 91, 287, 179);
		frame.getContentPane().add(scrollPane);
		
		textAreaChatGroup = new JTextArea();
		scrollPane.setViewportView(textAreaChatGroup);
		
		
		listGroupChat = new List();
		listGroupChat.setBounds(303, 11, 110, 263);
		frame.getContentPane().add(listGroupChat);
		
		JButton btnNewName = new JButton("Create name");
		/***Create name for group***/
		btnNewName.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				nameGroup = textFieldNameGroup.getText();
				ClientForm.client.send("{ \"type\" : \"group\", \"nameGroup\" : \""	
						+textFieldNameGroup.getText()+"\", \"sender\" : \""+
						ClientForm.textFieldAcc.getText()+"\" }");
			}
		});
		btnNewName.setHorizontalAlignment(SwingConstants.RIGHT);
		btnNewName.setBounds(208, 11, 89, 29);
		frame.getContentPane().add(btnNewName);
		
		textFieldAdduser = new JTextField();
		textFieldAdduser.setBounds(10, 51, 188, 29);
		frame.getContentPane().add(textFieldAdduser);
		textFieldAdduser.setColumns(10);
		
		JButton btnAdduser = new JButton("Add");
		/****Add new user*****/
		btnAdduser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				for(int i = 0 ; i < ClientForm.listUserOnline.countItems(); ++i){
					if(textFieldAdduser.getText().equals(ClientForm.listUserOnline.getItem(i))){
//						listGroupChat.add(textFieldAdduser.getText());
						ClientForm.client.send("{ \"type\" : \"group\", \"nameGroup\" : \""	
								+textFieldNameGroup.getText()+"\", \"typeGroup\" : \"change\", "
										+ "\"action\" : \"add\", \"name\" : \""+
										textFieldAdduser.getText()+"\", \"sender\":\""+
										ClientForm.textFieldAcc.getText()+"\" }");
					}
				}
			}
		});
		btnAdduser.setBounds(208, 51, 89, 29);
		frame.getContentPane().add(btnAdduser);
		
		textFieldMsg = new JTextField();
		textFieldMsg.setBounds(10, 281, 287, 33);
		frame.getContentPane().add(textFieldMsg);
		textFieldMsg.setColumns(10);
		
		btnSendMSG = new JButton("send");
		/***Send message****/
		btnSendMSG.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String msg = "{ \"type\" : \"group\", \"nameGroup\" : \""+
						textFieldNameGroup.getText()+"\", \"typeGroup\" : \"msg\", \"content\" : \""+textFieldMsg.getText()+
						"\", \"sender\": \""+ClientForm.textFieldAcc.getText()+"\" }";
				ClientForm.client.send(msg + "\r\n");
				textFieldMsg.setText("");
			}
		});
		btnSendMSG.setBounds(303, 280, 110, 34);
		frame.getContentPane().add(btnSendMSG);
	}
	
}
