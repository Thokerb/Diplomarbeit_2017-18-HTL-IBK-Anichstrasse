package model;

import java.sql.*;
import java.text.ParseException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

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
	private static String[] testzeile = new String[]{"Nachhilfe","fhjdskfhdsuifhusdifhdshfisfhjisd fjdisfjhsidf sfjidsofhisd fjdisofhjisdo hdisofhsid","Verena","Verena","Wow a kastaniet<", "'2017-11-25'"};
	private static String easySuchwort2;

	//Variablen relevant für Methoden

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		String such=FunktionenDB.getSuchwort();
		FunktionenDB.VereinfachtesSuchwortgenerator(such);
		String suchwort=FunktionenDB.getEasySuchwort();
		Suchwort(suchwort);
		System.out.println(easySuchwort2);
		 */
		//writeStichwörter(easySuchwort2);
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
				pstmt.setString(i, testzeile2[i-1]);
				System.out.println(" '" + testzeile2[i-1] + "'");
			}
			pstmt.executeUpdate();

			pstmt.close();pstmt=null;
		} catch (SQLException e) {
			e.printStackTrace();
			erfolg = false;
		}
		return erfolg;

	}

	private static String Suchwort(String name){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

/*
	public void Datum()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date parsed = null;
		try {
			parsed = sdf.parse("20140912");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		java.sql.Date data = new java.sql.Date(parsed.getTime());

		// Contato DataNascimento era Calendar
		//contato.setDataNascimento(Calendar.getInstance());         

		// grave nessa conexão!!! 
		ContatoDao dao = new ContatoDao("mysql");           

		// método elegante 
		dao.adiciona(contato); 
		System.out.println("Banco: ["+dao.getNome()+"] Gravado! Data: "+contato.getDataNascimento());

	}

	public static void dolm() throws ParseException 
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date parsed = format.parse("20110210");
		java.sql.Date sql = new java.sql.Date(parsed.getTime());
	}

*/


}
