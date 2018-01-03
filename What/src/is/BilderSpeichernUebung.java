package is;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BilderSpeichernUebung {

	static final String JDBC_DRIVER = "org.postgresql.Driver";  
	static final String DB_URL = "jdbc:postgresql://localhost/UebungenBilder";

	//  Database credentials
	static final String USER = "postgres";
	static final String PASS = "password";
	//private static String dbUrl = "jdbc:postgresql://localhost:5432/diplomarbeit?user=postgres&password=password";
	static Connection conn = null;
	static Statement stmt = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;

	public static void main(String[] args) {
		Bildeinfuegen();
	}

	public BilderSpeichernUebung() throws InstantiationException, IllegalAccessException{
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
			System.out.println("Verbindung aufbauen");

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
			System.out.println("Fehler beim schlieﬂen der Verbindung:");
			System.out.println("Meldung: "+e.getMessage());
			e.printStackTrace();
		}
	}

	public static void Bildeinfuegen()
	{
		File file = new File("bilder/bild.jpg");
		FileInputStream fis;
		String INSERT_DATA_SQL="INSERT INTO bilder VALUES (?, ?)";
		try {

			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
			fis = new FileInputStream(file);
			//pstmt = conn.prepareStatement("INSERT INTO bilder VALUES (?, ?)");
			pstmt.setString(1, file.getName());
			pstmt.setBinaryStream(2, fis, file.length());
			pstmt.executeUpdate();

			pstmt.close();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public static void BildAuslesen()
	{
		try{
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			PreparedStatement ps = conn.prepareStatement("SELECT bild FROM bilder WHERE name = ?");
			ps.setString(1, "bilder/bild.jpg");
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				while (rs.next()) {
					byte[] imgBytes = rs.getBytes(1);
					// verwenden Sie die Daten hier irgendwie
				}
				rs.close();
			}
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
