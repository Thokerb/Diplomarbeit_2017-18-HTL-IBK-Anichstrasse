

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.DBManager;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//	// size of byte buffer to send file
	//	private static final int BUFFER_SIZE = 4096;   
	//
	//	// database connection settings
	//	static final String JDBC_DRIVER = "org.postgresql.Driver"; 
	//	private String dbURL =  "jdbc:postgresql://localhost/diplomarbeit";
	//	private String dbUser = "postgres";
	//	private String dbPass = "password";


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn=null;

		String antwort = request.getParameter("download");
		System.out.println("DOWNLOADREQUEST: "+antwort);

		String fileName = "";
		String fileType = ""; //mehrere arten? 

		try {

			//Verena:
			DBManager db=new DBManager();
			conn=db.getConnection();

			//TODO den richtigen dateinamen angeben und inhalttext
			//file?? header setzen des ausiflusehn


			db.BLOBauslesen("dateinamen","inhalttext"); //TODO können diese Parameter auch so hergeholt werden für weiterverarbetiung? 

		} catch (SQLException ex) {
			ex.printStackTrace();
			response.getWriter().print("SQL Error: " + ex.getMessage());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				// closes the database connection
				try {
					conn.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}          
		}

		response.setContentType(fileType);

		response.setHeader("Content-disposition","attachment; filename=yourcustomfilename.pdf");

		File my_file = new File(fileName); //dateinamen?  https://stackoverflow.com/questions/1442893/implementing-a-simple-file-download-servlet
				
		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(my_file);
		byte[] buffer = new byte[4096];
		int length;
		
		while ((length = in.read(buffer)) > 0){
			out.write(buffer, 0, length);
		}
		
		in.close();
		out.flush();


	}

}
