package com.fis.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import com.fis.server.message.ChatAnalyst;
import com.fis.server.message.GroupAnalyst;
import com.fis.server.message.Key;
import com.fis.server.message.Analyst;
import com.fis.server.message.UserAnalyst;
import com.fis.server.orcl.Login;

public class ClientThread extends Thread{
	
	private ChatAnalyst message = null;
	private UserAnalyst user = null;
	private GroupAnalyst group;
	
	private DataInputStream is =null;
	private DataOutputStream os = null;
	private Socket clientSocket = null;
	private GetMsgThread getMsg;
	
	public Queue<String> msgClient;
	
	public ClientThread(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	public void newStreamConnect() throws IOException{
		os = new DataOutputStream(clientSocket.getOutputStream());
		is = new DataInputStream(clientSocket.getInputStream());
	}
	
	public void run(){
		try{
			msgClient = new LinkedList<String>();
			newStreamConnect();
			getMsg = new GetMsgThread(is, msgClient);
			getMsg.start();
			do{
				//System.out.println("bbbbbbbbbbbbb");
				System.out.print("");
				if(msgClient.size()> 0){
					String msg = msgClient.remove();
					System.out.println(" LOgin : "+msg);
					Analyst analyst = new Analyst(msg);
					if(Key.ACCOUNT.equals(analyst.type)){
						loginDB(msg);
					}
					if(Key.MESSAGE.equals(analyst.type)){
						sendMessage(msg);
					}
					if(Key.GROUP.equals(analyst.type)){
						groupAction(msg);
					}
					if(Key.DISCONNECT.equals(analyst.type)){
						disconnectClient();
						break;
					}
				}
			}while(true);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void groupAction(String msg) throws IOException{
		group = new GroupAnalyst(msg);
		String nameG = group.getNameGroup();
		boolean check = true;
		for(int i = 0 ; i < Server.listGroup.size(); ++i){
			if(nameG.equals(Server.listGroup.get(i).nameGroup)){
				if(group.typeGroup().equals(Key.CHANGE)){
					if(group.getAction().equals(Key.ADD)){
						if(Server.listGroup.get(i).name.indexOf(group.getName())< 0){
							Server.listGroup.get(i).name.add(group.getName());
						}
						for(int k  = 0 ; k < Server.vSocket.size(); ++k){
							for(int j = 0 ; j < Server.listGroup.get(i).name.size(); ++j){
								if(Server.listGroup.get(i).name.get(j).equals(
												Server.vSocket.get(k).user.getAcc())){
									String newMsg = "{ \"type\" : \"group\", \"nameGroup\" : \""
												+group.getNameGroup()+"\", \"typeGroup\" : \"change\", \"online\" : [";
									for(int n = 0; n < Server.listGroup.get(i).name.size(); ++n ){
										newMsg+="{ \"name\" : \""+Server.listGroup.get(i).name.get(n)+"\" }";
									}
									newMsg += " ] }";
									Server.vSocket.get(k).os.writeUTF(newMsg + "\r\n");
								}
							}
						}
					}
					if(group.getAction().equals(Key.REMOVE)){
						Server.listGroup.get(i).name.remove(group.getName());
						for(int k  = 0 ; k < Server.vSocket.size(); ++k){
							for(int j = 0 ; j < Server.listGroup.get(i).name.size(); ++j){
								if(Server.listGroup.get(i).name.get(j).equals(
												Server.vSocket.get(k).user.getAcc())){
									String newMsg = "{ \"type\" : \"group\", \"nameGroup\" : \""
												+group.getNameGroup()+"\", \"typeGroup\" : \"change\", \"online\" : [";
									for(int n = 0; n < Server.listGroup.get(i).name.size(); ++n ){
										newMsg+="{ \"name\" : \""+Server.listGroup.get(i).name.get(n)+"\" }";
									}
									newMsg += " ] }";
									Server.vSocket.get(k).os.writeUTF(newMsg + "\r\n");
								}
							}
						}
						int lU = Server.listGroup.get(i).name.size();
						Group gval = Server.listGroup.get(i);
						if(lU == 0){
							Server.listGroup.remove(gval);
							break;
						}
					}
				}
				if(group.typeGroup().equals(Key.MESSAGE)){
					for(int k  = 0 ; k < Server.vSocket.size(); ++k){
						for(int j = 0 ; j < Server.listGroup.get(i).name.size(); ++j){
							if(Server.listGroup.get(i).name.get(j).equals(
											Server.vSocket.get(k).user.getAcc())){
								Server.vSocket.get(k).os.writeUTF(msg + "\r\n");
							}
						}
					}		
				}
				check = false;
			}
		}
		if(check){
			Group g = new Group();
			System.out.println(group.getNameGroup());
			g.nameGroup = group.getNameGroup();
			System.out.println("SENDER : " + group.getSender());
			g.name.add(group.getSender());
			Server.listGroup.add(g);
			String newMsg = "{ \"type\" : \"group\", \"nameGroup\" : \""
					+group.getNameGroup()+"\", \"typeGroup\" : \"change\", \"online\" : [ { \"name\" : \""
					+group.getSender()+"\"} ] }";
			os.writeUTF(newMsg + "\r\n");
		}

	}
	
	public void loginDB(String msg) throws IOException{
		user = new UserAnalyst(msg);		
		if(Login.checkAccount(user.getAcc(), user.getPass())){
			ServerForm.listUser.add(user.getAcc());
			os.writeUTF("{\"type\":\"result\", \"value\": \"true\"} \r\n");
		}
		else{
			os.writeUTF("{\"type\":\"result\", \"value\": \"false\"} \r\n");
		}
	}
	
	public void sendMessage(String msg) throws IOException{
		message = new ChatAnalyst(msg);
		int size = Server.vSocket.size();
		for(int i = 0; i< size;++i){
			if(this.message.getRecv().size() == 0){
				if(!Server.vSocket.get(i).user.getAcc().equals(user.getAcc())){
					Server.vSocket.get(i).os.writeUTF(msg + "\r\n");
				}
			}
			else{
				for(int j = 0 ; j < this.message.getRecv().size(); ++j){
					if(message.getRecv().get(j).equals(
							Server.vSocket.get(i).user.getAcc())){
						Server.vSocket.get(i).os.writeUTF(msg + "\r\n");
					}
				}
			}
		}
	}
	
	public void disconnectClient() throws IOException{
		os.close();
		is.close();
		clientSocket.close();
		ServerForm.listUser.remove(user.getAcc());
		Server.vSocket.remove(this);
	}

	public UserAnalyst getUser() {
		return user;
	}
	public Socket getClientSocket() {
		return clientSocket;
	}
	public DataOutputStream getOs() {
		return os;
	}
}
