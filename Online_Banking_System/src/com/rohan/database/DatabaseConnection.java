package com.rohan.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
	public Connection database() throws SQLException,ClassNotFoundException
	{
		String path = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "Rohan7";
		String pass = "Rohan001";
		
		Class.forName(path);
		Connection con = DriverManager.getConnection(url,user,pass);
		return con; 
	}
}
