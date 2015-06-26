package com.fis.client.message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Analyst {
	/*
	 * {
	 * 	"type" :" acc/msg",
	 *   .........,
	 *   .........,
	 * }
	 * 
	 */ 
	public String type;
	protected JSONParser jsonParser;
	protected JSONObject jsonObj;
	
	public Analyst(String jsonString){
		try{
			jsonParser = new JSONParser();
			jsonObj = (JSONObject) jsonParser.parse(jsonString);
			type = (String) jsonObj.get("type");
		}catch(ParseException e){
			e.printStackTrace();
		}
	}
}
