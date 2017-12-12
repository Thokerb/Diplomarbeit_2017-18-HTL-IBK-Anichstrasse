package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuslesenDB {

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

		
		public static void main(String[] args) {
			
			
			// TODO Auto-generated method stub
			/*
			try {
				AuslesenDB db = new AuslesenDB();
				Connection conn=db.getConnection();
				List<MeineTabelle> elemente = db.readMeineTabelle(conn);
				for(MeineTabelle e: elemente)
				{
					System.out.println(e);
				}
				db.releaseConnection(conn);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			}
			*/
			
			//readDaten();
			//readAutoren();
			//readDateinamen();
			//readTags();

		}
		
		public AuslesenDB() throws InstantiationException, IllegalAccessException{
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
				conn=DriverManager.getConnection(DB_URL,USER,PASS);
				
				
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
				System.out.println("Fehler beim schlieﬂen der Verbindung:");
				System.out.println("Meldung: "+e.getMessage());
				e.printStackTrace();
			}
		}
		
		
		//Auslesen aller Daten in der Tabelle Uploaddaten
		public static ArrayList<String[]> readDaten() {
			//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
			ArrayList<String[]> daten = new ArrayList<String[]>();
			//SQL-Abfrage
			String READ_DATA_SQL_DATEN = "SELECT language, tag, blobdatei, stichworttext, inhalttext, uploader, autor, dateiname,uploadid,uploaddatum FROM uploaddaten";
			//opens a connection, 
			try { 
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				pstmt = conn.prepareStatement(READ_DATA_SQL_DATEN);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					String[] zeile = new String[10];
					System.out.print("Gelesen wurde: ");
					for (int i = 0; i < 10; i++) {
						zeile[i] = rs.getString(i+1);
						System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
					}
					daten.add(zeile);
					System.out.println();
				}
				rs.close(); rs=null;
				pstmt.close(); pstmt=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return daten;
		}


		public static ArrayList<String[]> readAutoren() {
			//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
			ArrayList<String[]> Autoren = new ArrayList<String[]>();
			//SQL-Abfrage
			String READ_DATA_SQL_AUTOREN = "select autor from uploaddaten group by autor";
			try { 
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				pstmt = conn.prepareStatement(READ_DATA_SQL_AUTOREN);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					String[] spalten = new String[1];
					System.out.print("Gelesen wurde: ");
					spalten[0] = rs.getString(1);

					Autoren.add(spalten);
					System.out.println(" '" + spalten[0] + "'");	//zur Kontrolle
				}
				rs.close(); rs=null;
				pstmt.close(); pstmt=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return Autoren;

		}

		public static ArrayList<String[]> readDateinamen() {

			ArrayList<String[]> dateinamen = new ArrayList<String[]>();
			//SQL-Abfrage
			String READ_DATA_SQL_DATEINAMEN = "select dateiname from uploaddaten group by dateiname";
			try {
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				pstmt = conn.prepareStatement(READ_DATA_SQL_DATEINAMEN);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					String[] spalten = new String[1];
					System.out.print("Gelesen wurde: ");
					spalten[0] = rs.getString(1);

					dateinamen.add(spalten);
					System.out.println(" '" + spalten[0] + "'");	//zur Kontrolle
				}
				rs.close(); rs=null;
				pstmt.close(); pstmt=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return dateinamen;

		}

		public static ArrayList<String[]> readTags() {

			ArrayList<String[]> Tags = new ArrayList<String[]>();
			//SQL-Abfrage
			String READ_DATA_SQL_AUTOREN = "select tag from uploaddaten group by tag";
			try {
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				pstmt = conn.prepareStatement(READ_DATA_SQL_AUTOREN);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					String[] spalten = new String[1];
					System.out.print("Gelesen wurde: ");
					spalten[0] = rs.getString(1);

					Tags.add(spalten);
					System.out.println(" '" + spalten[0] + "'");	//zur Kontrolle
				}
				
				rs.close(); rs=null;
				pstmt.close(); pstmt=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return Tags;

		}
		
		public List<Benutzer> readBenutzer (Connection conn) throws SQLException
		{
			ArrayList<Benutzer> daten = new ArrayList<>();
			String SQL="select * from data";
			
			try {
				PreparedStatement pstmt=conn.prepareStatement(SQL);
				ResultSet rs=pstmt.executeQuery();
				while(rs.next())
				{
					String benutzername=rs.getString(1);
					String email=rs.getString(2);
					String vorname=rs.getString(3);
					String nachname=rs.getString(4);
					int passwort=rs.getInt(5);
					
					Benutzer zeile = new Benutzer(benutzername,email,vorname,nachname,passwort);
					daten.add(zeile);
				}
				rs.close(); rs=null;
				pstmt.close(); pstmt=null;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return daten;
		}
}
