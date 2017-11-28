package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;

public class Datenbank3 {

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

	//Variable relevant für Methoden
	private static String easyText;
	private static String text="Das ist ein wunderschönes Haus, ich liebe dein Haus";
	private static String suchwort="Helm";
	private static String easySuchwort;
	private static boolean wert;

	//Zum Austesten
	private static ArrayList<String[]> liste = new ArrayList<String[]>();
	static Date currentDate = Calendar.getInstance().getTime();
	private static String[] testzeile = new String[]{"Nachhilfe","fhjdskfhdsuifhusdifhdshfisfhjisd fjdisfjhsidf sfjidsofhisd fjdisofhjisdo hdisofhsid","Verena","Verena","Super cooler Text", "'2017-11-25'"};


	public static void main(String[] args) {


		System.out.println(currentDate);

		VerbindungAufbauen();
		//readDaten();
		//readDaten2();
		//readAutoren();
		//readDateinamen();
		//readTags();
		//writeDaten(testzeile);
		Stichworttextgenerator(text);
		fulltextsearch(suchwort,suchwort);
		VereinfachtesSuchwortgenerator(suchwort);
		
		autorASC();


		//fuellen der Liste mit Daten
		//liste.add(testzeile);

	}


	public static void VerbindungAufbauen()
	{
		System.out.println("Connecting to database...");
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Connecting successful");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connecting nicht successful");
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Tags;

	}

	// TODO fehler bei  uploaddatum, kann nicht hineingeschrieben werden
	//Neue Daten in Datebantabelle schreiben.
	public static boolean writeDaten(String[] testzeile2) {

		boolean erfolg = true;
		//SQL-Abfrag zum hineinschreiben neuer Daten
		String INSERT_DATA_SQL = "INSERT INTO uploaddaten ( tag, inhalttext, uploader, autor, dateiname, uploaddatum) VALUES (?, ?, ?, ?, ?, ?)";

		//connection Aufbau
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);

			//System.out.println("HIIIIIII"); //zur Kontrolle

			for (int i = 1; i <= 6; i++) {
				pstmt.setString(i, testzeile[i-1]);
				System.out.println(" '" + testzeile[i-1] + "'");
			}
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			erfolg = false;
		}
		return erfolg;

	}
	/*
		public static boolean writeDaten(ArrayList<String[]> eintraege) {

			boolean erfolg = true;
			//SQL-Abfrag zum hineinschreiben neuer Daten
			String INSERT_DATA_SQL = "INSERT INTO uploaddaten (language, tag, blobdatei, stichworttext, inhalttext, uploader, autor, dateiname,uploadid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			//connection Aufbau
			try (
					Connection connection = DriverManager.getConnection(dbUrl);
					PreparedStatement pStatement = connection.prepareStatement(INSERT_DATA_SQL);) {
					for (String[] zeile  : eintraege) {

					for (int i = 1; i <= 9; i++) {
						pStatement.setString(i, zeile[i-1]);
					}
					pStatement.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				erfolg = false;
			}
			return erfolg;
		}
	 */

	// TODO funktionert nit ganz
	//Überprüft ob angegebnes wort im text ist
	public static boolean fulltextsearch(String wort,String wort2) {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "select (\'"+wort+"\')@@ to_tsquery(\'"+wort2+"\')";
		try {	
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			PreparedStatement pStatement = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pStatement.executeQuery();
			while (rs.next()) {
				System.out.println("Ist das eigengebene Suchtwort, "+easySuchwort+" im Text?");
				System.out.print(wert);

				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wert;
	}

	//Methode zum generieren eines vereinfachten Text zur 
	public static String Stichworttextgenerator(String wort) {
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

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return easyText;
	}

	public static String VereinfachtesSuchwortgenerator(String wort) {
		//System.out.print("Das Wort"+text+"wurde vereinfacht zu "+EasyText+". ");
		//pstmt=null;
		String SEARCH_FOR_DATA_SQL_DATEN = "select to_tsvector(\'"+ wort +"\');";

		System.out.println("Test");
		
		try {
			//if(pstmt==null){
				conn = DriverManager.getConnection(DB_URL,USER,PASS);
				pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
				System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
				rs = pstmt.executeQuery();
				while (rs.next()) {

					easySuchwort = rs.getString(1);
					System.out.print("Das Wort '"+suchwort+"' wurde vereinfacht zu '"+easySuchwort+"'.");


					System.out.println();
				}
			//}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return easySuchwort;
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				for (int i = 0; i < 10; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatennachAutorDESC.add(zeile);
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DatennachAutorDESC;
		
	}
	
	public static ArrayList<String[]> uploaddatumASC()
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatenuploaddatumASC = new ArrayList<String[]>();
		
		//SQL-Abfrage
		String READ_DATEN_UPLOADDATUMASC="select dateiname, autor, tag, uploaddatum from uploaddaten order by Autor ASC";
		
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DatenuploaddatumASC;
		
	}

	
	public static ArrayList<String[]> uploaddatumDESC()
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatenUploaddatumDESC = new ArrayList<String[]>();
		ArrayList<String> list = new ArrayList<String>();
		//SQL-Abfrage
		String READ_DATEN_UPLOADDATUMDESC="select dateiname, autor, tag, uploaddatum from uploaddaten order by Autor DESC";
		
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_UPLOADDATUMDESC);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 10; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatenUploaddatumDESC.add(zeile);
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DatenUploaddatumDESC;
		//Gson gson = new Gson();
		
		//String jsonCartList = gson.toJson(DatenUploaddatumDESC);
		
		
	}

}
