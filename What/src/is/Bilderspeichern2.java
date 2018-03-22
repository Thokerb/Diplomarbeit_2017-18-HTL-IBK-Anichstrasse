package is;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

public class Bilderspeichern2 {
	
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
				
				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				// Alle LargeObject-Aufrufe m¸ssen in einem Transaktionsblock stehen
				conn.setAutoCommit(false);

				// Erzeuge einen Large-Object-Manager
				LargeObjectManager lobj = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();

				// Erzeuge ein neues Large Object
				int oid = lobj.create(LargeObjectManager.READ | LargeObjectManager.WRITE);

				// ÷ffne das Large Object zum Schreiben
				LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE);

				// ÷ffne die Datei
				Path path = FileSystems.getDefault().getPath("bilder", "bild2.jpg");
				File file=path.toFile();
				FileInputStream fis = new FileInputStream(file);

				// Kopiere die Daten aus der Datei in das Large Object
				byte buf[] = new byte[2048];
				int s, tl = 0;
				while ((s = fis.read(buf, 0, 2048)) > 0) {
				    obj.write(buf, 0, s);
				    tl += s;
				}

				// Schlieﬂe das Large Object
				obj.close();

				// F¸ge die Zeile in die Tabelle ein
				PreparedStatement ps = conn.prepareStatement("INSERT INTO bilder VALUES (?, ?)");
				ps.setString(1, "name");
				ps.setInt(2, oid);
				ps.executeUpdate();
				System.out.println("In Datenbank gespeichert!");
				ps.close();
				fis.close();
		
		
	}

	public Bilderspeichern2() throws InstantiationException, IllegalAccessException{
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
	
	public static void Bildspeichern() throws IOException, SQLException
	{
		// Alle LargeObject-Aufrufe m√ºssen in einem Transaktionsblock stehen
		conn.setAutoCommit(false);

		// Erzeuge einen Large-Object-Manager
		LargeObjectManager lobj = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();

		// Erzeuge ein neues Large Object
		int oid = lobj.create(LargeObjectManager.READ | LargeObjectManager.WRITE);

		// ÷ffne das Large Object zum Schreiben
		LargeObject obj = lobj.open(oid, LargeObjectManager.WRITE);

		// ÷ffne die Datei
		File file = new File("bild.jpg");
		FileInputStream fis = new FileInputStream(file);

		// Kopiere die Daten aus der Datei in das Large Object
		byte buf[] = new byte[2048];
		int s, tl = 0;
		while ((s = fis.read(buf, 0, 2048)) > 0) {
		    obj.write(buf, 0, s);
		    tl += s;
		}

		// Schlieﬂe das Large Object
		obj.close();

		// F√ºge die Zeile in die Tabelle ein
		PreparedStatement ps = conn.prepareStatement("INSERT INTO bilder VALUES (?, ?)");
		ps.setString(1, file.getName());
		ps.setInt(2, oid);
		ps.executeUpdate();
		ps.close();
		fis.close();
	}
	
	public void BilderAuslesen() throws SQLException
	{
		// Alle LargeObject-Aufrufe m√ºssen in einem Transaktionsblock stehen
		conn.setAutoCommit(false);

		// Erzeuge einen Large-Object-Manager
		LargeObjectManager lobj = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();

		PreparedStatement ps = conn.prepareStatement("SELECT bildoid FROM bilder WHERE name = ?");
		ps.setString(1, "meinbild.gif");
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
		    while (rs.next()) {
		        // √ñffne das Large Object zum Schreiben
		        int oid = rs.getInt(1);
		        LargeObject obj = lobj.open(oid, LargeObjectManager.READ);

		        // Lese die Daten
		        byte buf[] = new byte[obj.size()];
		        obj.read(buf, 0, obj.size());
		        // Verwenden Sie die Daten hier irgendwie

		        // Schlie√üe das Objekt
		        obj.close();
		    }
		    rs.close();
		}
		ps.close();
	}

}