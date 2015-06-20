/*{
 * 	"type": "",
 * 	"name":"",
 * 	"content":"",
 * 	"recv":[
 * 	{
 * 		"rname" : ""
 * 	},
 * 	{	
 * 		"rname" : ""
 * 	}
 *	]
 * }
 */
package com.fis.client.message;

import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ChatContent {
	private String name;
	private String content;
	private List<String> recv;
	
	public ChatContent(String jsonString){
		try{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
			
			this.name = (String) jsonObject.get("name");
			this.content = (String) jsonObject.get("content");
			JSONArray recvArr = (JSONArray) jsonObject.get("recv");
			recv = new Vector<String>();
			for(int i = 0; i< recvArr.size();++i){
				JSONObject jOb = (JSONObject) recvArr.get(i);
				recv.add((String)jOb.get("rname"));
			}
		}catch(ParseException e){
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getRecv() {
		return recv;
	}

	public void setRecv(List<String> recv) {
		this.recv = recv;
	}
}
