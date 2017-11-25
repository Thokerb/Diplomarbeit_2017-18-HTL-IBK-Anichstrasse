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
	static ResultSet rs = null;

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
	
	public static ArrayList<String[]> AlleDatenAuslesen()
	{
		System.out.println("Folgende Daten sind in der Tabelle Uploaddaten");
		String SQL_READ_DATEN="select * from Uploaddaten";
		
		try {
			stmt=conn.prepareStatement(SQL_READ_DATEN);
			rs=stmt.executeQuery(SQL_READ_DATEN);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public static ArrayList<String[]> readDaten2() {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//SQL-Abfrage
		String READ_DATA_SQL_DATEN = "SELECT language, tag, blobdatei, stichworttext, inhalttext, uploader, autor, dateiname,uploadid FROM uploaddaten";
		//opens a connection, 
		try {
			pstmt = conn.prepareStatement(READ_DATA_SQL_DATEN);
			//resultSet = pstmt.executeQuery(){
			
			while (rs.next()) {
				String[] zeile = new String[9];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 9; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				daten.add(zeile);
				System.out.println();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return daten;
	}

}
