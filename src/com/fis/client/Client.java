package com.fis.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{
	
	private String host = "";
	private DataInputStream is = null;
	private DataOutputStream os = null;
	private Socket clientSocket;
	
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
				String msg = "";
				msg = this.is.readUTF().toString();
				
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

