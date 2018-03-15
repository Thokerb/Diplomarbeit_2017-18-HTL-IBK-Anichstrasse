package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.Part;

public class DBManager {

	//Datenbank relevante Variablen
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "org.postgresql.Driver";  
	static final String DB_URL = "jdbc:postgresql://localhost/diplomarbeit";

	//  Database credentials
	static final String USER = "postgres";
	static final String PASS = "password";
	//private static String dbUrl = "jdbc:postgresql://localhost:5432/diplomarbeit?user=postgres&password=password";
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;

	//Variablen
	private static boolean wert=false;
	private static int anzahl;
	private static float relevanz;
	private static String easyText;
	private static String easySuchwort;
	private static String easySuchwort2;

	public static void main(String[] args) {
		//Stichtextgenerator("Wort");
		//String wort="Skellette";
		//VereinfachtesSuchwortgenerator(wort);
		//writeStichwörter(wort);

		//fulltextsearch(easyText,easySuchwort2);

		//ranking2("Zwiebel");
		//readDaten(conn);

		//autorASC(conn);

		//PasswortNeuSetzen("Verena","mypassword1");

		//writeDaten(String[] testzeile2, Part filePart, Date date)

		//Datenlöschen(61);

		//PasswortNeuSetzen("Verena", "passwoert12");

		//UpdateStatus(66,"public");

		//Datenlöschen(64);
	}

	public DBManager() throws InstantiationException, IllegalAccessException{
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
			//e.printStackTrace();
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return conn;
	}

