package com.fis.client.message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TypeMsg {
	/*
	 * {
	 * 	"type" :" acc/msg",
	 *   .........,
	 *   .........,
	 * }
	 * 
	 */ 
	public static int getType(String jsonString){
		String type;
		try{
			JSONParser jsonParser = new JSONParser();
			JSONObject jOb = (JSONObject) jsonParser.parse(jsonString);
			type = (String) jOb.get("type");
		}catch(ParseException e){
			e.printStackTrace();
			return -1;
		}
		if(type.equals("result")){
			return 0;
		}
		if(type.equals("msg")){
			return 1;
		}
		if(type.equals("online")){
			return 2;
		}
		return -1;
	}
}
