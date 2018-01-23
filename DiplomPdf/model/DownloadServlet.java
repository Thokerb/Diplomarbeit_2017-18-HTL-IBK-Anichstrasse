

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
import com.google.gson.JsonObject;

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


		response.setHeader("Content-Disposition",
				"attachment;filename=downloadname.txt");

		response.setHeader("Content-disposition","attachment; filename=yourcustomfilename.pdf");

		String fileName = "";
		String fileType = ""; //mehrere arten? 

		Connection conn=null;

		String antwort = request.getParameter("download");
		System.out.println("DownloadRequest: "+antwort);

		Gson gsn = new Gson();
		JsonObject jobj; 

		jobj = gsn.fromJson(antwort, JsonObject.class);
		String idObj = jobj.get("ID").getAsString();
		System.out.println("ID: "+ idObj);
		switch(fileType){

		case "PDF"  :{
			response.setContentType("application/pdf");
			break;
		}
		case "DOCX"  :{

			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			break;
		}
		case "DOC"  :{

			response.setContentType("application/msword");
			break;
		}
		case "TXT"  :{
			response.setContentType("text/plain");
			break;
		}
		default:{

			System.out.println("Datei kann nicht gespeichert werden, Dateityp "+ fileType +"wird nicht unterstützt");
		}



		try {

			//Verena:
			DBManager db=new DBManager();
			conn = db.getConnection();

			//TODO den richtigen dateinamen angeben und inhalttext
			//file?? header setzen des ausiflusehn


			//			db.BLOBauslesen("dateinamen","inhalttext"); //TODO können diese Parameter auch so hergeholt werden für weiterverarbetiung? 

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



		File my_file = new File(fileName); //dateinamen?  https://stackoverflow.com/questions/1442893/implementing-a-simple-file-download-servlet

		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(my_file);
		//		byte[] buffer = db.BLOBauslesen("dateinamen","inhalttext");
		//		byte[] buffer = db.getBLOB();;
		//		int length;
		//
		//		while ((length = in.read(buffer)) > 0){
		//			out.write(buffer, 0, length);
		//		}
		//
		//		out.flush();
		//		in.close();


		}

	}
}
