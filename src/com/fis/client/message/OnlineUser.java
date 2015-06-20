package com.fis.client.message;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OnlineUser {
	public static Map<Integer, String> listOnl;
	
	public OnlineUser(String jsonString){
		
		try{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonString);
			
			JSONArray onlArr = (JSONArray) jsonObject.get("user");
			listOnl = new HashMap<Integer, String>();
			for(int i = 0; i< onlArr.size();++i){
				JSONObject jOb = (JSONObject) onlArr.get(i);
				listOnl.put(i, jOb.get("name").toString());
			}
		}catch(ParseException e){
			e.printStackTrace();
		}
	}
}
