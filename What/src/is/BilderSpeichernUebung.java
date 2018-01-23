package is;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

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

	public static void main(String[] args){
		//Bildeinfuegen();
		//BildAuslesen();
		//Auslesen();
		lesen();
		//Bildeinfuegen();
		
		//Bildeinfuegen2();
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
		String name="HalloPDF";
		//File file = new File ((Knackquiz.class.getResource("/view/KnackQuizTransparent.png")));
		//File file = new File("\\What\\src\\is\\bild.jpg");
		Path path = FileSystems.getDefault().getPath("bilder", "Hallo.pdf");
		File file=path.toFile();
		//File file = new File(getCacheDirectory() + "\\results.txt");
		
		String INSERT_DATA_SQL="INSERT INTO bilder VALUES (?, ?)";
		//String INSERT_DATA_SQL="UPDATE bilder set bild =? WHERE name = '"+name+"'";
		try {

			FileInputStream fis= new FileInputStream(file);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
			fis = new FileInputStream(file);
			//pstmt = conn.prepareStatement("INSERT INTO bilder VALUES (?, ?)");
			//pstmt.setString(2, "HalloPDF");
			pstmt.setBinaryStream(1, fis, (int)file.length());
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

		System.out.println("Daten in Datenbank gepeichert.");
	}
	
	public static void Bildeinfuegen2()
	{
		String name="HalloPDF";
		//File file = new File ((Knackquiz.class.getResource("/view/KnackQuizTransparent.png")));
		//File file = new File("\\What\\src\\is\\bild.jpg");
		Path path = FileSystems.getDefault().getPath("bilder", "Hallo.pdf");
		File file=path.toFile();
		//File file = new File(getCacheDirectory() + "\\results.txt");
		
		String INSERT_DATA_SQL="INSERT INTO bilder VALUES (?, ?)";
		try {

			FileInputStream fis= new FileInputStream(file);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			pstmt = conn.prepareStatement(INSERT_DATA_SQL);
			fis = new FileInputStream(file);
			//pstmt = conn.prepareStatement("INSERT INTO bilder VALUES (?, ?)");
			pstmt.setString(1, "HalloPDF1");
//			pstmt.setBinaryStream(2, fis, file.length());braucht ma nit wirklich
			pstmt.setBinaryStream(2, fis, (int)file.length());
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

		System.out.println("Daten in Datenbank gepeichert.");
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
					String name= rs.getString(1);
					byte[] imgBytes = rs.getBytes(1);
					
					System.out.println(name);
				}
				rs.close();
			}
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void Auslesen()
	{
		// Get the Large Object Manager to perform operations with
		LargeObjectManager lobj;
		try {
			conn= DriverManager.getConnection(DB_URL,USER,PASS);
			lobj = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
			PreparedStatement ps = conn.prepareStatement("SELECT bild FROM bilder WHERE bild = ?");
			ps.setString(1, "Hallo.pdf");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			    // Open the large object for reading
				String name = rs.getString(1);
			    int oid = rs.getInt(2);
			    LargeObject obj = lobj.open(oid, LargeObjectManager.READ);

			    // Read the data
			    byte buf[] = new byte[obj.size()];
			    obj.read(buf, 0, obj.size());
			    // Do something with the data read here
			    
			    String pfad = "C:/Temp";
				File file = createFile(pfad, "Hallo.pdf");
				Files.copy(obj.getInputStream(), file.toPath());
			
			    
			    // Close the object
			    obj.close();
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public static void lesen()
 	{
 		FileOutputStream fos = null;
 		try {
 
             conn = DriverManager.getConnection(DB_URL, USER, PASS);
 
            String query = "SELECT bild, LENGTH(bild) FROM bilder WHERE name = 'Hallo.pdf'";
             pstmt = conn.prepareStatement(query);
 
             ResultSet result = pstmt.executeQuery();
             result.next();
 
   //          fos = new FileOutputStream("C:/Temp/Thomas1.pdf");
 
             int len = result.getInt(2);
             byte[] buf = result.getBytes("bild");
    //         fos.write(buf, 0, len);
             
             System.out.println("Bytes ausgeben:");
             System.out.println(buf);
 //          String pfad = "C:/Temp";
 //			File file = createFile(pfad, "bild2.jpg");
 //			Files.copy(file.toPath(), fos);
 
 
         } catch (Exception ex) {
         	ex.printStackTrace();
             
 
         } 
   
     }
	
	
	
	private static File createFile(String pfad, String name){
		File uploads = new File(pfad);
		File file = new File(uploads, name);
		return file;

	}

	public static void loeschen()
	{
		int foovalue = 500;
		
		try {
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			PreparedStatement st = conn.prepareStatement("DELETE FROM bilder WHERE columnfoo = ?");
			st.setInt(1, foovalue);
			int rowsDeleted = st.executeUpdate();
			System.out.println(rowsDeleted + " rows deleted");
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
