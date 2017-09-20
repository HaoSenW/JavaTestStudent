package com.mec.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MecDatabase {
	private PreparedStatement preparedStatement;
	private Connection connection;
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public MecDatabase(String SQLString) {
		try {
			this.connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/微易码科目管理系统",
					"root",
					"wenjing5211314");
			this.preparedStatement = connection.prepareStatement(SQLString);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet doQuery() throws SQLException {
		if(preparedStatement == null) {
			return null;
		}
		
		return preparedStatement.executeQuery();
	}
	
	public int doUpdate() throws SQLException {
		if(preparedStatement == null) {
			return -1;
		}
		
		return preparedStatement.executeUpdate();
	}
	
	public void exit() {
			try {
				if(preparedStatement != null) {
					preparedStatement.close();
					preparedStatement = null;
				}
				
				if(connection != null && connection.isClosed()) {
					connection.close();
					connection = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
		}
	}
}
