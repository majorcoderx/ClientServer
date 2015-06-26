package com.fis.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Queue;

public class GetMsgThread extends Thread{
	
	private DataInputStream is;
	private Queue<String> msgClient;
	public GetMsgThread(DataInputStream is,Queue<String> msgClient){
		this.is = is;
		this.msgClient = msgClient;
	}
	
	public void run(){
		try{
			while(true){
				msgClient.add(is.readUTF());
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
