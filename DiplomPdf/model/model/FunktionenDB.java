package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FunktionenDB {
	
	//Datenbank relevante Variablen
			// JDBC driver name and database URL
			static final String JDBC_DRIVER = "org.postgresql.Driver";  
			static final String DB_URL = "jdbc:postgresql://localhost/diplomarbeit";

			//  Database credentials
			static final String USER = "postgres";
			static final String PASS = "password";
			//private static String dbUrl = "jdbc:postgresql://localhost:5432/diplomarbeit?user=postgres&password=password";
			static Connection conn = null;
			static Statement stmt = null;
			static PreparedStatement pstmt = null;
			static ResultSet rs = null;
			
			//zum Testen
			private static String easyText;
			private static String easySuchwort2;
			private static String text="Das ist ein wunderschönes Haus, ich liebe dein Haus";
			private static String suchwort="Helm";
			private static String easySuchwort;
			private static boolean wert=false;
			private static int anzahl;
			private static String wort="haus";

	public static void main(String[] args) {
		
		//Stichtextgenerator(text);
		//VereinfachtesSuchwortgenerator(suchwort);
		fulltextsearch(text,wort);
		
	}
	
	
	public FunktionenDB() throws InstantiationException, IllegalAccessException{
		super();
		// DB Driver init
		try {
			Class.forName(JDBC_DRIVER);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException
	{
		Connection conn = null;
		//neuen Connection holen
		try {
			conn=DriverManager.getConnection(DB_URL);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		}
		return conn;
	}
	
	public void releaseConnection(Connection conn)
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Fehler beim schließen der Verbindung:");
			System.out.println("Meldung: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	//Methode zum generieren eines vereinfachten Text zur 
		public static String Stichtextgenerator(String wort) {
			//System.out.print("Das Wort"+text+"wurde vereinfacht zu "+EasyText+". ");

			String SEARCH_FOR_DATA_SQL_DATEN = "select to_tsvector(\'"+ wort +"\')";

			try {
				if(pstmt==null){
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
					//System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
					rs = pstmt.executeQuery();
					while (rs.next()) {

						easyText = rs.getString(1);
						System.out.print("Der Text '"+text+"' wurde vereinfacht zu '"+easyText+"'.");
						//System.out.println(easyText);

						System.out.println();
					}
				}

				rs.close();rs=null;
				pstmt.close();pstmt=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return easyText;
		}

		
		public static String VereinfachtesSuchwortgenerator(String wort) {
			/**
			 * Methode zur vereinfachung des Suchwortes und Speicherung in der Datenbank
			 * @param wort
			 * @return der Rückgabewert ist die vereinfachte Form des ursprünglichen Suchwortes
			 * 		   Hierbei werden alle Großbuchstaben durch Kleinbuchstaben ersetzt; Präpositionen,
			 * 		   Artikel, Personalpronomen,Nachsilben,.. fallen weg;
			 */
			String SEARCH_FOR_DATA_SQL_DATEN = "select to_tsvector(\'"+ wort +"\');";
			
			try {
				if(pstmt==null){
					conn = DriverManager.getConnection(DB_URL,USER,PASS);
					pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
					//System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
					rs = pstmt.executeQuery();
					while (rs.next()) {

						setEasySuchwort(rs.getString(1));
						System.out.print("Das Wort '"+suchwort+"' wurde vereinfacht zu '"+getEasySuchwort()+"'.");


						System.out.println();
					}
				}

					rs.close();rs=null;
					pstmt.close();pstmt=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return getEasySuchwort();
		}

		// TODO funktionert nit ganz
		//Überprüft ob angegebnes wort im text ist
		public static boolean fulltextsearch(String wort,String wort2) {
			ArrayList<String[]> daten = new ArrayList<String[]>();
			//Tabellenzeilen aus Datenbank einlesen
			String SEARCH_FOR_DATA_SQL_DATEN = "select (\'"+wort+"\')@@ to_tsquery(\'"+wort2+"\')";
			//System.out.print(easySuchwort);
			try {	
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					wert=rs.getBoolean(1);
					System.out.println("Ist das eingegebene Suchwort, "+wort2+", im Text?");
					System.out.print(wert);

					System.out.println();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return wert;
		}
		
		// TODO
		public void ranking()
		{
			
		}


		public static String getEasySuchwort() {
			return easySuchwort;
		}


		public static void setEasySuchwort(String easySuchwort) {
			FunktionenDB.easySuchwort = easySuchwort;
		}


		public static String getSuchwort() {
			return suchwort;
		}


		public static void setSuchwort(String suchwort) {
			FunktionenDB.suchwort = suchwort;
		}


	
	

}
