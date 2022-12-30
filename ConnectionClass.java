package com.ecommerse.project;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass 
{
	
	private Connection connection =null;
	public Connection getConnection()
	{
	try
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/e_commers","root","0123456789");
		//System.out.println("succ");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	return connection;
	
	}
	
	

}
