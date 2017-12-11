package model;

import java.sql.*;
import java.text.ParseException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HineinschreibenDB {

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
	private static String[] testzeile = new String[]{"Nachhilfe","fhjdskfhdsuifhusdifhdshfisfhjisd fjdisfjhsidf sfjidsofhisd fjdisofhjisdo hdisofhsid","Verena","Verena","Wow a kastanie", "2017-11-25"};
	private static String easySuchwort2;
	//private static date;

	//Variablen relevant für Methoden

	public static void main(String[] args) {
		/*
		String such=FunktionenDB.getSuchwort();
		FunktionenDB.VereinfachtesSuchwortgenerator(such);
		String suchwort=FunktionenDB.getEasySuchwort();
		Suchwort(suchwort);
		System.out.println(easySuchwort2);
		 */
		//writeStichwörter(easySuchwort2);
		
		//date=convertDate("2017-11-25");
		writeDaten(testzeile);

	}

	public HineinschreibenDB() throws InstantiationException, IllegalAccessException{
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

	// TODO fehler bei  uploaddatum, kann nicht hineingeschrieben werden
	//Neue Daten in Datebantabelle schreiben.
	public static boolean writeDaten(String[] testzeile2) {

		boolean erfolg = true;
		//SQL-Abfrag zum hineinschreiben neuer Daten
		String INSERT_DATA_SQL = "INSERT INTO uploaddaten ( tag, inhalttext, uploader, autor, dateiname, uploaddatum, stichworttext, dateityp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		//connection Aufbau
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);

			//System.out.println("HIIIIIII"); //zur Kontrolle

			for (int i = 1; i <= 8; i++) {
				pstmt.setString(i, testzeile2[i-1]);
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


}
