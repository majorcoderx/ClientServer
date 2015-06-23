package com.fis.client;

import com.fis.client.message.OnlineUser;
import com.fis.client.message.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class Client extends Thread{
	
	private String host;
	private DataInputStream is = null;
	private DataOutputStream os = null;
	private Socket clientSocket;
	private String msg;
	private ChatContent chatMsg;
	private final int port = 10008;
	private boolean ckRun = true;
	
	public Client(String host){
		this.host = host;
	}
	
	public void run() {
		try{
			clientSocket = new Socket(host, port);
			ClientForm.textAreaChat.append("***Connect to FIS Server success !!!***\n\n");
			os = new DataOutputStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
			while(ckRun){
				msg = is.readUTF();
				System.out.println("MSG SERVER GUI VE: "+msg);
				if(TypeMsg.getType(msg) == Type.RESULT){
					getResultLogin(msg);
				}
				if (TypeMsg.getType(msg) == Type.MESSAGE) {
					getMessage(msg);
				}
				if (TypeMsg.getType(msg) == Type.ONLINE) {
					getListOnline(msg);
				}	
			}
		}catch(IOException e){
			e.printStackTrace();
			ClientForm.textAreaChat.append("**Server disconnect, check your internet !!!**\n");
			ClientForm.checkLogin = false;
			System.exit(1);
		}
	}
	
	public void getResultLogin(String msg){
		ResultLogin rsLog = new ResultLogin(msg);
		if(rsLog.result){
			welcomeChat();
			ClientForm.checkLogin = true;
		}
		else{
			loginFail();
			ClientForm.checkLogin = false;
		}
	}
	
	public void getMessage(String msg){
		chatMsg = new ChatContent(msg);
		String s = "<"+chatMsg.getName()+ " >>>" + chatMsg.getContent();
		ClientForm.textAreaChat.append(s+"\n");
	}
	
	public void getListOnline(String msg){
		ClientForm.listUserOnline.removeAll();
		new OnlineUser(msg);
		for(Map.Entry<Integer, String> entry : OnlineUser.listOnl.entrySet()){
			ClientForm.listUserOnline.add(entry.getValue(), entry.getKey());
		}
	}

	public void close(){
		try{
			this.os.writeUTF("{ \"type\": \"disconnect\"}");
			ckRun = false;
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void send(String msg){
		try{
			os.writeUTF(msg);
			System.out.println(msg);
			os.flush();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loginFail(){
		ClientForm.textAreaChat.append("****Login fail, try again !!!******\n");
		ClientForm.textAreaChat.append("***********************************\n");
	}
	
	public void welcomeChat(){
		ClientForm.textAreaChat.append("****Welcome you to FIS chat !!!***\n");
		ClientForm.textAreaChat.append("*Add fist char @ : chat with man !\n");
		ClientForm.textAreaChat.append("**Add fist char $ : chat group !!*\n");
		ClientForm.textAreaChat.append("******Not char : chat all !!!*****\n");
		ClientForm.textAreaChat.append("**********************************\n");
	}
	
}

