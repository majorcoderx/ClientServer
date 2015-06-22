package com.fis.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.fis.server.message.ChatContent;
import com.fis.server.message.Type;
import com.fis.server.message.TypeMsg;
import com.fis.server.message.UserContent;
import com.fis.server.orcl.Login;

public class ClientThread extends Thread{
	
	private ChatContent message = null;
	private UserContent user = null;
	private DataInputStream is =null;
	private DataOutputStream os = null;
	private Socket clientSocket = null;
	
	public ClientThread(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	public void newStreamConnect() throws IOException{
		os = new DataOutputStream(clientSocket.getOutputStream());
		is = new DataInputStream(clientSocket.getInputStream());
	}
	
	public void run(){
		try{
			newStreamConnect();
			String msg;
			while(true){
				msg = is.readUTF();
				if(Type.ACCOUNT == TypeMsg.getType(msg)){
					loginDB(msg);
				}
				if(Type.MESSAGE == TypeMsg.getType(msg)){
					sendMessage(msg);
				}
				if(Type.DISCONNECT == TypeMsg.getType(msg)){
					disconnectClient();
					break;
				}
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loginDB(String msg) throws IOException{
		user = new UserContent(msg);		
		if(Login.checkAccount(user.getAcc(), user.getPass())){
			os.writeUTF("{\"type\":\"result\", \"value\": \"true\"}");
			os.flush();
		}
		else{
			os.writeUTF("{\"type\":\"result\", \"value\": \"false\"}");
			os.flush();
		}
	}
	
	public void sendMessage(String msg) throws IOException{
		message = new ChatContent(msg);
		int size = Server.vSocket.size();
		for(int i = 0; i< size;++i){
			if(this.message.getRecv().size() == 0){
				if(!Server.vSocket.get(i).user.getAcc().equals(user.getAcc())){
					Server.vSocket.get(i).os.writeUTF(msg);
					Server.vSocket.get(i).os.flush();
				}
			}
			else{
				for(int j = 0 ; j < this.message.getRecv().size(); ++j){
					if(message.getRecv().get(j).equals(
							Server.vSocket.get(i).user.getAcc())){
						System.out.println("GUI GOI TIN: "+ msg);
						Server.vSocket.get(i).os.writeUTF(msg);
						Server.vSocket.get(i).os.flush();
					}
				}
			}
		}
	}
	
	public void disconnectClient() throws IOException{
		os.close();
		is.close();
		clientSocket.close();
		Server.vSocket.remove(this);
	}

	public UserContent getUser() {
		return user;
	}
	public Socket getClientSocket() {
		return clientSocket;
	}
	public DataOutputStream getOs() {
		return os;
	}
}
