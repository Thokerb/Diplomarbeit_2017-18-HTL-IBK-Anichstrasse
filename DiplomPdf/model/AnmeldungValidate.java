import java.sql.*;

public class AnmeldungValidate {

	static final String JDBC_DRIVER = "org.postgresql.Driver";  
	static final String DB_URL = "jdbc:postgresql://localhost/diplomarbeit";

	static final String USER = "postgres";
	static final String PASS = "password";

	static Connection conn = null;

	public static boolean checkUser(String username, String pwd) {

		boolean st = false; 
		String sql = "SELECT * FROM benutzer where benutzername=? and passwort=?";

		System.out.println("Connecting to database...");

		try {

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			System.out.println("Connecting successful");

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1,  username);
			ps.setString(2,  pwd);

			ResultSet rs = ps.executeQuery(); // st boolean? 

			st = rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connecting not successful");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return st; 

	}


}