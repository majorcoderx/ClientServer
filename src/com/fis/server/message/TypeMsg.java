package com.fis.server.message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TypeMsg {
	/*
	 * {
	 * 	"type" :" acc/msg/disconnect",
	 *   .........,
	 *   .........,
	 * }
	 * 
	 */
	private static String type;
	public static synchronized int getType(String jsonString){
		try{
			JSONParser jsonParser = new JSONParser();
			JSONObject jOb = (JSONObject) jsonParser.parse(jsonString);
			type = (String) jOb.get("type");
		}catch(ParseException e){
			e.printStackTrace();
			return -1;
		}
		if(type.equals("acc")){
			return 0;
		}
		if(type.equals("msg")){
			return 1;
		}
		if(type.equals("disconnect")){
			return 2;
		}
		return -1;
	}
}
