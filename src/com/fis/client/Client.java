package com.fis.client;

import com.fis.client.chatgroup.GroupChatForm;
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
	private ChatAnalyst chatMsg;
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
//			sendDemo();
			while(ckRun){
				msg = is.readUTF();
				System.out.println("MSG SERVER GUI VE: "+msg);
				Analyst analyst = new Analyst(msg);
				if(analyst.type.equals(Key.RESULT)){
					getResultLogin(msg);
				}
				if (analyst.type.equals(Key.MESSAGE)) {
					getMessage(msg);
				}
				if (analyst.type.equals(Key.ONLINE)) {
					getListOnline(msg);
				}
				if(analyst.type.equals(Key.GROUP)){
					getMsgGroup(msg);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
			ClientForm.textAreaChat.append("**Server disconnect, check your internet !!!**\n");
			ClientForm.checkLogin = false;
			System.exit(1);
		}
	}
	
	public void getMsgGroup(String msg){
		GroupAnalyst gAnalyst = new GroupAnalyst(msg);
		boolean check = true;
		for(int i = 0 ; i < ClientForm.lGroup.size(); ++i){
			if(ClientForm.lGroup.get(i).textFieldNameGroup.getText().equals(gAnalyst.getNameGroup())){
				if(gAnalyst.typeGroup().equals(Key.MESSAGE)){
					ClientForm.lGroup.get(i).textAreaChatGroup.append("<" +gAnalyst.getSender()+ ">>>"
								+ gAnalyst.getContent()+"\n");
				}
				if(gAnalyst.typeGroup().equals(Key.CHANGE)){
					ClientForm.lGroup.get(i).listGroupChat.removeAll();
					for (int j = 0; j < gAnalyst.getOnline().size(); ++j) {
						ClientForm.lGroup.get(i).listGroupChat.add(gAnalyst.getOnline().get(j));
					}
				}
				check = false;
			}
		}
		if(check){
			GroupChatForm gChat = new GroupChatForm();
			gChat.frame.setVisible(true);
			gChat.textFieldNameGroup.setText(gAnalyst.getNameGroup());
			if(gAnalyst.typeGroup().equals(Key.MESSAGE)){
				gChat.textAreaChatGroup.append("<" +gAnalyst.getSender()+ ">>>"
						+ gAnalyst.getContent() + "\n");
			}
			if(gAnalyst.typeGroup().equals(Key.CHANGE)){
				for (int j = 0; j < gAnalyst.getOnline().size(); ++j) {
					gChat.listGroupChat.add(gAnalyst.getOnline().get(j));
				}
			}
			ClientForm.lGroup.add(gChat);
		}
	}
	
	public void getResultLogin(String msg){
		ResultAnalyst rsLog = new ResultAnalyst(msg);
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
		chatMsg = new ChatAnalyst(msg);
		String s = "<"+chatMsg.getName()+ " >>>" + chatMsg.getContent();
		ClientForm.textAreaChat.append(s+"\n");
	}
	
	public void getListOnline(String msg){
		ClientForm.listUserOnline.removeAll();
		new OnlineAnalyst(msg);
		for(Map.Entry<Integer, String> entry : OnlineAnalyst.listOnl.entrySet()){
			ClientForm.listUserOnline.add(entry.getValue(), entry.getKey());
		}
	}

	public void close(){
		try{
			this.os.writeUTF("{ \"type\": \"dis\"} \r\n");
			ckRun = false;
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void send(String msg){
		try{
			os.writeUTF(msg+ "\r\n");
			System.out.println(msg);
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