	public void releaseConnection(Connection conn)
	{
		//Connection löschen
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Fehler beim schließen der Verbindung:");
			System.out.println("Meldung: "+e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * Hier werden die Methoden zum Schreiben von Daten in die Datenbank
	 * @param testzeile2
	 * @return
	 */
	public static boolean writeDaten(Connection conn,String[] testzeile2, Part filePart, String date){

		InputStream fis;
		String entscheidungshilfe=null;

		boolean erfolg = true;
		//SQL-Abfrag zum hineinschreiben neuer Daten
		String INSERT_DATA_SQL = "INSERT INTO uploaddaten (tag, inhalttext, uploader, autor, dateiname, stichworttext, dateityp, status, dokumentdatum, uploaddatum, blobdatei) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

		//connection Aufbau
		try {
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
			fis = filePart.getInputStream();

			pstmt.setString(1, testzeile2[0]);
			pstmt.setString(2, testzeile2[1]);
			pstmt.setString(3, testzeile2[2]);
			pstmt.setString(4, testzeile2[3]);
			pstmt.setString(5, testzeile2[4]);
			pstmt.setString(6, testzeile2[5]);
			pstmt.setString(7, testzeile2[6]);
			pstmt.setString(8, "private");
			pstmt.setString(9, date);
			pstmt.setString(10, getaktuellesDatum());
			pstmt.setBinaryStream(11, fis, (int)filePart.getSize());
			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
			erfolg = false;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return erfolg;

	}
	
	public boolean writeDaten(Connection conn,Daten testzeile2){

		boolean erfolg = true;
		//SQL-Abfrag zum hineinschreiben neuer Daten
		String INSERT_DATA_SQL = "INSERT INTO uploaddaten (dateiname, autor, uploader, inhalttext, stichworttext, tag, dateityp, uploaddatum, dokumentdatum, status, blobdatei) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

		//connection Aufbau
		try {
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);

			pstmt.setString(1, testzeile2.getDateiname());
			pstmt.setString(2, testzeile2.getAutor());
			pstmt.setString(3, testzeile2.getUploader());
			pstmt.setString(4, testzeile2.getInhalttext());
			pstmt.setString(5, testzeile2.getStichworttext());
			pstmt.setString(6, testzeile2.getTag());
			pstmt.setString(7, testzeile2.getDateityp());
			pstmt.setString(8, testzeile2.getUploaddatum());
			pstmt.setString(9, testzeile2.getDokumentdatum());
			pstmt.setString(10, testzeile2.getStatus());
			pstmt.setBytes(11, testzeile2.getBlobdatei());


			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
			erfolg = false;
		}
		return erfolg;

	}
	
	//TODO
	public boolean writegeloeschteDaten(Connection conn,Daten testzeile2){

		String entscheidungshilfe=null;

		boolean erfolg = true;
		//SQL-Abfrag zum hineinschreiben neuer Daten
		String INSERT_DATA_SQL = "INSERT INTO geloeschtedaten (dateiname, autor, uploader, inhalttext, stichworttext, tag, dateityp, uploaddatum, dokumentdatum, status, blobdatei, deletedatum) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";

		
		String date = getaktuellesDatum();
		
		//connection Aufbau
		try {
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
			pstmt.setString(1, testzeile2.getDateiname());
			pstmt.setString(2, testzeile2.getAutor());
			pstmt.setString(3, testzeile2.getUploader());
			pstmt.setString(4, testzeile2.getInhalttext());
			pstmt.setString(5, testzeile2.getStichworttext());
			pstmt.setString(6, testzeile2.getTag());
			pstmt.setString(7, testzeile2.getDateityp());
			pstmt.setString(8, testzeile2.getUploaddatum());
			pstmt.setString(9, testzeile2.getDokumentdatum());
			pstmt.setString(10, testzeile2.getStatus());
			pstmt.setBytes(11, testzeile2.getBlobdatei());
			pstmt.setString(12, date);
			
			System.out.println("Testzeile: "+testzeile2);
			
			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
			erfolg = false;
		}
		return erfolg;

	}

	public static String getaktuellesDatum(){
		
		GregorianCalendar now = new GregorianCalendar(); 

		SimpleDateFormat d = new SimpleDateFormat("dd.MM.yyyy");
		String pD = d.format(now.getTime());
		System.out.println(pD);

		return pD;
	}

	//TODO was wollt i mit der Methode?
	public String Suchwort(String name){
		easySuchwort2 = name;
		easySuchwort2 = name.substring(0,name.length()-2);
		System.out.println(easySuchwort2);

		return easySuchwort2;
	}


	public void writeStichwörter(Connection conn,String wort)
	{
		String SQL2="Insert into suchwoerter (Suchwort) VALUES (?)";
		//String SQL2="Insert into verwendSuchwort (Suchwort) VALUES (?) ON DUPLICATE KEY UPDATE 'suchwort' = 'suchwort';";

		try {
			pstmt=conn.prepareStatement(SQL2);

			pstmt.setString(1, wort);
			System.out.println(" '" +wort+"'");
			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public void convertDate(String s){
		// Print out the inserted string
		System.out.println(s);

		// Split the String into year, month and date
		// and save it into an array
		String arrayString[] = s.split("-");
		for(int i = 0; i < arrayString.length; i++){
			System.out.println(arrayString[i]);
		}

		//Only for out-printing because it is only the format 
		// in which it is shown
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		System.out.println(sdf);

		// Creating the Integer values of the date
		// and setting them to the values saved in
		// the array
		int year = Integer.parseInt(arrayString[2]);
		int month = Integer.parseInt(arrayString[0]);
		int day = Integer.parseInt(arrayString[1]);

		// Output to check the values are set correctly
		System.out.println(year);
		System.out.println(month);
		System.out.println(day);

		// Creating a calendar object
		// and setting the year, day and month
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); // <-- months start at 0.
		cal.set(Calendar.DAY_OF_MONTH, day);

		// Converting 
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());

		// Only output to check if everything worked correctly
		System.out.println(sdf.format(date));

	}


	/**
	 * Hier werden die Methoden zur geordneten Reihenfolge für die Antwort in den DataTableServlet geschrieben und andere SELECT Abfragen
	 * @return
	 */

	public ArrayList<String[]> meineDaten(Connection conn,String sortierdings,String spalte,String reihung)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachAutorASC = new ArrayList<String[]>();

		//SQL-Abfrage
		String READ_DATEN_AUTORASC="select uploadid,dateityp, dateiname, autor, dokumentdatum, uploaddatum, status from uploaddaten where uploader='"+sortierdings+"' order by "+spalte+" "+ reihung+";";

		System.out.println(READ_DATEN_AUTORASC);
		try {
			pstmt = conn.prepareStatement(READ_DATEN_AUTORASC);
			rs = pstmt.executeQuery();
			System.out.println("yoo");
			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 7; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatennachAutorASC.add(zeile);
				System.out.println();
			}

			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return DatennachAutorASC;

	}
	
	public ArrayList<String[]> publicDaten(Connection conn,String spalte,String reihung)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachAutorASC = new ArrayList<String[]>();

		//SQL-Abfrage
		String READ_DATEN_AUTORASC="select uploadid,dateityp, dateiname, uploader, autor, uploaddatum, dokumentdatum, status from uploaddaten where status='public' order by "+spalte+" "+ reihung+";";

		System.out.println(READ_DATEN_AUTORASC);
		try {
			pstmt = conn.prepareStatement(READ_DATEN_AUTORASC);
			rs = pstmt.executeQuery();
			System.out.println("yoo");
			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 8; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatennachAutorASC.add(zeile);
				System.out.println();
			}

			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Wird des ausgeführt");
		return DatennachAutorASC;

	}
	
