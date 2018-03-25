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
	static public String DB_URL = "jdbc:postgresql://localhost/diplomarbeit";

	//  Database credentials
	static public String DB_USER = "postgres";
	static public String DB_PASS = "password";
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
			conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);


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
	public static boolean writeDaten(Connection conn,Daten uploaddaten, Part filePart){

		InputStream fis;

		boolean erfolg = true;
		//SQL-Abfrag zum hineinschreiben neuer Daten
		String INSERT_DATA_SQL = "INSERT INTO uploaddaten (inhalttext, uploader, autor, dateiname, stichworttext, dateityp, status, dokumentdatum, uploaddatum, blobdatei,zustand) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

		//connection Aufbau
		try {
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
			fis = filePart.getInputStream();

			pstmt.setString(1, uploaddaten.getInhalttext());
			pstmt.setString(2, uploaddaten.getUploader());
			pstmt.setString(3, uploaddaten.getAutor());
			pstmt.setString(4, uploaddaten.getDateiname());
			pstmt.setString(5, uploaddaten.getStichworttext());
			pstmt.setString(6, uploaddaten.getDateityp());
			pstmt.setString(7, "private");
			pstmt.setString(8, uploaddaten.getDokumentdatum());
			pstmt.setString(9, getaktuellesDatum());
			pstmt.setBinaryStream(10, fis, (int)filePart.getSize());
			pstmt.setBoolean(11, true);
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

//	public boolean writeDaten(Connection conn,Daten Uploaddaten){
//
//		boolean erfolg = true;
//		//SQL-Abfrag zum hineinschreiben neuer Daten
//		String INSERT_DATA_SQL = "INSERT INTO uploaddaten (dateiname, autor, uploader, inhalttext, stichworttext, dateityp, uploaddatum, dokumentdatum, status, blobdatei) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";
//
//		//connection Aufbau
//		try {
//			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
//
//			pstmt.setString(1, Uploaddaten.getDateiname());
//			pstmt.setString(2, Uploaddaten.getAutor());
//			pstmt.setString(3, Uploaddaten.getUploader());
//			pstmt.setString(4, Uploaddaten.getInhalttext());
//			pstmt.setString(5, Uploaddaten.getStichworttext());
//			pstmt.setString(7, Uploaddaten.getDateityp());
//			pstmt.setString(8, Uploaddaten.getUploaddatum());
//			pstmt.setString(9, Uploaddaten.getDokumentdatum());
//			pstmt.setString(10, Uploaddaten.getStatus());
//			pstmt.setBytes(11, Uploaddaten.getBlobdatei());
//
//
//			pstmt.executeUpdate();
//
//			pstmt.close();pstmt=null;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			erfolg = false;
//		}
//		return erfolg;
//
//	}

	public boolean writegeloeschteDaten(Connection conn,Daten deletedaten){

		boolean erfolg = true;
		//SQL-Abfrag zum hineinschreiben neuer Daten
		String INSERT_DATA_SQL = "INSERT INTO geloeschtedaten (dateiname, autor, uploader, inhalttext, stichworttext, dateityp, uploaddatum, dokumentdatum, status, blobdatei, deletedatum) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?)";


		String date = getaktuellesDatum();

		//connection Aufbau
		try {
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
			pstmt.setString(1, deletedaten.getDateiname());
			pstmt.setString(2, deletedaten.getAutor());
			pstmt.setString(3, deletedaten.getUploader());
			pstmt.setString(4, deletedaten.getInhalttext());
			pstmt.setString(5, deletedaten.getStichworttext());
			pstmt.setString(6, deletedaten.getDateityp());
			pstmt.setString(7, deletedaten.getUploaddatum());
			pstmt.setString(8, deletedaten.getDokumentdatum());
			pstmt.setString(9, deletedaten.getStatus());
			pstmt.setBytes(10, deletedaten.getBlobdatei());
			pstmt.setString(11, date);

			System.out.println("Gelöschte Daten: "+deletedaten);

			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
			erfolg = false;
		}
		return erfolg;

	}
	
	public void updateZustandloeschen(Connection conn, int uploadid) {
		String sql = "update uploaddaten set zustand = 'false', deletedatum = '"+getaktuellesDatum()+"' where uploadid = '"+uploadid+"'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateZustandwiederherstellen(Connection conn, int uploadid) {
		String sql = "update uploaddaten set zustand = 'true', deletedatum = '' where uploadid = '"+uploadid+"'";
		System.out.println("SQL zum Daten wiederherstellen: "+sql);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
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


	public void writeStichwörter(Connection conn,String wort,String username)
	{
		String SQL2="Insert into suchwoerter (Suchwort,Benutzer) VALUES (?,?)";
		//String SQL2="Insert into verwendSuchwort (Suchwort) VALUES (?) ON DUPLICATE KEY UPDATE 'suchwort' = 'suchwort';";

		try {
			pstmt=conn.prepareStatement(SQL2);

			pstmt.setString(1, wort);
			pstmt.setString(2, username);
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

	public ArrayList<Daten> meineDaten(Connection conn,String sortierdings,String spalte,String reihung)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<Daten> DatenSortiertPrivate = new ArrayList<>();
		
		//SQL-Abfrage
		String READ_DATEN_PRIVATE="select uploadid,dateityp, dateiname, autor, uploaddatum, dokumentdatum, status from uploaddaten where uploader='"+sortierdings+"' and zustand='true' order by "+spalte+" "+ reihung+";";

		System.out.println("SQL Daten auf Website angeben"+READ_DATEN_PRIVATE);
		try {
			pstmt = conn.prepareStatement(READ_DATEN_PRIVATE);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				int uploadid = rs.getInt(1);
				String dateityp = rs.getString(2);
				String dateiname = rs.getString(3);
				String autor = rs.getString(4);
				String uploaddatum = rs.getString(5);
				String dokumentdatum = rs.getString(6);
				String status = rs.getString(7);
				float anzahl = 0;
				
				Daten zeile = new Daten(uploadid,dateityp,dateiname, autor, uploaddatum, dokumentdatum, status,anzahl);
				DatenSortiertPrivate.add(zeile);
			}

			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return DatenSortiertPrivate;

	}

	public ArrayList<Daten> publicDaten(Connection conn,String spalte,String reihung)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<Daten> DatenSortierPublic = new ArrayList<Daten>();

		//SQL-Abfrage
		String READ_DATEN_AUTORASC="select uploadid,dateityp, dateiname, uploader, autor, uploaddatum, dokumentdatum, status from uploaddaten where status='public' and zustand='true' order by "+spalte+" "+ reihung+";";

		System.out.println(READ_DATEN_AUTORASC);
		try {
			pstmt = conn.prepareStatement(READ_DATEN_AUTORASC);
			rs = pstmt.executeQuery();
			System.out.println("yoo");
			while(rs.next())
			{
				int uploadid = rs.getInt(1);
				String dateityp = rs.getString(2);
				String dateiname = rs.getString(3);
				String uploader = rs.getString(4);
				String autor = rs.getString(5);
				String uploaddatum = rs.getString(6);
				String dokumentdatum = rs.getString(7);
				String status = rs.getString(8);
				
				Daten zeile = new Daten(uploadid,dateityp,dateiname, uploader, autor, uploaddatum, dokumentdatum, status);
				DatenSortierPublic.add(zeile);
			}

			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Wird des ausgeführt");
		return DatenSortierPublic;

	}

	public ArrayList<Daten> geloeschteDaten(Connection conn,String sortierdings,String spalte,String reihung)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<Daten> geloeschteDaten = new ArrayList<Daten>();

		//SQL-Abfrage
		String READ_DATEN_AUTORASC="select uploadid, dateityp, dateiname, autor, deletedatum, uploaddatum, dokumentdatum from uploaddaten where uploader='"+sortierdings+"' and zustand='false' order by "+spalte+" "+ reihung+";";

		System.out.println(READ_DATEN_AUTORASC);
		try {
			pstmt = conn.prepareStatement(READ_DATEN_AUTORASC);
			rs = pstmt.executeQuery();
			System.out.println("yoo");
			while(rs.next())
			{
				int uploadid = rs.getInt(1);
				String dateityp = rs.getString(2);
				String dateiname = rs.getString(3);
				String autor = rs.getString(4);
				String deletedatum = rs.getString(5);
				String uploaddatum = rs.getString(6);
				String dokumentdatum = rs.getString(7);
				long anzahl = 0;
				
				System.out.println("Deletedatum ausgeben: "+deletedatum);
				
				Daten zeile = new Daten(uploadid,dateityp,dateiname, autor, deletedatum, uploaddatum, dokumentdatum,anzahl);
				geloeschteDaten.add(zeile);
			}

			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return geloeschteDaten;

	}

	public Daten readgeloeschteDatei(Connection conn,int id)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		Daten uploaddaten = new Daten();

		//SQL-Abfrage
		String READ_DATEN_AUTORASC="select dateiname, autor, uploader, inhalttext, stichworttext, dateityp, status, tag, uploaddatum, dokumentdatum, blobdatei, deletedatum from uploaddaten where uploadid='"+id+"';";

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
				String uploaddatum=rs.getString(8);
				String dokumentdatum=rs.getString(9);
				byte[] blobdatei=rs.getBytes(10);
				String deletedatum=rs.getString(11);

				uploaddaten =new Daten(dateiname,autor,uploader,inhalttext,stichworttext,dateityp,status,uploaddatum,dokumentdatum,deletedatum,blobdatei);

				System.out.println(uploaddaten);
			}

			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return uploaddaten;

	}

//	public Daten readzuloeschendeDatei(Connection conn,int id)
//	{
//		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
//		Daten uploaddaten = new Daten();
//
//		//SQL-Abfrage
//		String READ_DATEN_AUTORASC="select dateiname, autor, uploader, inhalttext, stichworttext, dateityp, uploaddatum, dokumentdatum,status,blobdatei from uploaddaten where uploadid='"+id+"';";
//
//		System.out.println(READ_DATEN_AUTORASC);
//		try {
//			pstmt = conn.prepareStatement(READ_DATEN_AUTORASC);
//			rs = pstmt.executeQuery();
//			System.out.println("yoo");
//			while(rs.next())
//			{
//				String dateiname=rs.getString(1);
//				String autor=rs.getString(2);
//				String uploader=rs.getString(3);
//				String inhalttext=rs.getString(4);
//				String stichworttext=rs.getString(5);
//				String dateityp=rs.getString(6);
//				String uploaddatum=rs.getString(7);
//				String dokumentdatum=rs.getString(8);
//				String status=rs.getString(9);
//				byte[] blobdatei=rs.getBytes(10);
//
//				uploaddaten=new Daten(dateiname,autor,uploader,inhalttext,stichworttext,dateityp,status,uploaddatum,dokumentdatum,blobdatei);
//
//				System.out.println(uploaddaten);
//			}
//
//			pstmt.close(); pstmt=null;
//			rs.close();rs=null;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return uploaddaten;
//
//	}

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

	public int AnzahlEinträgeDaten(Connection conn,String spalte,String spalteninhalt,String zustand)
	{
		String SQL="select count(uploadid) from uploaddaten where "+spalte+"='"+spalteninhalt+"' and zustand='"+zustand+"'";

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

	//Überprüft ob ein bestimmtes wort im text ist
	public boolean  tsearch(Connection conn,String wort,String wort2) {

		//SQL Abfrage
		String SEARCH_FOR_DATA_SQL_DATEN = "select (\'"+wort+"\')@@(\'"+wort2+"\')";

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

	//TODO das von der Dateinamen auch gesucht wird
	public ArrayList<Daten> durchsuchenPrivate(Connection conn, String wort,String username) {
		ArrayList<Daten> daten = new ArrayList<Daten>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "SELECT uploadid, dateityp, dateiname, autor, uploaddatum, dokumentdatum, status FROM (SELECT uploaddaten.uploadid as uploadid,"
				+ "uploaddaten.dateityp as dateityp, uploaddaten.dateiname as dateiname, uploaddaten.dokumentdatum as dokumentdatum, uploaddaten.uploaddatum as uploaddatum,"
				+ "uploaddaten.status as status, uploaddaten.autor as autor, uploaddaten.uploader as uploader, uploaddaten.zustand as zustand,"
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.dateiname), 'A') || "
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.inhalttext), 'B') ||"
				+ " setweight(to_tsvector('simple', uploaddaten.autor), 'C') as document"
				+ " FROM uploaddaten) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', \'"+wort+"\') and uploader='"+username+"\' and zustand='true'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', \'"+wort+"\')) DESC";

		System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
		try {	
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			rs.getFetchSize();
			System.out.println("rs.getFetchSize()"+rs.getFetchSize());
			while (rs.next()) {
				int uploadid = rs.getInt(1);
				String dateityp = rs.getString(2);
				String dateiname = rs.getString(3);
				String autor = rs.getString(4);
				String uploaddatum = rs.getString(5);
				String dokumentdatum = rs.getString(6);
				String status = rs.getString(7);
				float i = 0;
				
				Daten zeile = new Daten(uploadid,dateityp,dateiname, autor, uploaddatum, dokumentdatum, status,i);
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
	
	public ArrayList<Daten> durchsuchenPrivate2(Connection conn, String wort,String username) {
		ArrayList<Daten> daten = new ArrayList<Daten>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "SELECT uploadid, dateityp, dateiname, autor, uploaddatum, dokumentdatum, status FROM (SELECT uploaddaten.uploadid as uploadid,"
				+ "uploaddaten.dateityp as dateityp, uploaddaten.dateiname as dateiname, uploaddaten.dokumentdatum as dokumentdatum, uploaddaten.uploaddatum as uploaddatum,"
				+ "uploaddaten.status as status, uploaddaten.autor as autor, uploaddaten.uploader as uploader, uploaddaten.zustand as zustand, uploaddaten.stichworttext as stichworttext"
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.dateiname), 'A') || "
				+ " setweight(uploaddaten.language::regconfig, uploaddaten.stichworttext), 'B') ||"
				+ " setweight(to_tsvector('simple', uploaddaten.autor), 'C') as document"
				+ " FROM uploaddaten) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', \'"+wort+"\') and uploader='"+username+"\' and zustand='true'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', \'"+wort+"\')) DESC";

		System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
		try {	
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			rs.getFetchSize();
			System.out.println("rs.getFetchSize()"+rs.getFetchSize());
			while (rs.next()) {
				int uploadid = rs.getInt(1);
				String dateityp = rs.getString(2);
				String dateiname = rs.getString(3);
				String autor = rs.getString(4);
				String uploaddatum = rs.getString(5);
				String dokumentdatum = rs.getString(6);
				String status = rs.getString(7);
				float i = 0;
				
				Daten zeile = new Daten(uploadid,dateityp,dateiname, autor, uploaddatum, dokumentdatum, status,i);
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
	public ArrayList<Daten> durchsuchenGeloeschte(Connection conn, String wort,String username) {
		ArrayList<Daten> daten = new ArrayList<Daten>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "SELECT uploadid, dateityp, dateiname, autor, uploaddatum, dokumentdatum, deletedatum status FROM (SELECT uploaddaten.uploadid as uploadid, uploaddaten.dateityp as dateityp, "
				+ "uploaddaten.dateiname as dateiname, uploaddaten.dokumentdatum as dokumentdatum, uploaddaten.uploaddatum as uploaddatum, uploaddaten.status as status, uploaddaten.autor as autor, uploaddaten.uploader as uploader, uploaddaten.deletedatum as deletedatum, uploaddaten.zustand as zustand,"
				+ " setweight(to_tsvector(geloeschtedaten.language::regconfig, geloeschtedaten.dateiname), 'A') || "
				+ " setweight(to_tsvector(geloeschtedaten.language::regconfig, geloeschtedaten.inhalttext), 'B') ||"
				+ " setweight(to_tsvector('simple', geloeschtedaten.autor), 'C') as document"
				+ " FROM uploaddaten"
				+ " GROUP BY uploaddaten.uploadid) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', \'"+wort+"\') and uploader='"+username+"\' and zustand='false'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', \'"+wort+"\')) DESC";

		System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
		try {	
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			rs.getFetchSize();
			System.out.println("rs.getFetchSize()"+rs.getFetchSize());
			while (rs.next()) {
				int uploadid = rs.getInt(1);
				String dateityp = rs.getString(2);
				String dateiname = rs.getString(3);
				String autor = rs.getString(4);
				String uploaddatum = rs.getString(5);
				String dokumentdatum = rs.getString(6);
				String deletedatum = rs.getString(7);
				String status = rs.getString(8);
				float anzahl = 0;
				
				Daten zeile = new Daten(uploadid,dateityp,dateiname, autor, uploaddatum, dokumentdatum, deletedatum, status, anzahl);
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

	public ArrayList<Daten> durchsuchenPublic(Connection conn, String wort) {
		ArrayList<Daten> daten = new ArrayList<Daten>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "SELECT uploadid, dateityp, dateiname, uploader,autor, uploaddatum, dokumentdatum, status FROM (SELECT uploaddaten.uploadid as uploadid, uploaddaten.dateityp as dateityp, "
				+ "uploaddaten.dateiname as dateiname, uploaddaten.autor as autor, uploaddaten.dokumentdatum as dokumentdatum, uploaddaten.uploaddatum as uploaddatum, uploaddaten.status as status, uploaddaten.uploader as uploader, uploaddaten.zustand as zustand,"
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.dateiname), 'A') || "
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.inhalttext), 'B') ||"
				+ " setweight(to_tsvector('simple', uploaddaten.autor), 'C') as document"
				+ " FROM uploaddaten"
				+ " GROUP BY uploaddaten.uploadid) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', \'"+wort+"\') and status='public' and zustand='true'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', \'"+wort+"\')) DESC";

		System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
		try {	
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			rs.getFetchSize();
			System.out.println("rs.getFetchSize()"+rs.getFetchSize());
			while (rs.next()) {
				int uploadid = rs.getInt(1);
				String dateityp = rs.getString(2);
				String dateiname = rs.getString(3);
				String uploader = rs.getString(4);
				String autor = rs.getString(5);
				String uploaddatum = rs.getString(6);
				String dokumentdatum = rs.getString(7);
				String status = rs.getString(8);
				
				Daten zeile = new Daten(uploadid,dateityp,dateiname, uploader, autor, uploaddatum, dokumentdatum, status);
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


	//	public void Blobeinfuegen(Connection conn,Part filePart,String text)
	//	{
	//		//String INSERT_DATA_SQL="INSERT INTO uploaddaten (blobdatei) VALUES (?)";
	//		String INSERT_DATA_SQL="UPDATE uploaddaten set blobdatei =? WHERE inhalttext = '"+text+"'";
	//		try {
	//
	//			InputStream fis = filePart.getInputStream();
	//			//			FileInputStream fis= new FileInputStream(file);
	//			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
	//			//			fis = new FileInputStream(file);
	//			//			pstmt.setString(1, file.getName());
	//			pstmt.setBinaryStream(2, fis, (int)filePart.getSize());
	//			pstmt.executeUpdate();
	//
	//			pstmt.close();
	//			fis.close();
	//
	//			pstmt.close(); pstmt=null;
	//		} catch (FileNotFoundException e) {
	//			e.printStackTrace();
	//		}catch (SQLException e) {
	//			e.printStackTrace();
	//		}catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//
	//		System.out.println("Daten in Datenbank gepeichert.");
	//	}


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


//	public List<Daten> readDaten2(Connection conn) {
//		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
//		List<Daten> daten = new ArrayList<>();
//		//SQL-Abfrage
//		String READ_DATA_SQL_DATEN = "SELECT uploadid, dateiname, autor, uploader, inhalttext, stichworttext, dateityp, status, uploaddatum, dokumentdatum FROM uploaddaten";
//		//opens a connection, 
//		try { 
//			pstmt = conn.prepareStatement(READ_DATA_SQL_DATEN);
//			rs = pstmt.executeQuery();
//			while (rs.next()) {
//				int uploadid = rs.getInt(1);
//				String dateiname=rs.getString(2);
//				String autor=rs.getString(3);
//				String uploader=rs.getString(4);
//				String inhalttext=rs.getString(5);
//				String stichworttext=rs.getString(6);
//				String dateityp=rs.getString(7);
//				String status=rs.getString(8);
//				String uploaddatum=rs.getString(9);
//				String dokumentdatum=rs.getString(10);
//
//				Daten zeile = new Daten(uploadid,dateiname,autor,uploader,inhalttext,stichworttext,dateityp,status,uploaddatum,dokumentdatum);
//				daten.add(zeile);
//
//			}
//			System.out.println();
//
//			rs.close(); rs=null;
//			pstmt.close(); pstmt=null;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return daten;
//	}



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
			
			pstmt.close(); pstmt=null;
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
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,  username);
			pstmt.setString(2,  pwd);

			rs = pstmt.executeQuery(); // st boolean? 

			st = rs.next();

			pstmt.close(); pstmt=null;
			rs.close(); rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connecting not successful");
		}
		return st; 
	}

	public void deletehash(Connection conn2, String username) {
		// TODO Auto-generated method stub
		String sql = "update benutzer set authcode = null where benutzername = '"+username+"'";
		try {
			pstmt = conn2.prepareStatement(sql);
			pstmt.executeUpdate();
			
			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDateiinfo(int id,Connection con) {
		// TODO Auto-generated method stub
		String SQL = "Select * from uploaddaten where uploadid = '"+id+"'";
		String uploader = null;
		try {
			pstmt = con.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			while(rs.next()){
				uploader = rs.getString("uploader");
			}

			pstmt.close(); pstmt=null;
			rs.close(); rs=null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uploader;
	}

}
