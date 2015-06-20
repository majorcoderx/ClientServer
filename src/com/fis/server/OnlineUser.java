/*Hi all,
 * It code for online running on client chat
 * It packing message in a json file and send to client
 * Client decryping message, and show on list user online
 * 
 * type of  json string
 * {
 * 	"type" : "online",
 * 	"user" : [
 * 		{
 * 			"name": "---",
 * 			"name": "---"
 * 			 ............
 * 		}	
 *  ]
 * }
 * 
 */

package com.fis.server;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class OnlineUser extends Thread {
	private List<String> lUO;
	private String msg;
	
	public void run(){
		try{
			while(true){
				lUO =  new LinkedList<String>();
				msg  = "{ \"type\": \"online\", \"user\" :[  ";
				for(int  i = 0;i < Server.vSocket.size(); ++i){
					if(Server.vSocket.get(i).getUser() != null){
						lUO.add(Server.vSocket.get(i).getUser().getAcc());
					}
				}
				for(int  i = 0; i < lUO.size(); ++i){
					if(i != (lUO.size() - 1)){
						msg += " { \"name\": \"" + lUO.get(i) + "\" }, ";
					}
					else{
						msg+= " { \"name\": \"" + lUO.get(i) + "\" } ";
					}
				}
				msg += " ]}";
				for(int i = 0; i < Server.vSocket.size(); ++i){
					if(Server.vSocket.get(i).getOs() != null){
						Server.vSocket.get(i).getOs().writeUTF(msg);
					}
				}
				Thread.sleep(32000);
			}
		}catch(IOException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
