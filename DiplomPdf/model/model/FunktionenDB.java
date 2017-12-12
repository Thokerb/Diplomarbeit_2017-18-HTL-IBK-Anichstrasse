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
			private static float relevanz;
			
			public static ArrayList<String[]> sortiertAutorASC = new ArrayList<String[]>();

	public static void main(String[] args) {
		
//		Stichtextgenerator(text);
//		VereinfachtesSuchwortgenerator(suchwort);
//		fulltextsearch(text,wort);

		
//		easySuchwort2=HineinschreibenDB.Suchwort(easySuchwort);
//		System.out.println(easySuchwort2);
//		ranking(easyText,easySuchwort2);
	
		

		geordneteAusgabe.autorASC();
		setsortiertAutorASC();
		
		for(int i=0;i<=2;i++)
		{
			System.out.println(sortiertAutorASC.get(i)[0]);
			System.out.println("HI");
			System.out.println(sortiertAutorASC.get(i)[1]);
			System.out.println(sortiertAutorASC.get(i)[2]);
			System.out.println(sortiertAutorASC.get(i)[3]);
			System.out.println(sortiertAutorASC.get(i)[4]);
		}
		
		
		int anzahl=geordneteAusgabe.AnzahlEinträge();
		System.out.println(anzahl);
		
		System.out.println("String antwortautorASC =[{\"DateiTyp\":\""+sortiertAutorASC.get(0)[0]+"\",\"Name\":\""+sortiertAutorASC.get(0)[1]+"\",\"Autor\":\""+sortiertAutorASC.get(0)[2]+"\",\"UploadDatum\":\""+sortiertAutorASC.get(0)[3]+"\",\"DokumentDatum\":\""+sortiertAutorASC.get(0)[4]+"\"}");
		String antwortautorASC = "{\"draw\":,\"recordsTotal\":"+anzahl +",\"recordsFiltered\":"+anzahl +",\"data\":[{\"DateiTyp\":\""+sortiertAutorASC.get(0)[0]+"\",\"Name\":\""+sortiertAutorASC.get(0)[1]+"\",\"Autor\":\""+sortiertAutorASC.get(0)[2]+"\",\"UploadDatum\":\""+sortiertAutorASC.get(0)[3]+"\",\"DokumentDatum\":\""+sortiertAutorASC.get(0)[4]+"\"}";
		System.out.println(antwortautorASC);
		
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


		public static void setsortiertAutorASC()
		{
			sortiertAutorASC = geordneteAusgabe.autorASC();

		}
	

}
