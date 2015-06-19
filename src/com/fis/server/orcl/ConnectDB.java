package com.fis.server.orcl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
	public static Connection conn;
	public static Statement st;
	public static ResultSet rs;
	
	public ConnectDB(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:orcl","scott","tiger");
			st = conn.createStatement();
		}catch(ClassNotFoundException ex){
			ex.printStackTrace();
		}catch(SQLException ex){
			ex.printStackTrace();
		}
	}
}
