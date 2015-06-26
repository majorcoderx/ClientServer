/*Hi guys,
 * There's some line code for a thread server
 * It as mini server of list server at SUPER SERVER chat
 */

package com.fis.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.fis.server.orcl.ConnectDB;


public class Server extends Thread{

	private Socket clientSocket = null;
	private ServerSocket serverSocket = null;
	private String hostname = null;
	private int port;
	public static Vector<ClientThread> vSocket =  null;
	private boolean ckRunOline = false;
	
	public static List<Group> listGroup;
	
	public Server(int port, String host){
		this.port = port;
		this.hostname = host;
	}
	
	public void run() {
		listGroup = new LinkedList<Group>();
		vSocket = new Vector<ClientThread>();
		new ConnectDB();
		OnlineUser onl = new OnlineUser();
		try{
			serverSocket = new ServerSocket(port);
			ServerForm.textAreaChat.append(">>>>HOST NAME: " + hostname + ", PORT: " + port+ "\n");
			ServerForm.textAreaChat.append("****OPEN SERVER - START WORKING !!!***\n");
			while(true){
				clientSocket = serverSocket.accept();
				vSocket.add(0, new ClientThread(clientSocket));
				vSocket.firstElement().start();
				if(!ckRunOline){
					onl.start();
					ckRunOline = true;
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	
	public void close(){
		try{
			if(serverSocket != null){
				serverSocket.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
