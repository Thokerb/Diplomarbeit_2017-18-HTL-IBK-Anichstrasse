package is;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BLOB {

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

	public static void main(String[] args) throws SQLException, IOException {

		Connection con = DriverManager.getConnection(DB_URL,USER, PASS);

		con.setAutoCommit(true);
		String sql ="insert into biler (name, bild) values (?,?)";

		PreparedStatement pstmt = null;
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, 101);

		File f1 = new File("pic1.jpg");
		Blob blob = con.createBlob();
		FileInputStream fis = new FileInputStream(f1);
		OutputStream out = blob.setBinaryStream(1);
		int i = -1;
		while ((i = fis.read()) != -1) {
			out.write(i);
		}

		pstmt.setBlob(3, blob);
		pstmt.executeUpdate();

	}
	
	public BLOB() throws InstantiationException, IllegalAccessException{
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
			System.out.println("Fehler beim Schlieﬂen der Verbindung:");
			System.out.println("Meldung: "+e.getMessage());
			e.printStackTrace();
		}
	}
}
