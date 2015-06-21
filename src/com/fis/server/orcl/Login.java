package com.fis.server.orcl;

import java.sql.SQLException;

public class Login {
	
	public static synchronized boolean checkAccount(String acc,String pass){
		boolean check  = false;
		String sql = "select * from account where acc = '"+acc+"'";
		try{
			ConnectDB.rs =  ConnectDB.st.executeQuery(sql);
			while(ConnectDB.rs.next()){
				check = true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return check;
	}
}
