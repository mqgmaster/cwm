package com.ufrgs.gerencia.agent;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static final String DATABASE  = "condominium";
	private static final String HOST	  = "localhost";
	private static final String PASS	  = "";
	private static final String USER	  = "root";
	private static final int    PORT      = 3306;
	
	private static String connectionStatus;
	
	
	public DBConnection() {
		// TODO: Empty implementation
	}
	
	public static Connection getMySQLConnection() {
		Connection con = null;
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver);
			String URI = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
			
			con = DriverManager.getConnection(URI, USER, PASS);
			if(con != null) {
				connectionStatus = "Connected";
			} else {
				connectionStatus = "Not Connected";
			}
			return con;
		} catch(ClassNotFoundException e) {
			System.err.println("Driver não encontrado: " + e);
			return null;
		} catch(SQLException e) {
			System.err.println("Conexão não estabelecida: " + e);
			return null;
		}
	}
	
	public static boolean closeMySQLConnection() {
		try {
			DBConnection.getMySQLConnection().close();
			return true;
		} catch(SQLException e) {
			return false;
		}
	}
}

