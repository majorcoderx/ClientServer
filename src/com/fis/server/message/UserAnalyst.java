/*
 * {
 * 	"type" : "",
 * 	"acc" : "",
 * 	"pass":""
 * } 
 * 
 */

package com.fis.server.message;

public class UserAnalyst extends Analyst {
	private String acc;
	private String pass;
	public UserAnalyst(String jsonString){
		super(jsonString);
	}
	public String getAcc() {
		acc = (String) jsonObj.get("acc");
		return acc;
	}
	public String getPass() {
		pass = (String) jsonObj.get("pass");
		return pass;
	}	
}
