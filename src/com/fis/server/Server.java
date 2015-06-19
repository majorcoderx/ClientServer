/*Hi guys,
 * There's some line code for a thread server
 * It as mini server of list server at SUPER SERVER chat
 */

package com.fis.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Vector;

import com.fis.server.orcl.ConnectDB;


public class Server extends Thread{

	private Socket clientSocket = null;
	private ServerSocket serverSocket = null;
	private String hostname = null;
	private int port;
	public static Vector<clientThread> vSocket =  null;
	public Server(int port, String host){
		this.port = port;
		this.hostname = host;
		System.out.println(this.port);
	}
	
	public void run() {
		vSocket = new Vector<clientThread>();
		
		new ConnectDB();
		try{
			serverSocket = new ServerSocket(this.port);
			while(true){
				clientSocket = serverSocket.accept();
				vSocket.add(0, new clientThread(clientSocket));
				vSocket.firstElement().start();
				OnlineUser onl = new OnlineUser();
				onl.start();
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
