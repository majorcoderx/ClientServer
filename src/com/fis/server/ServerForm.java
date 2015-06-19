package com.fis.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JTextField;

import com.fis.server.orcl.ConnectDB;

public class ServerForm {

	private JFrame frame;
	public JTextField textFieldHost;
	public JTextField textFieldPort;
	
	private Server server = null;
	
	public static JTextPane textPaneChat;
	public static List listServer;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerForm window = new ServerForm();
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
	public ServerForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("FIS Server");
		frame.setBounds(100, 100, 476, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
			}
		});
		btnClear.setBounds(10, 288, 90, 36);
		frame.getContentPane().add(btnClear);
		
		JButton btnClose = new JButton("Close Server");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		btnClose.setBounds(335, 288, 109, 36);
		frame.getContentPane().add(btnClose);
		
		JButton btnOpen = new JButton("Open Server");
		
		btnOpen.setBounds(160, 288, 109, 36);
		frame.getContentPane().add(btnOpen);
		
		listServer = new List();
		listServer.setFont(new Font("Dialog", Font.ITALIC, 13));
		listServer.setBounds(335, 41, 109, 235);
		frame.getContentPane().add(listServer);
		
		textFieldHost = new JTextField();
		textFieldHost.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textFieldHost.setText("localhost");
		textFieldHost.setBounds(131, 7, 313, 28);
		frame.getContentPane().add(textFieldHost);
		textFieldHost.setColumns(10);
		
		JLabel lblHostname = new JLabel("Hostname:");
		lblHostname.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblHostname.setBounds(10, 11, 109, 14);
		frame.getContentPane().add(lblHostname);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Tahoma", Font.ITALIC, 14));
		lblPort.setBounds(10, 54, 46, 14);
		frame.getContentPane().add(lblPort);
		
		textFieldPort = new JTextField();
		textFieldPort.setText("10008");
		textFieldPort.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textFieldPort.setBounds(131, 46, 187, 29);
		frame.getContentPane().add(textFieldPort);
		textFieldPort.setColumns(10);
		
		textPaneChat = new JTextPane();
		textPaneChat.setBounds(10, 79, 308, 198);
		frame.getContentPane().add(textPaneChat);
		
		btnOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent pres) {
				new ConnectDB();
				server = new Server(Integer.parseInt(textFieldPort.getText()),
						textFieldHost.getText());
				server.start();
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg) {
				// close server and disable connect to DB
				int keep = JOptionPane.showConfirmDialog(
					    null,
					    "Would you like close application?",
					    "Shutdown",
					    JOptionPane.YES_NO_OPTION);
				if(keep == 0){
					try{
						ConnectDB.conn.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
					finally{
						if(server != null){
							server.close();
						}
						System.exit(1);
					}
				}				
			}
		});
	}
}
