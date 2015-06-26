package com.fis.client.message;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class OnlineAnalyst extends Analyst{
	public static Map<Integer, String> listOnl;
	
	public OnlineAnalyst(String jsonString){
		super(jsonString);
		JSONArray onlArr = (JSONArray) jsonObj.get("user");
		listOnl = new HashMap<Integer, String>();
		for(int i = 0; i< onlArr.size();++i){
			JSONObject jOb = (JSONObject) onlArr.get(i);
			listOnl.put(i, jOb.get("name").toString());
		}
	}
}
