package com.fis.client.message;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OnlineUser {
	
	private Map<Integer, String> listOnl;
	
	public OnlineUser(String jsonString){
		
		try{
			JSONParser jsonParser = new JSONParser();
			JSONObject jOb = (JSONObject) jsonParser.parse(jsonString);
			
			
		}catch(ParseException e){
			e.printStackTrace();
		}
	}
}
