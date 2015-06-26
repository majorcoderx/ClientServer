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

public class ChatAnalyst extends Analyst{
	private String name;
	private String content;
	private List<String> recv;
	
	public ChatAnalyst(String jsonString){
		super(jsonString);
	}

	public String getName() {
		name = (String) jsonObj.get("name");
		return name;
	}

	public String getContent() {
		content = (String) jsonObj.get("content");
		return content;
	}

	public List<String> getRecv() {
		JSONArray recvArr = (JSONArray) jsonObj.get("recv");
		recv = new Vector<String>();
		for(int i = 0; i< recvArr.size();++i){
			JSONObject jOb = (JSONObject) recvArr.get(i);
			recv.add((String)jOb.get("rname"));
		}
		return recv;
	}
}
