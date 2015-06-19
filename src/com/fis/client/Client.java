package com.fis.client;

import com.fis.server.message.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Client extends Thread{
	
	private String host = "";
	private DataInputStream is = null;
	private DataOutputStream os = null;
	private Socket clientSocket;
	private String msg;
	
	private ChatContent chatMsg;
	
	public static List<String> listOnline = new LinkedList<String>();
	
	private final int port = 10008;
	
	public Client(String host){
		this.host = host;
	}
	
	public void run() {
		try{
			clientSocket = new Socket("localhost", 10008);
			this.os = new DataOutputStream(clientSocket.getOutputStream());
			this.is = new DataInputStream(clientSocket.getInputStream());
			while(true){
				msg = this.is.readUTF();
				if(msg.equals("[login@true]")){
					ClientForm.txtPanelChat.setText("****Welcome you to FIS chat !!!***");
					ClientForm.checkLogin = true;
				}
				else if(msg.equals("[login@fail]")){
					ClientForm.textFieldMsg.setText("****Login fail, try again !!!***");
					ClientForm.checkLogin = false;
				}
				else{
					if(TypeMsg.getType(msg) == Type.MESSAGE){
						chatMsg = new ChatContent(msg);
						String s  = ClientForm.textFieldMsg.getText();
						s+= "<"+chatMsg.getName()+ ">" + chatMsg.getContent();
						ClientForm.textFieldMsg.setText(s);
					}
					if(TypeMsg.getType(msg) == Type.ONLINE){
						
					}
				}
				
			}
		}catch(IOException e){
			System.out.println("aaaaaaaaaaaaaaaaa");
			e.printStackTrace();
		}
	}

	public void close(){
		try{
			if(clientSocket !=  null){
				if(os != null){
					os.close();
				}
				if(is != null){
					is.close();
				}
				clientSocket.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void send(String msg){
		try{
			this.os.writeUTF(msg);
			System.out.println(msg);
			this.os.flush();
			
		}catch (IOException e) {
			// TODO: handle exception\
			e.printStackTrace();
		}
	}
}

