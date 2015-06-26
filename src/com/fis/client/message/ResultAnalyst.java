package com.fis.client.message;

public class ResultAnalyst extends Analyst{
	public boolean result;
	public ResultAnalyst(String jsonString){
		super(jsonString);
		String resultString;
		resultString = (String) jsonObj.get("value");
		if (resultString.equals("true")) {
			this.result = true;
		} else
			this.result = false;
	}
}
