package com.fis.server.message;

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
	private static String type;
	public static int getType(String jsonString){
		try{
			JSONParser jsonParser = new JSONParser();
			JSONObject jOb = (JSONObject) jsonParser.parse(jsonString);
			type = (String) jOb.get("type");
		}catch(ParseException e){
			e.printStackTrace();
		}
		if(type.equals("msg")){
			return 1;
		}
		else if(type.equals("acc")){
			return 0;
		}
		else{
			return 2;
		}
	}
}
