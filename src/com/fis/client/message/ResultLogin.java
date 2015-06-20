package com.fis.client.message;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ResultLogin {
	public boolean result;
	public ResultLogin(String jsonString){
		String resultString;
		try{
			JSONParser jsonParser = new JSONParser();
			JSONObject jOb = (JSONObject) jsonParser.parse(jsonString);
			
			resultString = (String) jOb.get("value");
			if(resultString.equals("true")){
				this.result = true;
			}
			else this.result = false;
		}catch(ParseException e){
			e.printStackTrace();
		}
	}
}
