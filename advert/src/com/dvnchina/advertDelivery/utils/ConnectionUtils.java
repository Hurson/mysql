package com.dvnchina.advertDelivery.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class ConnectionUtils {

private static Logger logger = Logger.getLogger(ConnectionUtils.class);
	
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	
	//创建连接
	public static Connection getConnection(){
		
		Connection connection = null;
		
		try {
			Class.forName(config.get("jdbc.driverClassName"));
			String url = config.get("jdbc.url");
			String user = config.get("jdbc.username");
			String password = config.get("jdbc.password");
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
		
	}
	
	//关闭连接
	public static void closedConnection(Connection connection , ResultSet result,PreparedStatement pstmt){
		
		if(result!=null){
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(pstmt!=null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
