package Model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Model.DAO.ConnectDatabase;

public class ConnectDatabase {
private static Connection instance = null;
	
	private ConnectDatabase() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (ClassNotFoundException e) {
			System.out.println("Catch class :" + e.getMessage());
			e.printStackTrace();
		}
		
		try {
			instance = DriverManager.getConnection(InfractionConnection.connectionString, InfractionConnection.userName, InfractionConnection.password);
		} 
		catch (SQLException e) {
			System.out.println("Catch SQL " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Connection getInstance() {
		if(instance == null){
			new ConnectDatabase();
		}
		return instance;
	}	
}
