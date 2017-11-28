package model;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Datenbank {
	//Datenbank relevante Variablen
	private static String dbUrl = "jdbc:postgresql://localhost:5432/diplomarbeit?user=postgres&password=password";
	static Connection conn = null;
	static Statement stmt = null;
	static PreparedStatement pstmt = null;
	static ResultSet resultSet = null;

	//Variable relevant für Methoden
	private static String easyText;
	private static String text="Das ist ein wunderschönes Haus, ich liebe dein Haus";
	private static String suchwort;
	private static String easySuchwort="Helm";
	private static boolean wert;
	
	//Zum Austesten
	private static ArrayList<String[]> liste = new ArrayList<String[]>();
	static Date currentDate = Calendar.getInstance().getTime();
	private static String[] testzeile = new String[]{"Nachhilfe","fhjdskfhdsuifhusdifhdshfisfhjisd fjdisfjhsidf sfjidsofhisd fjdisofhjisdo hdisofhsid","Verena","Verena","Super cooler Text", "'2017-11-25'"};

	
	public static void main(String[] args) {
		
		
		System.out.println(currentDate);
		
		//readDaten();
		//readDaten2();
		readAutoren();
		//readDateinamen();
		//readTags();
		//writeDaten(testzeile);
		//Stichworttextgenerator(text);
		//fulltextsearch(easySuchwort,easySuchwort);
		//Stichworttextgenerator(text);
		

		//fuellen der Liste mit Daten
		//liste.add(testzeile);

	}
	

	//Auslesen aller Daten in der Tabelle Uploaddaten
	public static ArrayList<String[]> readDaten() {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//SQL-Abfrage
		String READ_DATA_SQL_DATEN = "SELECT language, tag, blobdatei, stichworttext, inhalttext, uploader, autor, dateiname,uploadid FROM uploaddaten";
		//opens a connection, 
		try (Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(READ_DATA_SQL_DATEN);
				ResultSet resultSet = pStatement.executeQuery()) {
			while (resultSet.next()) {
				String[] zeile = new String[9];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 9; i++) {
					zeile[i] = resultSet.getString(i+1);
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

		ArrayList<String[]> Autoren = new ArrayList<String[]>();

		String READ_DATA_SQL_AUTOREN = "select autor from uploaddaten group by autor";
		try (Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(READ_DATA_SQL_AUTOREN);
				ResultSet resultSet = pStatement.executeQuery()) {

			while (resultSet.next()) {
				String[] spalten = new String[1];
				System.out.print("Gelesen wurde: ");
				spalten[0] = resultSet.getString(1);

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

		String READ_DATA_SQL_DATEINAMEN = "select dateiname from uploaddaten group by dateiname";
		try (Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(READ_DATA_SQL_DATEINAMEN);
				ResultSet resultSet = pStatement.executeQuery()) {

			while (resultSet.next()) {
				String[] spalten = new String[1];
				System.out.print("Gelesen wurde: ");
				spalten[0] = resultSet.getString(1);

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

		String READ_DATA_SQL_AUTOREN = "select tag from uploaddaten group by tag";
		try (Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(READ_DATA_SQL_AUTOREN);
				ResultSet resultSet = pStatement.executeQuery()) {

			while (resultSet.next()) {
				String[] spalten = new String[1];
				System.out.print("Gelesen wurde: ");
				spalten[0] = resultSet.getString(1);

				Tags.add(spalten);
				System.out.println(" '" + spalten[0] + "'");	//zur Kontrolle
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Tags;

	}

	// TODO fehler bei  uploaddatum
	//Neue Daten in Datebantabelle schreiben.
	public static boolean writeDaten(String[] testzeile2) {

		boolean erfolg = true;
		//SQL-Abfrag zum hineinschreiben neuer Daten
		String INSERT_DATA_SQL = "INSERT INTO uploaddaten ( tag, inhalttext, uploader, autor, dateiname, uploaddatum) VALUES (?, ?, ?, ?, ?, ?)";
		
		//connection Aufbau
		try (
				Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(INSERT_DATA_SQL);) {

			System.out.println("HIIIIIII");

			for (int i = 1; i <= 6; i++) {
				pStatement.setString(i, testzeile[i-1]);
				System.out.println(" '" + testzeile[i-1] + "'");
			}
			pStatement.executeUpdate();

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
		try {	conn = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
				resultSet = pStatement.executeQuery();
			while (resultSet.next()) {
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
				conn = DriverManager.getConnection(dbUrl);
				pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
				//System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) {

					easyText = resultSet.getString(1);
					System.out.print("Das Wort '"+text+"' wurde vereinfacht zu '"+easyText+"'h.");
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

		String SEARCH_FOR_DATA_SQL_DATEN = "select to_tsvector(\'"+ wort +"\')";

		try {
			if(pstmt==null){
				conn = DriverManager.getConnection(dbUrl);
				pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
				//System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
				resultSet = pstmt.executeQuery();
				while (resultSet.next()) {

					easySuchwort = resultSet.getString(1);
					System.out.print("Das Wort '"+suchwort+"' wurde vereinfacht zu '"+easySuchwort+"'h.");


					System.out.println();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return easySuchwort;
	}


}
