package com.fis.server.message;

public class GroupAnalyst extends Analyst{

	private String nameGroup;
	
	public GroupAnalyst(String jsonString) {
		super(jsonString);
	}
	
	public String getNameGroup(){
		nameGroup = (String) jsonObj.get("nameGroup");
		return nameGroup;
	}
	
	public String typeGroup(){ //change/message
		return (String) jsonObj.get("typeGroup");
	}
	
	public String getAction(){ //add/remove
		return (String) jsonObj.get("action");
	}
	
	public String getMessage(){
		return (String) jsonObj.get("content");
	}
	
	public String getName(){
		return (String) jsonObj.get("name");
	}
	
	public String getSender(){
		return (String) jsonObj.get("sender");
	}
}
