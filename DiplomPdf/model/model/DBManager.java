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
	static Connection conn = null;
	static Statement stmt = null;
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
	public static boolean writeDaten(String[] testzeile2, Part filePart, Date date){
		
		InputStream fis;
		String entscheidungshilfe=null;
	
		boolean erfolg = true;
		//SQL-Abfrag zum hineinschreiben neuer Daten
		String INSERT_DATA_SQL = "INSERT INTO uploaddaten (tag, inhalttext, uploader, autor, dateiname, stichworttext, dateityp, status, uploaddatum, blobdatei) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?)";

		//connection Aufbau
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
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
			pstmt.setDate(9, date);
			pstmt.setBinaryStream(10, fis, (int)filePart.getSize());
			
//			for (int i = 1; i <=9; i++) {
//				if(i==1) entscheidungshilfe="normal";
//				if(i==6) entscheidungshilfe="date";
//				if(i==9) entscheidungshilfe="blob"; 
//					
//				
//				switch(entscheidungshilfe)
//				{case "normal" :{
//					System.out.print("Dateiinformationen in DB speichern");
//					pstmt.setString(i, testzeile2[i-1]);
//					break;
//				}
//				case "date" :{
//					// dd-mm-yyyy
//					// yyyy-mm-dd
//					String[] datumsTeile = testzeile2[i-1].split("-");
//					//Date datum=PDFmanager.getDatum(); 
//					Date datum=	new Date(Integer.parseInt(datumsTeile[2]), Integer.parseInt(datumsTeile[1]), Integer.parseInt(datumsTeile[0]));
//					System.out.println(datum);
//					pstmt.setDate(i, datum);
//					break;
//				}
//				case "blob" :{
//					System.out.print("Datei als Blob in DB speichern");
//					pstmt.setBinaryStream(9, fis, (int)filePart.getSize());
//					break;
//				}
//				default :{
//					System.out.println("Fehler ALARM");
//					//pstmt.setString(i, testzeile2[i-1]);
//					break;
//				}
//				}
					//pstmt.setString(i, testzeile2[i-1]);
			
				//System.out.println(" '" + testzeile2[i-1] + "'");
//			}
			//pstmt.setDate(id, getSQLDate(lDate));
			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
			erfolg = false;
		}catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return erfolg;

	}


	//TODO Bolbdatei einfügen
	public static boolean writeDaten2(String[] testzeile2, Part filePart) {

		boolean erfolg = true;
		//SQL-Abfrag zum hineinschreiben neuer Daten
		String INSERT_DATA_SQL = "INSERT INTO uploaddaten ( tag, inhalttext, uploader, autor, dateiname, uploaddatum, stichworttext, dateityp, blobdatei) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		//connection Aufbau
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);


			for (int i = 1; i <= 8; i++) {
				if(i==6)
				{
					// dd-mm-yyyy
					// yyyy-mm-dd
					String[] datumsTeile = testzeile2[i-1].split("-");
					//Date datum=PDFmanager.getDatum(); 
					Date datum=	new Date(Integer.parseInt(datumsTeile[2]), Integer.parseInt(datumsTeile[1]), Integer.parseInt(datumsTeile[0]));
					pstmt.setDate(i, datum);
				}
				else
				{
					pstmt.setString(i, testzeile2[i-1]);
				}
				System.out.println(" '" + testzeile2[i-1] + "'");
			}
			//pstmt.setDate(id, getSQLDate(lDate));
			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
			erfolg = false;
		}
		return erfolg;

	}

	static String Suchwort(String name){
		easySuchwort2 = name;
		easySuchwort2 = name.substring(0,name.length()-2);
		System.out.println(easySuchwort2);

		return easySuchwort2;
	}


	public static void writeStichwörter(String wort)
	{
		String SQL2="Insert into suchwoerter (Suchwort) VALUES (?)";
		//String SQL2="Insert into verwendSuchwort (Suchwort) VALUES (?) ON DUPLICATE KEY UPDATE 'suchwort' = 'suchwort';";

		try {
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt=conn.prepareStatement(SQL2);

			pstmt.setString(1, wort);
			System.out.println(" '" +wort+"'");
			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public static void convertDate(String s){
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
	 * Hier werden die Methoden zur geordneten Reihenfolge für die Antwort in den DataTableServlet geschrieben
	 * @return
	 */

	public static ArrayList<String[]> autorASC(Connection conn)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachAutorASC = new ArrayList<String[]>();

		//SQL-Abfrage
		String READ_DATEN_AUTORASC="select uploadid,dateityp, dateiname, autor, tag, uploaddatum, status from uploaddaten order by Autor ASC";

		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
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

			//						try {
			//							//conn = DriverManager.getConnection(DB_URL,USER,PASS);
			//							pstmt = conn.prepareStatement(READ_DATEN_AUTORASC);
			//							rs = pstmt.executeQuery();
			//							
			//							while(rs.next())
			//							{
			//								String dateityp = rs.getString(1);
			//								String dateiname = rs.getString(2);
			//								String autor = rs.getString(3);
			//								String tag = rs.getString(4);
			//								String uploaddatum = rs.getString(5);
			//								
			//								Uploaddaten zeile = new Uploaddaten();
			//								DatennachAutorASC.add(zeile);
			//								System.out.println();
			//							}

			//System.out.println(DatennachAutorASC.get(0)[0]);

			//			for(int i=0;i<=3;i++)
			//			{
			//				System.out.println(DatennachAutorASC.get(i)[0]);
			//				System.out.println(DatennachAutorASC.get(i)[1]);
			//				System.out.println(DatennachAutorASC.get(i)[2]);
			//				System.out.println(DatennachAutorASC.get(i)[3]);
			//			}

			pstmt.close(); pstmt=null;
			rs.close();rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return DatennachAutorASC;

	}

	public static ArrayList<String[]> autorDESC(Connection conn)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachAutorDESC = new ArrayList<String[]>();

		//SQL-Abfrage
		String READ_DATEN_AUTORDESC="select uploadid, dateityp, dateiname, autor, tag, uploaddatum, status from uploaddaten order by Autor DESC";

		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_AUTORDESC);
			rs = pstmt.executeQuery();

			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 7; i++) {
//					if(i==5)
//					{
//						zeile[i] = rs.getInt(i+1);
//					}
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


	public static ArrayList<String[]> uploaddatumASC(Connection conn)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatenuploaddatumASC = new ArrayList<String[]>();

		//SQL-Abfrage
		String READ_DATEN_UPLOADDATUMASC="select uploadid, dateityp, dateiname, autor, tag, uploaddatum, status from uploaddaten order by uploaddatum ASC";

		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_UPLOADDATUMASC);
			rs = pstmt.executeQuery();

			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 7; i++) {
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


	public static ArrayList<String[]> uploaddatumDESC(Connection conn)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatenUploaddatumDESC = new ArrayList<String[]>();
		//ArrayList<String> list = new ArrayList<String>();
		//SQL-Abfrage
		String READ_DATEN_UPLOADDATUMDESC="select uploadid, dateityp, dateiname, autor, tag, uploaddatum, stauts from uploaddaten order by uploaddatum DESC";

		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_UPLOADDATUMDESC);
			rs = pstmt.executeQuery();

			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 7; i++) {
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

	public static ArrayList<String[]> dateinameASC(Connection conn)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachDateinameASC = new ArrayList<String[]>();

		//SQL-Abfrage
		String READ_DATEN_DATEINAMEASC="select uploadid, dateityp, dateiname, autor, tag, uploaddatum, status from uploaddaten order by dateiname ASC";

		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_DATEINAMEASC);
			rs = pstmt.executeQuery();

			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 7; i++) {
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

	public static ArrayList<String[]> dateinameDESC(Connection conn)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> DatennachDateinameDESC = new ArrayList<String[]>();

		//SQL-Abfrage
		String READ_DATEN_DATEINAMEDESC="select uploadid, dateityp, dateiname, autor, tag, uploaddatum, status from uploaddaten order by dateiname DESC";

		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(READ_DATEN_DATEINAMEDESC);
			rs = pstmt.executeQuery();

			while(rs.next())
			{
				String[] zeile = new String[10];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 7; i++) {
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

	public int AnzahlEinträge(Connection conn)
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


	/**
	 * Hier stehen die Methoden für bestimmte Funktionen, Volltextsuche, Text Vereinfachung, Dateien als BLOB in Datenbank speichern und auslesen...
	 */

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
					System.out.print("Der Text Inhalttext wurde vereinfacht zu '"+easyText+"'.");
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
	public static boolean fulltextsearch(String wort,String wort2) {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "select (\'"+wort+"\')@@(\'"+wort2+"\')";
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
	public static float ranking(String wort,String wort2) {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "select ts_rank((\'"+wort+"\')@@ to_tsquery(\'"+wort2+"\')) as relevancy";
		System.out.print(SEARCH_FOR_DATA_SQL_DATEN);
		try {	
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				relevanz=rs.getFloat(1);
				System.out.println("Ist das eingegebene Suchwort, "+wort2+", im Text und mit einer wie hohen relevanz auf den Text?");
				System.out.print(relevanz);

				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return relevanz;
	}

	//TODO verwendetetags Joinen
	public static ArrayList<String[]> ranking2(String wort) {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String SEARCH_FOR_DATA_SQL_DATEN = "SELECT uploadid, dateityp, dateiname, autor, uploaddatum, uploaddatum, status FROM (SELECT uploaddaten.uploadid as uploadid, uploaddaten.dateityp as dateityp, "
				+ "uploaddaten.dateiname as dateiname, uploaddaten.autor as autor, uploaddaten.tag as tag, uploaddaten.uploaddatum as uploaddatum, uploaddaten.status as status,"
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.dateiname), 'A') || "
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.inhalttext), 'B') ||"
				+ " setweight(to_tsvector('simple', uploaddaten.autor), 'C') ||"
				+ " setweight(to_tsvector('simple', coalesce(string_agg(uploaddaten.tag, ' '))), 'B') as document"
				+ " FROM uploaddaten"
				+ " GROUP BY uploaddaten.uploadid, uploaddaten.autor) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', \'"+wort+"\')"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', \'"+wort+"\')) DESC";
		System.out.println(SEARCH_FOR_DATA_SQL_DATEN);
		try {	
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(SEARCH_FOR_DATA_SQL_DATEN);
			rs = pstmt.executeQuery();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return daten;
	}

//	public static void Blobeinfuegen2()
//	{
//		String name="HalloPDF";
//		//File file = new File ((Knackquiz.class.getResource("/view/KnackQuizTransparent.png")));
//		//File file = new File("\\What\\src\\is\\bild.jpg");
//		Path path = FileSystems.getDefault().getPath("bilder", "Hallo.pdf");
//		File file=path.toFile();
//		//File file = new File(getCacheDirectory() + "\\results.txt");
//
//		//	String INSERT_DATA_SQL="INSERT INTO bilder VALUES (?, ?)";
//		String INSERT_DATA_SQL="UPDATE bilder set bild =? WHERE name = '"+name+"'";
//		try {
//
//			FileInputStream fis= new FileInputStream(file);
//			conn = DriverManager.getConnection(DB_URL,USER,PASS);
//			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
//			fis = new FileInputStream(file);
//			//pstmt = conn.prepareStatement("INSERT INTO bilder VALUES (?, ?)");
//			//pstmt.setString(2, "HalloPDF");
//			pstmt.setBinaryStream(1, fis, (int)file.length());
//			pstmt.executeUpdate();
//
//			pstmt.close();
//			fis.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}catch (SQLException e) {
//			e.printStackTrace();
//		}catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		System.out.println("Daten in Datenbank gepeichert.");
//	}

	public static void Blobeinfuegen(Part filePart,String text)
	{
		//TODO: richtigen Pfad angeben 
		//		Path path = FileSystems.getDefault().getPath("bilder", "bild2.jpg");
		//		File file=path.toFile();


		//String INSERT_DATA_SQL="INSERT INTO uploaddaten (blobdatei) VALUES (?)";
		String INSERT_DATA_SQL="UPDATE uploaddaten set blobdatei =? WHERE inhalttext = '"+text+"'";
		try {

			InputStream fis = filePart.getInputStream();
			//			FileInputStream fis= new FileInputStream(file);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
			//			fis = new FileInputStream(file);
			//			pstmt.setString(1, file.getName());
			pstmt.setBinaryStream(2, fis, (int)filePart.getSize());
			pstmt.executeUpdate();

			pstmt.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Daten in Datenbank gepeichert.");
	}


	public byte[] BLOBauslesen(String id)
	{
		//FileOutputStream fos = null;
		byte[] buf=null;
		try {

			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			String query = "SELECT blobdatei, LENGTH(blobdatei) FROM uploaddaten WHERE inhalttext = \'"+id+"\'";
			pstmt = conn.prepareStatement(query);

			ResultSet result = pstmt.executeQuery();
			result.next();
			
			//TODO verallgemeinern
			//fos = new FileOutputStream("C:/Users/veren/Downloads/\'"+dateiname+"\'");

			int len = result.getInt(2);
			buf = result.getBytes("bild");
		//	fos.write(buf, 0, len);

			//          String pfad = "C:/Temp";
			//			File file = createFile(pfad, "bild2.jpg");
			//			Files.copy(file.toPath(), fos);


		} catch (Exception ex) {
			ex.printStackTrace();


		}
		return buf; 

	}

	/**
	 * Hier stehen Methoden zum auslesen der Daten allgemein
	 */

	//Auslesen aller Daten in der Tabelle Uploaddaten
	public static ArrayList<String[]> readDaten(Connection conn) {
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//SQL-Abfrage
		String READ_DATA_SQL_DATEN = "SELECT tag, stichworttext, inhalttext, uploader, autor, dateiname,uploadid,uploaddatum,dateityp FROM uploaddaten where uploadid='1'";
		//opens a connection, 
		try { 
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
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


	public static List<Uploaddaten> readDaten2(Connection conn) {
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		List<Uploaddaten> daten = new ArrayList<>();
		//SQL-Abfrage
		String READ_DATA_SQL_DATEN = "SELECT language, tag, blobdatei, stichworttext, inhalttext, uploader, autor, dateiname,uploadid,uploaddatum FROM uploaddaten";
		//opens a connection, 
		try { 
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
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

				Uploaddaten zeile = new Uploaddaten();
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
		ArrayList<Benutzer> benutzer = new ArrayList<>();
		String SQL="select * from benutzer";

		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			ResultSet rs=pstmt.executeQuery();
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
		String SQL="select * from suchwoerter";

		try {
			PreparedStatement pstmt=conn.prepareStatement(SQL);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				String suchwoert=rs.getString(1);

				Suchwoerter zeile = new Suchwoerter(suchwoert);
				suchwoerter.add(zeile);
			}
			rs.close(); rs=null;
			pstmt.close(); pstmt=null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return suchwoerter;
	}
	
	
	public void Datenlöschen(int id)
	{
		String SQL="delete from uploaddaten where '"+id+"'";
		
		try {
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate(SQL);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void PasswortNeuSetzen(String username, String password)
	{

		String SQL="UPDATE benutzer set passwort ='"+password+"' WHERE benutzername = '"+username+"';";

		try {

			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			wert=false;
		}


	}

	public String[] Dateiname(Connection conn)
	{
		
		String SQL="select dateiname from uploaddaten;";
		int anzahl=AnzahlEinträge(conn);
		String[] spalten = new String[anzahl];
		
		try {
			conn=DriverManager.getConnection(DB_URL,USER,PASS);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return spalten;
		
	}
	
	

}

