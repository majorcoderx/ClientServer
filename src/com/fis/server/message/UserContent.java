/*
 * {
 * 	"type" : "",
 * 	"acc" : "",
 * 	"pass":""
 * } 
 * 
 */

package com.fis.server.message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserContent {
	private String acc;
	private String pass;
	public UserContent(String jsonString){
		try{
			JSONParser jsonParser = new JSONParser();
			JSONObject jOb = (JSONObject) jsonParser.parse(jsonString);
			
			this.acc = (String) jOb.get("acc");
			this.pass = (String) jOb.get("pass");
		}catch(ParseException e){
			e.printStackTrace();
		}
	}
	public String getAcc() {
		return acc;
	}
	public String getPass() {
		return pass;
	}	
}
