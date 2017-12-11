package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class geordneteAusgabe {

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
	
	//Variablen relevant für Methoden
	private static int anzahl;

	public static void main(String[] args) {
		//autorASC();
		//autorDESC();
		
		//uploaddatumASC();
		//uploaddatumDESC();
		
		dateinameASC();
		dateinameDESC();
	
		//AnzahlEinträge();
	}


	public geordneteAusgabe() throws InstantiationException, IllegalAccessException{
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
	
	
	public static ArrayList<String[]> autorASC()
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachAutorASC = new ArrayList<String[]>();
		
		//SQL-Abfrage
		String READ_DATEN_AUTORASC="select dateiname, autor, tag, uploaddatum from uploaddaten order by Autor ASC";
		
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_AUTORASC);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 4; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatennachAutorASC.add(zeile);
				System.out.println();
			}
			
			//System.out.println(DatennachAutorASC.get(0)[0]);
			/*
			for(int i=0;i<=3;i++)
			{
				System.out.println(DatennachAutorASC.get(i)[0]);
				System.out.println(DatennachAutorASC.get(i)[1]);
				System.out.println(DatennachAutorASC.get(i)[2]);
				System.out.println(DatennachAutorASC.get(i)[3]);
			}*/
			
			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return DatennachAutorASC;
		
	}
	
	public static ArrayList<String[]> autorDESC()
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachAutorDESC = new ArrayList<String[]>();
		
		//SQL-Abfrage
		String READ_DATEN_AUTORDESC="select dateiname, autor, tag, uploaddatum from uploaddaten order by Autor DESC";
		
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_AUTORDESC);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 4; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatennachAutorDESC.add(zeile);
				System.out.println();
			}
			/*
			for(int i=0;i<=3;i++)
			{
				System.out.println(DatennachAutorDESC.get(i)[0]);
				System.out.println(DatennachAutorDESC.get(i)[1]);
				System.out.println(DatennachAutorDESC.get(i)[2]);
				System.out.println(DatennachAutorDESC.get(i)[3]);
			}
			*/
			
			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return DatennachAutorDESC;
		
	}
	
	// TODO wieso gleich wie DESC
	public static ArrayList<String[]> uploaddatumASC()
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatenuploaddatumASC = new ArrayList<String[]>();
		
		//SQL-Abfrage
		String READ_DATEN_UPLOADDATUMASC="select dateiname, autor, tag, uploaddatum from uploaddaten order by uploaddatum ASC";
		
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_UPLOADDATUMASC);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 4; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatenuploaddatumASC.add(zeile);
				System.out.println();
			}
			
			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return DatenuploaddatumASC;
		
	}

	
	public static ArrayList<String[]> uploaddatumDESC()
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatenUploaddatumDESC = new ArrayList<String[]>();
		//ArrayList<String> list = new ArrayList<String>();
		//SQL-Abfrage
		String READ_DATEN_UPLOADDATUMDESC="select dateiname, autor, tag, uploaddatum from uploaddaten order by uploaddatum DESC";
		
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_UPLOADDATUMDESC);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 4; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatenUploaddatumDESC.add(zeile);
				System.out.println();
			}
			
			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return DatenUploaddatumDESC;
	}
	
	// TODO wieso gleich wie DESC
	public static ArrayList<String[]> dateinameASC()
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachDateinameASC = new ArrayList<String[]>();
		
		//SQL-Abfrage
		String READ_DATEN_DATEINAMEASC="select dateiname, autor, tag, uploaddatum from uploaddaten order by dateiname ASC";
		
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_DATEINAMEASC);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 4; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatennachDateinameASC.add(zeile);
				System.out.println();
			}
			
			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return DatennachDateinameASC;
		
	}
	
	public static ArrayList<String[]> dateinameDESC()
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachDateinameDESC = new ArrayList<String[]>();
		
		//SQL-Abfrage
		String READ_DATEN_DATEINAMEDESC="select dateiname, autor, tag, uploaddatum from uploaddaten order by dateiname DESC";
		
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_DATEINAMEDESC);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 4; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatennachDateinameDESC.add(zeile);
				System.out.println();
			}
			
			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return DatennachDateinameDESC;
		
	}
	
	public static int AnzahlEinträge()
	{
		String SQL="select count(uploadid) from uploaddaten";
		
		try {
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				anzahl=rs.getInt(1);
				System.out.println(anzahl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return anzahl;
	}
	
	// TODO 
	public static void Ranking()
	{
		
	}
	
	

}
