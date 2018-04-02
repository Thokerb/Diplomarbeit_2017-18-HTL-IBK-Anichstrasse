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

	//Datenbankreferenzen
	static public String DB_USER = "postgres";
	static public String DB_PASS = "password";
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;

	//Variablen
	private static boolean wert=false;
	private static int anzahl;
	private static float relevanz;


	public DBManager() throws InstantiationException, IllegalAccessException{
		super();
		// Datenbank Driver initialisieren
		try {
			Class.forName(JDBC_DRIVER);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException
	{
		Connection conn = null;
		//neuen Connection aufbauen
		try {
			conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);

		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return conn;
	}

	public void releaseConnection(Connection conn)
	{
		//bestehende Connection löschen
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
	 * @param uploaddaten
	 * @return
	 */
	public static boolean writeDaten(Connection conn,Daten uploaddaten, Part filePart){
		InputStream fis;
		boolean erfolg = true;
		//SQL-Operation zum hineinschreiben neuer Daten
		String SQL = "INSERT INTO uploaddaten (inhalttext, uploader, autor, dateiname, stichworttext, dateityp, status, dokumentdatum, uploaddatum, blobdatei,zustand) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

		try {
			pstmt = conn.prepareStatement(SQL);
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


	public void updateZustandloeschen(Connection conn, int uploadid) {
		//SQL-Operation zum Updaten der Daten
		String SQL = "update uploaddaten set zustand = 'false', deletedatum = ?, status='private' where uploadid = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, getaktuellesDatum());
			pstmt.setInt(2, uploadid);
			pstmt.executeUpdate();

			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateZustandwiederherstellen(Connection conn, int uploadid) {
		//SQL-Operation zum Updaten der Daten
		String SQL = "update uploaddaten set zustand = 'true', deletedatum = '' where uploadid = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, uploadid);
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

	public void writeStichwörter(Connection conn,String wort,String username)
	{
		//SQL-Operation zum Hineinschreiben der Suchwörter in die Datenbank
		String SQL="Insert into suchwoerter (Suchwort,Benutzer) VALUES (?,?)";

		try {
			pstmt=conn.prepareStatement(SQL);

			pstmt.setString(1, wort);
			pstmt.setString(2, username);
			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Hier werden die Methoden zur geordneten Reihenfolge für die Antwort in den DataTableServlet geschrieben und andere SELECT Abfragen
	 * @return
	 */

	public ArrayList<Daten> meineDaten(Connection conn,String uploader,String spalte,String reihung)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<Daten> DatenSortiertPrivate = new ArrayList<>();
		//SQL-Abfrage zum Auslesen der privaten sortierten Daten
		String SQL = "select uploadid,dateityp, dateiname, autor, uploaddatum, dokumentdatum, status from uploaddaten where uploader= ? and zustand='true' order by "+spalte+" "+reihung+";";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, uploader);
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
		//SQL-Abfrage zum Auslesen der public sortierten Daten
		String SQL="select uploadid,dateityp, dateiname, uploader, autor, uploaddatum, dokumentdatum, status from uploaddaten where status='public' and zustand='true' order by "+spalte+" "+reihung+";";

		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
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

	public ArrayList<Daten> geloeschteDaten(Connection conn,String sortierparameter,String spalte,String reihung)
	{
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<Daten> geloeschteDaten = new ArrayList<Daten>();
		//SQL-Abfrage zum Auslesen der gelöschten sortierten Daten
		String SQL="select uploadid, dateityp, dateiname, autor, deletedatum, uploaddatum, dokumentdatum from uploaddaten where uploader=? and zustand='false' order by "+spalte+" "+reihung+";";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, sortierparameter);
			rs = pstmt.executeQuery();
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

	public int AnzahlEinträge(Connection conn,String user)
	{
		String SQL="select count(*) from uploaddaten JOIN benutzer ON(uploaddaten.uploader = benutzer.benutzername) WHERE benutzername = ?;";

		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, user);
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

	public int AnzahlEinträgeDaten(Connection conn,String spalte,String spalteninhalt,boolean zustand)
	{
		String SQL="select count(uploadid) from uploaddaten where "+spalte+"=? and zustand=?";

		System.out.println(SQL);
		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, spalteninhalt);
			pstmt.setBoolean(2, zustand);
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
	public String VereinfachterTextGenerator(Connection conn, String wort) {
		/**
		 * Methode zur vereinfachung des Suchwortes und Speicherung in der Datenbank
		 * @param wort
		 * @return der Rückgabewert ist die vereinfachte Form des ursprünglichen Suchwortes
		 * 		   Hierbei werden alle Großbuchstaben durch Kleinbuchstaben ersetzt; Präpositionen,
		 * 		   Artikel, Personalpronomen,Nachsilben,.. fallen weg;
		 */
		//SQL-Operation
		String SQL = "select to_tsvector(?);";
		String vereinfachtertext = null;
		try {
			if(pstmt==null){
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, wort);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					vereinfachtertext=(rs.getString(1));
				}
			}

			rs.close();rs=null;
			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vereinfachtertext;
	}

	//Überprüft ob ein bestimmtes wort im text ist
	public boolean  tsearch(Connection conn,String wort,String wort2) {

		//SQL-Operation zum Abfragen ob ein Wort in einem Text ist
		String SQL = "select (?)@@(?)";

		try {	
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, wort);
			pstmt.setString(2, wort2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				wert=rs.getBoolean(1);
			}

			pstmt.close(); pstmt=null;
			rs.close(); rs=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wert;
	}

	//Relevanz eines Wortes im Bezug auf das Suchwort zum Text
	public float ranking(Connection conn,String wort,String wort2) {

		//SQL-Operation
		String SQL = "select ts_rank((?)@@ to_tsquery(?)) as relevancy";

		try {	
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, wort);
			pstmt.setString(2, wort2);
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

	//Volltextsuche der privaten Dokumente
	public ArrayList<Daten> durchsuchenPrivate(Connection conn, String wort,String username) {
		ArrayList<Daten> daten = new ArrayList<Daten>();
		//SQL-Abfrage
		String SQL = "SELECT uploadid, dateityp, dateiname, autor, uploaddatum, dokumentdatum, status "
				+ "FROM (SELECT uploaddaten.uploadid as uploadid,"
				+ "uploaddaten.dateityp as dateityp, uploaddaten.dateiname as dateiname, uploaddaten.dokumentdatum as dokumentdatum, uploaddaten.uploaddatum as uploaddatum,"
				+ "uploaddaten.status as status, uploaddaten.autor as autor, uploaddaten.uploader as uploader, uploaddaten.zustand as zustand,"
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.dateiname), 'A') || "
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.inhalttext), 'B') ||"
				+ " setweight(to_tsvector('simple', uploaddaten.autor), 'C') as document"
				+ " FROM uploaddaten) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', ?) and uploader=? and zustand='true'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', ?)) DESC";

		try {	
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, wort);
			pstmt.setString(2, username);
			pstmt.setString(3, wort);
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

	//Volltextsuche der privaten Dokumente
	public ArrayList<Daten> durchsuchenPrivate2(Connection conn, String wort,String username) {
		ArrayList<Daten> daten = new ArrayList<Daten>();
		//SQL_Abfrage
		String SQL = "SELECT uploadid, dateityp, dateiname, autor, uploaddatum, dokumentdatum, status FROM (SELECT uploaddaten.uploadid as uploadid,"
				+ "uploaddaten.dateityp as dateityp, uploaddaten.dateiname as dateiname, uploaddaten.dokumentdatum as dokumentdatum, uploaddaten.uploaddatum as uploaddatum,"
				+ "uploaddaten.status as status, uploaddaten.autor as autor, uploaddaten.uploader as uploader, uploaddaten.zustand as zustand, uploaddaten.stichworttext as stichworttext"
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.dateiname), 'A') || "
				+ " setweight(uploaddaten.language::regconfig, uploaddaten.stichworttext), 'B') ||"
				+ " setweight(to_tsvector('simple', uploaddaten.autor), 'C') as document"
				+ " FROM uploaddaten) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', ?) and uploader=? and zustand='true'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', ?)) DESC";

		try {	
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, wort);
			pstmt.setString(2, username);
			pstmt.setString(3, wort);
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

	//Volltextsuche der gelöschten Dokumente
	public ArrayList<Daten> durchsuchenGeloeschte(Connection conn, String wort,String username) {
		ArrayList<Daten> daten = new ArrayList<Daten>();
		//SQL-Abfrage
		String SQL = "SELECT uploadid, dateityp, dateiname, autor, uploaddatum, dokumentdatum, deletedatum status FROM (SELECT uploaddaten.uploadid as uploadid, uploaddaten.dateityp as dateityp, "
				+ "uploaddaten.dateiname as dateiname, uploaddaten.dokumentdatum as dokumentdatum, uploaddaten.uploaddatum as uploaddatum, uploaddaten.status as status, uploaddaten.autor as autor, uploaddaten.uploader as uploader, uploaddaten.deletedatum as deletedatum, uploaddaten.zustand as zustand,"
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.dateiname), 'A') || "
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.inhalttext), 'B') ||"
				+ " setweight(to_tsvector('simple', uploaddaten.autor), 'C') as document"
				+ " FROM uploaddaten"
				+ " GROUP BY uploaddaten.uploadid) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', ?) and uploader=? and zustand='false'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', ?)) DESC";
		try {	
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, wort);
			pstmt.setString(2, username);
			pstmt.setString(3, wort);
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

	//Volltextsuche der public Dokumente
	public ArrayList<Daten> durchsuchenPublic(Connection conn, String wort) {
		ArrayList<Daten> daten = new ArrayList<Daten>();
		//SQL-Abfrage
		String SQL = "SELECT uploadid, dateityp, dateiname, uploader,autor, uploaddatum, dokumentdatum, status FROM (SELECT uploaddaten.uploadid as uploadid, uploaddaten.dateityp as dateityp, "
				+ "uploaddaten.dateiname as dateiname, uploaddaten.autor as autor, uploaddaten.dokumentdatum as dokumentdatum, uploaddaten.uploaddatum as uploaddatum, uploaddaten.status as status, uploaddaten.uploader as uploader, uploaddaten.zustand as zustand,"
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.dateiname), 'A') || "
				+ " setweight(to_tsvector(uploaddaten.language::regconfig, uploaddaten.inhalttext), 'B') ||"
				+ " setweight(to_tsvector('simple', uploaddaten.autor), 'C') as document"
				+ " FROM uploaddaten"
				+ " GROUP BY uploaddaten.uploadid) p_search"
				+ " WHERE p_search.document @@ to_tsquery('german', ?) and status='public' and zustand='true'"
				+ " ORDER BY ts_rank(p_search.document, to_tsquery('german', ?)) DESC";

		try {	
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, wort);
			pstmt.setString(2, wort);
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

	//Methode zum Auslesen des BLOB-Objektes
	public byte[] BLOBauslesen(Connection conn,int id)
	{
		byte[] buf=null;
		//SQL-Abfrage
		String SQL = "SELECT blobdatei, LENGTH(blobdatei) FROM uploaddaten WHERE uploadid = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
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

	//Auslesen aller Daten in der Tabelle Uploaddaten
	public ArrayList<String[]> readDaten(Connection conn, int id) {
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//SQL-Abfrage
		String SQL = "SELECT loeschid, dateiname, autor, uploader, inhalttext, stichworttext,blobdatei,tag FROM uploaddaten where uploadid=?"; 
		try { 
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
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

	//Auslesen aller Autoren
	public ArrayList<String[]> readAutoren(Connection conn) {
		//generieren einer ArrayList zum Zwischenspeichern von den Werten aus der Datenbank
		ArrayList<String[]> Autoren = new ArrayList<String[]>();
		//SQL-Abfrage
		String SQL = "select autor from uploaddaten group by autor";
		try { 
			pstmt = conn.prepareStatement(SQL);
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

	//Auslesen aller Dateinamen
	public ArrayList<String[]> readDateinamen(Connection conn) {

		ArrayList<String[]> dateinamen = new ArrayList<String[]>();
		//SQL-Abfrage
		String SQL = "select dateiname from uploaddaten group by dateiname";
		try {
			pstmt = conn.prepareStatement(SQL);
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

	//Auslesen aller Benutzer
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
				String passwort=rs.getString(3);

				Benutzer zeile = new Benutzer(benutzername,email,passwort);
				benutzer.add(zeile);
			}
			rs.close(); rs=null;
			pstmt.close(); pstmt=null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return benutzer;
	}

	//Aulesen von den letzten 3 Suchwörtern
	public List<Suchwoerter> readSuchwoerter (Connection conn) throws SQLException
	{
		ArrayList<Suchwoerter> suchwoerter = new ArrayList<>();
		//SQL-Operation
		String SQL="select * from suchwoerter order by suchwortid DESC LIMIT 3";

		try {
			pstmt=conn.prepareStatement(SQL);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				String suchwoert=rs.getString(2);
				int suchwortid=rs.getInt(1);
				String benutzer=rs.getString(3);

				Suchwoerter zeile = new Suchwoerter(suchwoert,suchwortid,benutzer);
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

	//Löschen eines Dokumentes mittels Angabe der Uploadid
	public void Datenlöschen(Connection conn,int id)
	{
		//SQL-Operation
		String SQL="delete from uploaddaten where uploadid=?;";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();

			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void PasswortNeuSetzen(Connection conn,String username, String password)
	{
		//SQL-Operation
		String SQL="UPDATE benutzer set passwort =? WHERE benutzername = ?;";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, password);
			pstmt.setString(2, username);
			pstmt.executeUpdate();

			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
			wert=false;
		}


	}

	public void RegisterBenutzer(Connection conn, Benutzer benutzer) throws ClassNotFoundException, SQLException
	{
		//SQL-Operation
		String SQL = "INSERT into benutzer (benutzername,email,passwort) values(?,?,?);";
		PreparedStatement ps = conn.prepareStatement( SQL);  

		ps.setString(1,benutzer.getBenutzername());
		ps.setString(2,benutzer.getEmail()); 
		ps.setString(3,benutzer.getPasswort()); 

		int i = ps.executeUpdate();
		System.out.println(SQL);

		if(i > 0 ) System.out.println("error");

	}

	public String getEmailByUser(Connection conn,String user) 
	{
		//SQL-Abfrage
		String SQL="SELECT email from benutzer WHERE benutzername = ?";
		String email=null;

		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, user);
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

	public String getUserByEmail(Connection conn,String emailuser)
	{
		//SQL-Abfrage
		String SQL="SELECT benutzername FROM benutzer WHERE email =?";
		String benutzername=null;

		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, emailuser);
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


	public String getUser(Connection conn,String username)
	{
		//SQL-Abfrage
		String SQL="select benutzername from benutzer where benutzername like ?;";
		String user=null;

		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, username);
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



	public String getEmail(Connection conn,String em)
	{
		//SQL-Abfrage
		String SQL="select email from benutzer where email like ?";
		String email=null;

		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, em);
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

	public String[] Dateiname(Connection conn, String username)
	{
		//SQL-Abfrage
		String SQL="select dateiname from uploaddaten JOIN benutzer ON(uploaddaten.uploader = benutzer.benutzername) WHERE benutzername = ?;";

		String[] spalten = new String[anzahl];

		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, username);
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
		//SQL-Operation zum Hineinschreiben des Hash-Codes
		String SQL="UPDATE benutzer set authcode =? WHERE email =?;";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, authcode);
			pstmt.setString(2, emailuser);
			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public  boolean CodeCheck(Connection conn,String hashcode) {

		List<String> vorhandeneHash = new ArrayList<String>();
		//SQL-Abfrage zum Auslesen des Hash-Codes
		String SQL="select authcode from benutzer;";

		try {
			pstmt = conn.prepareStatement(SQL);
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

		//SQL-Abfrage zum Auslesen des Benutzernamens nach Hash-Code
		String SQL="select benutzername from benutzer where authcode =?;";
		String user = "";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, hashcode);
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
		//SQL-Operation zum Ändern der Zugriffszustandes
		String SQL="UPDATE uploaddaten set status =? WHERE uploadid = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, status);
			pstmt.setInt(2, id);
			pstmt.executeUpdate();

			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Daten in Datenbank gepeichert.");
	}

	//löschen der Uploaddaten nach Benutzername
	public void deletebyname(String dateiname,String username, Connection conn)
	{
		//SQL-Operation
		String SQL = "delete from uploaddaten where dateiname =? AND uploader = ?;";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, dateiname);
			pstmt.setString(2, username);
			pstmt.executeUpdate();

			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkUser(Connection conn, String username, String pwd) {

		boolean st = false; 
		//SQL-Abfrage
		String SQL = "SELECT * FROM benutzer where benutzername=? and passwort=?";

		System.out.println("Connecting to database...");

		try {
			pstmt = conn.prepareStatement(SQL);

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

	public void deletehash(Connection conn, String username) 
	{
		//SQL-Operation
		String SQL = "update benutzer set authcode = null where benutzername = ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, username);
			pstmt.executeUpdate();

			pstmt.close(); pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getDateiinfo(int id,Connection con) {
		//SQL-Abfrage
		String SQL = "Select * from uploaddaten where uploadid = ?";
		String uploader = null;
		try {
			pstmt = con.prepareStatement(SQL);
			pstmt.setInt(1, id);
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
