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

public class clientThread extends Thread{
	
	private ChatContent message = null;
	private UserContent user = null;
	private DataInputStream is =null;
	private DataOutputStream os = null;
	private Socket clientSocket = null;
	
	public clientThread(Socket clientSocket){
		this.clientSocket = clientSocket;
		System.out.println("Port of Client: "+this.clientSocket.getPort());
	}
	
	public void run(){
		try{
			System.out.println("Bat dau thread " + this.clientSocket.getPort());
			this.os = new DataOutputStream(clientSocket.getOutputStream());
			this.is = new DataInputStream(clientSocket.getInputStream());
			
			String msg;
			while(true){
				msg = this.is.readUTF();
				System.out.println("msg client :" + msg);
				System.out.println("Do lon cua vSocket :" + Server.vSocket.size());
				
				if(Type.ACCOUNT == TypeMsg.getType(msg)){
					//connect check db
					user = new UserContent(msg);
					if(Login.checkAccount(user.getAcc(), user.getPass())){
						this.os.writeUTF("true");
					}
					else{
						this.os.writeUTF("false");
					}
				}
				if(Type.MESSAGE == TypeMsg.getType(msg)){
					message = new ChatContent(msg);
					int size = Server.vSocket.size();
					for(int i = 0; i< size;++i){
						if(msg.equals("@quit")){
							this.os.close();
							this.is.close();
							this.clientSocket.close();
							Server.vSocket.remove(i);
							break;
						}
						for(int j = 0 ; j < this.message.getRecv().size(); ++i){
							if(this.message.getRecv().get(j).equals(
									Server.vSocket.get(i).message.getName())){
								Server.vSocket.get(i).os.writeUTF(msg);
								Server.vSocket.get(i).os.flush();
							}
						}
					}
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
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
