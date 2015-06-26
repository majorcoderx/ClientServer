package com.fis.client.message;

import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GroupAnalyst extends Analyst{
	
	public GroupAnalyst(String jsonString){
		super(jsonString);
	}

	public String getName() {
		return (String) jsonObj.get("name");
	}

	public String getContent() {
		return (String) jsonObj.get("content"); 
	}
	
	public String getNameGroup(){ 
		return (String) jsonObj.get("nameGroup");
	}
	
	public String typeGroup(){
		return (String) jsonObj.get("typeGroup"); 
	}
	
	public String getAction(){
		return (String) jsonObj.get("action"); 
	}
	
	public String getSender(){
		return (String) jsonObj.get("sender");
	}
	
	public List<String> getOnline() {
		JSONArray recvArr = (JSONArray) jsonObj.get("online");
		Vector<String> recv = new Vector<String>();
		for(int i = 0; i< recvArr.size();++i){
			JSONObject jOb = (JSONObject) recvArr.get(i);
			recv.add((String)jOb.get("name"));
		}
		return recv;
	}
}