	public ArrayList<String[]> geloeschteDaten(Connection conn,String sortierdings,String spalte,String reihung)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachAutorASC = new ArrayList<String[]>();

		//SQL-Abfrage
		String READ_DATEN_AUTORASC="select loeschid, dateityp, dateiname, autor, deletedatum, uploaddatum, dokumentdatum from geloeschtedaten where uploader='"+sortierdings+"' order by "+spalte+" "+ reihung+";";

		System.out.println(READ_DATEN_AUTORASC);
		try {
			pstmt = conn.prepareStatement(READ_DATEN_AUTORASC);
			rs = pstmt.executeQuery();
			System.out.println("yoo");
			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 7; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				DatennachAutorASC.add(zeile);
				System.out.println();
			}

			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return DatennachAutorASC;

	}
	
	public Daten readgeloeschteDatei(Connection conn,int id)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		Daten uploaddaten = new Daten();

		//SQL-Abfrage
		String READ_DATEN_AUTORASC="select dateiname, autor, uploader, inhalttext, stichworttext, dateityp, status, tag, uploaddatum, dokumentdatum, blobdatei, deletedatum from geloeschtedaten where loeschid='"+id+"';";

		System.out.println(READ_DATEN_AUTORASC);
		try {
			pstmt = conn.prepareStatement(READ_DATEN_AUTORASC);
			rs = pstmt.executeQuery();
			System.out.println("yoo");
			while(rs.next())
			{
				String dateiname=rs.getString(1);
				String autor=rs.getString(2);
				String uploader=rs.getString(3);
				String inhalttext=rs.getString(4);
				String stichworttext=rs.getString(5);
				String dateityp=rs.getString(6);
				String status=rs.getString(7);
				String tag=rs.getString(8);
				String uploaddatum=rs.getString(9);
				String dokumentdatum=rs.getString(10);
				byte[] blobdatei=rs.getBytes(11);
				String deletedatum=rs.getString(12);
				
				uploaddaten =new Daten(dateiname,autor,uploader,inhalttext,stichworttext,dateityp,status,tag,uploaddatum,dokumentdatum,deletedatum,blobdatei);
				
				System.out.println(uploaddaten);
			}

			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return uploaddaten;

	}
	
	public Daten readzuloeschendeDatei(Connection conn,int id)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		Daten uploaddaten = new Daten();

		//SQL-Abfrage
		String READ_DATEN_AUTORASC="select dateiname, autor, uploader, inhalttext, stichworttext,tag,dateityp, uploaddatum, dokumentdatum,status,blobdatei from uploaddaten where uploadid='"+id+"';";

		System.out.println(READ_DATEN_AUTORASC);
		try {
			pstmt = conn.prepareStatement(READ_DATEN_AUTORASC);
			rs = pstmt.executeQuery();
			System.out.println("yoo");
			while(rs.next())
			{
				String dateiname=rs.getString(1);
				String autor=rs.getString(2);
				String uploader=rs.getString(3);
				String inhalttext=rs.getString(4);
				String stichworttext=rs.getString(5);
				String tag=rs.getString(6);
				String dateityp=rs.getString(7);
				String uploaddatum=rs.getString(8);
				String dokumentdatum=rs.getString(9);
				String status=rs.getString(10);
				byte[] blobdatei=rs.getBytes(11);
				
				uploaddaten=new Daten(dateiname,autor,uploader,inhalttext,stichworttext,dateityp,status,tag,uploaddatum,dokumentdatum,blobdatei);
				
				System.out.println(uploaddaten);
			}

			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return uploaddaten;

	}

	public int AnzahlEinträge(Connection conn,String user,String tabelle)
	{
		String SQL="select count(*) from "+tabelle+" JOIN benutzer ON(uploaddaten.uploader = benutzer.benutzername) WHERE benutzername = '"+user+"';";

		try {
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				anzahl=rs.getInt(1);
				System.out.println("Anzahl der Einträge in DB: "+anzahl);
			}

			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return anzahl;
	}
	
	public int AnzahlEinträge1(Connection conn,String spalte,String spalteninhalt,String tabelle,String id)
	{
		String SQL="select count("+id+") from "+tabelle+" where "+spalte+"='"+spalteninhalt+"'";

		System.out.println(SQL);
		try {
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				anzahl=rs.getInt(1);
				System.out.println("Anzahl der Einträge in DB: "+anzahl);
			}
			
			pstmt.close(); 
			if(pstmt!=null)
			{
				pstmt=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return anzahl;
	}


	/**
	 * Hier stehen die Methoden für bestimmte Funktionen, Volltextsuche, Text Vereinfachung, Dateien als BLOB in Datenbank speichern und auslesen...
	 */

	//Methode zum generieren eines vereinfachten Text zur 
	public String Stichtextgenerator(Connection conn,String text) {
		//System.out.print("Das Wort"+text+"wurde vereinfacht zu "+EasyText+". ");

		String SEARCH_FOR_DATA_SQL_DATEN = "select to_tsvector(\'"+ text +"\')";

		try {
			if(pstmt==null){
				pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
				//System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
				rs = pstmt.executeQuery();
				while (rs.next()) {

					easyText = rs.getString(1);
					System.out.print("Der Text Inhalttext wurde vereinfacht zu '"+easyText+"'.");
					//System.out.println(easyText);

					System.out.println();
				}
			}

			pstmt.close();
			pstmt=null;
//			rs.close();
			rs=null;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return easyText;
	}


	public String VereinfachtesSuchwortgenerator(Connection conn, String wort) {
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
				pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
				rs = pstmt.executeQuery();
				while (rs.next()) {

					easySuchwort=(rs.getString(1));
					//easySuchwort=(rs.getTsvector(1));
					System.out.print("Das Wort eingegebene Wort,'"+wort+"' ,wurde vereinfacht zu '"+easySuchwort+"'.");


					System.out.println();
				}
			}

			rs.close();rs=null;
			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return easySuchwort;
	}

	//Überprüft ob angegebnes wort im text ist
	public boolean fulltextsearch(Connection conn,String wort,String wort2) {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "select (\'"+wort+"\')@@(\'"+wort2+"\')";
		//System.out.print(easySuchwort);
		try {	
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				wert=rs.getBoolean(1);
				System.out.println("Ist das eingegebene Suchwort, "+wort2+", im Text?");
				System.out.print(wert);

				System.out.println();
			}

			pstmt.close(); pstmt=null;
			rs.close(); rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wert;
	}

	// TODO
	public float ranking(Connection conn,String wort,String wort2) {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "select ts_rank((\'"+wort+"\')@@ to_tsquery(\'"+wort2+"\')) as relevancy";
		System.out.print(SEARCH_FOR_DATA_SQL_DATEN);
		try {	
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				relevanz=rs.getFloat(1);
				System.out.println("Ist das eingegebene Suchwort, "+wort2+", im Text und mit einer wie hohen relevanz auf den Text?");
				System.out.print(relevanz);

				System.out.println();
			}
			pstmt.close(); pstmt=null;
			rs.close(); rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return relevanz;
	}

	//TODO mit count(uploadid) 
	public ArrayList<String[]> ranking2(Connection conn, String wort,String username) {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "SELECT uploadid, dateityp, dateiname, autor, uploaddatum, dokumentdatum, status FROM (SELECT uploaddaten.uploadid as uploadid, uploaddaten.dateityp as dateityp, "
				+ "uploaddaten.dateiname as dateiname, uploaddaten.dokumentdatum as dokumentdatum, uploaddaten.uploaddatum as uploaddatum, uploaddaten.status as status, uploaddaten.autor as autor, uploaddaten.uploader as uploader,"
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.dateiname), 'A') || "
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.inhalttext), 'B') ||"
				+ " setweight(to_tsvector('simple', uploaddaten.autor), 'C') ||"
				+ " setweight(to_tsvector('simple', coalesce(string_agg(uploaddaten.tag, ' '))), 'B') as document"
				+ " FROM uploaddaten"
				+ " GROUP BY uploaddaten.uploadid, uploaddaten.autor) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', \'"+wort+"\') and uploader='"+username+"\'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', \'"+wort+"\')) DESC";
		
		System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
		try {	
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			rs.getFetchSize();
			System.out.println("rs.getFetchSize()"+rs.getFetchSize());
			while (rs.next()) {
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 7; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				daten.add(zeile);
				
				System.out.println();
				System.out.println("Sortierte Texte nach Suchwort");

				System.out.println();
			}
			pstmt.close(); pstmt=null;
			rs.close(); rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return daten;
	}
	
	//TODO mit count(uploadid) 
	public ArrayList<String[]> ranking3(Connection conn, String wort,String username) {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "SELECT uploadid, dateityp, dateiname, autor, uploaddatum, dokumentdatum, deletedatum status FROM (SELECT geloeschtedaten.uploadid as uploadid, geloeschtedaten.dateityp as dateityp, "
				+ "geloeschtedaten.dateiname as dateiname, geloeschtedaten.dokumentdatum as dokumentdatum, geloeschtedaten.uploaddatum as uploaddatum, geloeschtedaten.status as status, geloeschtedaten.autor as autor, geloeschtedaten.uploader as uploader, geloeschtedaten.deletedatum as deletedatum"
				+ " setweight(to_tsvector(geloeschtedaten.language::regconfig, geloeschtedaten.dateiname), 'A') || "
				+ " setweight(to_tsvector(geloeschtedaten.language::regconfig, geloeschtedaten.inhalttext), 'B') ||"
				+ " setweight(to_tsvector('simple', geloeschtedaten.autor), 'C') ||"
				+ " setweight(to_tsvector('simple', coalesce(string_agg(geloeschtedaten.tag, ' '))), 'B') as document"
				+ " FROM geloeschtedaten"
				+ " GROUP BY geloeschtedaten.uploadid, geloeschtedaten.autor) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', \'"+wort+"\') and uploader='"+username+"\'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', \'"+wort+"\')) DESC";
		
		System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
		try {	
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			rs.getFetchSize();
			System.out.println("rs.getFetchSize()"+rs.getFetchSize());
			while (rs.next()) {
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 8; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				daten.add(zeile);
				
				System.out.println();
				System.out.println("Sortierte Texte nach Suchwort");

				System.out.println();
			}
			pstmt.close(); pstmt=null;
			rs.close(); rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return daten;
	}

	public ArrayList<String[]> ranking3(Connection conn, String wort) {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "SELECT uploadid, dateityp, dateiname, uploader,autor, uploaddatum, dokumentdatum, status FROM (SELECT uploaddaten.uploadid as uploadid, uploaddaten.dateityp as dateityp, "
				+ "uploaddaten.dateiname as dateiname, uploaddaten.autor as autor, uploaddaten.dokumentdatum as dokumentdatum, uploaddaten.uploaddatum as uploaddatum, uploaddaten.status as status, uploaddaten.uploader as uploader,"
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.dateiname), 'A') || "
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.inhalttext), 'B') ||"
				+ " setweight(to_tsvector('simple', uploaddaten.autor), 'C') ||"
				+ " setweight(to_tsvector('simple', coalesce(string_agg(uploaddaten.tag, ' '))), 'B') as document"
				+ " FROM uploaddaten"
				+ " GROUP BY uploaddaten.uploadid, uploaddaten.autor) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', \'"+wort+"\') and status='public'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', \'"+wort+"\')) DESC";
		
		System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
		try {	
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			rs.getFetchSize();
			System.out.println("rs.getFetchSize()"+rs.getFetchSize());
			while (rs.next()) {
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 7; i++) {
					zeile[i] = rs.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				daten.add(zeile);
				
				System.out.println();
				System.out.println("Sortierte Texte nach Suchwort");

				System.out.println();
			}
			pstmt.close(); pstmt=null;
			rs.close(); rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return daten;
	}


	public void Blobeinfuegen(Connection conn,Part filePart,String text)
	{
		//String INSERT_DATA_SQL="INSERT INTO uploaddaten (blobdatei) VALUES (?)";
		String INSERT_DATA_SQL="UPDATE uploaddaten set blobdatei =? WHERE inhalttext = '"+text+"'";
		try {

			InputStream fis = filePart.getInputStream();
			//			FileInputStream fis= new FileInputStream(file);
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
			//			fis = new FileInputStream(file);
			//			pstmt.setString(1, file.getName());
			pstmt.setBinaryStream(2, fis, (int)filePart.getSize());
			pstmt.executeUpdate();

			pstmt.close();
			fis.close();

			pstmt.close(); pstmt=null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Daten in Datenbank gepeichert.");
	}


	public byte[] BLOBauslesen(Connection conn,String id)
	{
		//FileOutputStream fos = null;
		byte[] buf=null;
		try {
			String query = "SELECT blobdatei, LENGTH(blobdatei) FROM uploaddaten WHERE uploadid = \'"+id+"\'";
			pstmt = conn.prepareStatement(query);

			ResultSet result = pstmt.executeQuery();
			result.next();

			int len = result.getInt(2);
			buf = result.getBytes("blobdatei");
			System.out.println("buf"+buf);

			pstmt.close(); pstmt=null;
		} catch (Exception ex) {
			ex.printStackTrace();


		}
		return buf; 

	}

	/**
	 * Hier stehen Methoden zum auslesen der Daten allgemein
	 */

	//TODO 
	//Auslesen aller Daten in der Tabelle Uploaddaten
	public ArrayList<String[]> readDaten(Connection conn, int id) {
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//SQL-Abfrage
		String READ_DATA_SQL_DATEN = "SELECT loeschid, dateiname, autor, uploader, inhalttext, stichworttext,blobdatei,tag FROM uploaddaten where uploadid=\'"+id+"\'"; 
		try { 
			pstmt = conn.prepareStatement(READ_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 9; i++) {
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


	public List<Daten> readDaten2(Connection conn) {
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		List<Daten> daten = new ArrayList<>();
		//SQL-Abfrage
		String READ_DATA_SQL_DATEN = "SELECT dateiname, autor, uploader, inhalttext, uploader, autor, dateiname,uploadid,uploaddatum FROM uploaddaten";
		//opens a connection, 
		try { 
			pstmt = conn.prepareStatement(READ_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String language=rs.getString(1);
				String tag=rs.getString(2);
				String blobdatei=rs.getString(3);
				String stichworttext=rs.getString(4);
				String inhalttext=rs.getString(5);
				String uploader=rs.getString(6);
				String autor=rs.getString(7);
				String dateiname=rs.getString(8);
				int uploadid=rs.getInt(9);
				String uploaddatum=rs.getString(10);

				Daten zeile = new Daten();
				daten.add(zeile);

			}
			System.out.println();

			rs.close(); rs=null;
			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return daten;
	}



	public ArrayList<String[]> readAutoren(Connection conn) {
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> Autoren = new ArrayList<String[]>();
		//SQL-Abfrage
		String READ_DATA_SQL_AUTOREN = "select autor from uploaddaten group by autor";
		try { 
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

	public ArrayList<String[]> readDateinamen(Connection conn) {

		ArrayList<String[]> dateinamen = new ArrayList<String[]>();
		//SQL-Abfrage
		String READ_DATA_SQL_DATEINAMEN = "select dateiname from uploaddaten group by dateiname";
		try {
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

	public ArrayList<String[]> readTags(Connection conn) {

		ArrayList<String[]> Tags = new ArrayList<String[]>();
		//SQL-Abfrage
		String READ_DATA_SQL_AUTOREN = "select tag from uploaddaten group by tag";
		try {
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
		ArrayList<Benutzer> benutzer = new ArrayList<>();
		String SQL="select * from benutzer";

		try {
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				String benutzername=rs.getString(1);
				String email=rs.getString(2);
				String vorname=rs.getString(3);
				String nachname=rs.getString(4);
				String passwort=rs.getString(5);

				Benutzer zeile = new Benutzer(benutzername,email,vorname,nachname,passwort);
				benutzer.add(zeile);
			}
			rs.close(); rs=null;
			pstmt.close(); pstmt=null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return benutzer;
	}

	public List<Suchwoerter> readSuchwoerter (Connection conn) throws SQLException
	{
		ArrayList<Suchwoerter> suchwoerter = new ArrayList<>();
		String SQL="select * from suchwoerter order by suchwortid DESC LIMIT 3";

		try {
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				String suchwoert=rs.getString(2);
				int suchwortid=rs.getInt(1);

				Suchwoerter zeile = new Suchwoerter(suchwoert,suchwortid);
				System.out.println(zeile);
				suchwoerter.add(zeile);
			}
			rs.close(); rs=null;
			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return suchwoerter;
	}


	public void Datenlöschen(Connection conn,int id,String tabelle,String spalte)
	{
		String SQL="delete from "+tabelle+" where "+spalte+"='"+id+"';";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();

			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void PasswortNeuSetzen(Connection conn,String username, String password)
	{

		String SQL="UPDATE benutzer set passwort ='"+password+"' WHERE benutzername = '"+username+"';";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();

			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
			wert=false;
		}


	}

	public void RegisterBenutzer(Connection conn,String username, String email, String pwd) throws ClassNotFoundException, SQLException {

		System.out.println("Connecting DB successful");

		String SQL = "INSERT into benutzer (benutzername,email,passwort) values(?,?,?);";
		PreparedStatement ps = conn.prepareStatement( SQL);  

		ps.setString(1,username);
		ps.setString(2,email); 
		ps.setString(3,pwd); 

		int i = ps.executeUpdate();
		System.out.println(SQL);
		
		if(i > 0 ) System.out.println("error");
		
	}

	public String getEmailByUser(Connection conn,String user) {
		{

			String SQL="SELECT email from benutzer WHERE benutzername = '"+user+"'";
			String email=null;

			try {
				pstmt=conn.prepareStatement(SQL);
				rs=pstmt.executeQuery();
				while(rs.next())
				{
					email=rs.getString(1);
				}
				rs.close(); rs=null;
				pstmt.close(); pstmt=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return email;
		}

	}

	public String getUserByEmail(Connection conn,String emailuser) {
	
		String SQL="SELECT benutzername FROM benutzer WHERE email ='"+emailuser+"'";
		String benutzername=null;

		try {
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				benutzername=rs.getString(1);
			}
			rs.close(); rs=null;
			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return benutzername;
	}


	public String getUser(Connection conn,String username) {

		String SQL="select benutzername from benutzer where benutzername='"+username+"'";
		String user=null;

		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			ResultSet rs=pstmt.executeQuery();
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();

			while(rs.next())
			{
				user=rs.getString(1);
			}
			rs.close(); rs=null;
			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}



	public String getEmail(Connection conn,String em) {
		{
			
			String SQL="select email from benutzer where email='"+em+"'";
			String email=null;

			try {
				pstmt=conn.prepareStatement(SQL);
				rs=pstmt.executeQuery();
				while(rs.next())
				{
					email=rs.getString(1);
				}
				rs.close(); rs=null;
				pstmt.close(); pstmt=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return email;
		}

	}

	//TODO WIP
	public String getDateiTyp(Connection conn,String idObj) {
		String SQL = "Select dateityp from uploaddaten where uploadid ='"+idObj+"';";
		String typ = "";
		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			while(rs.next()){
				typ = rs.getString(1);
			}

			pstmt.close(); pstmt=null;
			rs.close(); rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return typ;
	}

	public String[] Dateiname(Connection conn, String username)
	{
		String SQL="select dateiname from uploaddaten JOIN benutzer ON(uploaddaten.uploader = benutzer.benutzername) WHERE benutzername = '"+username+"';";
		
		String[] spalten = new String[anzahl];

		try {
			pstmt=conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			int counter=0;
			while (rs.next()) {
				System.out.print("Gelesen wurde: ");
				spalten[counter] = rs.getString(1);
				counter++;
			}

			rs.close(); rs=null;
			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return spalten;

	}


	public void saveHash(Connection conn,String authcode, String emailuser) {

		String Insert_Hash="UPDATE benutzer set authcode ='"+authcode+"' WHERE email ='"+emailuser+"';";

		//connection Aufbau
		try {
			pstmt = conn.prepareStatement(Insert_Hash);
			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}



	public  boolean CodeCheck(Connection conn,String hashcode) {

		List<String> vorhandeneHash = new ArrayList<String>();

		//SQL-Abfrage
		String ReadHash="select authcode from benutzer;";

		try {
			pstmt = conn.prepareStatement(ReadHash);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String code = rs.getString(1);
				System.out.println(code);
				vorhandeneHash.add(code);
			}

			rs.close(); rs=null;
			pstmt.close(); pstmt=null;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		if(vorhandeneHash.contains(hashcode)){
			return true;
		}
		else{
			return false;
		}
	}

	public String getUserbyHash(Connection conn,String hashcode) {

		//SQL-Abfrage
		String ReadUserbyHash="select benutzername from benutzer where authcode ='"+hashcode+"';";
		String user = "";
		try {
			pstmt = conn.prepareStatement(ReadUserbyHash);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				user = rs.getString(1);

			}

			rs.close(); rs=null;
			pstmt.close(); pstmt=null;
		}catch (Exception e) {
			// TODO: handle exception
		}

		return user;
	}

	public void UpdateStatus(Connection conn,int id, String status)
	{
		String INSERT_DATA_SQL="UPDATE uploaddaten set status ='"+status+"' WHERE uploadid = '"+id+"'";
		try {
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
			pstmt.executeUpdate();

			rs.close(); rs=null;
			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Daten in Datenbank gepeichert.");
	}

	public void deletebyname(String dateiname,String username, Connection conn) {
		String SQL = "delete from uploaddaten where dateiname ='"+dateiname+"' AND uploader = '"+username+"';";
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean checkUser(Connection conn, String username, String pwd) {

		boolean st = false; 
		String sql = "SELECT * FROM benutzer where benutzername=? and passwort=?";

		System.out.println("Connecting to database...");

		try {

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Connecting successful");

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1,  username);
			ps.setString(2,  pwd);

			ResultSet rs = ps.executeQuery(); // st boolean? 

			st = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connecting not successful");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return st; 
	}
	
	public void deletehash(Connection conn2, String username) {
		// TODO Auto-generated method stub
		String sql = "update benutzer set authcode = null where benutzername = '"+username+"'";
		try {
			pstmt = conn2.prepareStatement(sql);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDateiinfo(int id,Connection con,String tabelle,String spalte) {
		// TODO Auto-generated method stub
		String SQL = "Select * from "+tabelle+" where "+spalte+" = '"+id+"'";
		String uploader = null;
		try {
			pstmt = con.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				 uploader = rs.getString("uploader");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uploader;
	}

}