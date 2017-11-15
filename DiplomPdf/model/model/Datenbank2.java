package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Datenbank2 {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.postgresql.Driver";  
	static final String DB_URL = "jdbc:postgresql://localhost/diplomarbeit";

	//  Database credentials
	static final String USER = "postgres";
	static final String PASS = "password";

	static Connection conn = null;
	static Statement stmt = null;
	static PreparedStatement pstmt = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void VerbindungAufbauen()
	{
		System.out.println("Connecting to database...");
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connecting successful");
	}

}
