/*
 * {
 * 	"type" :" acc/msg/disconnect",
 *   .........,
 *   .........,
 * }
 * 
 */
//private static String type;

package com.fis.server.message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Analyst {
	
	protected JSONParser jsonParser;
	protected JSONObject jsonObj;
	
	public String type;
	
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
