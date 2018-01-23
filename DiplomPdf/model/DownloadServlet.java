

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
		byte[] buffer;

		Connection conn=null;

		String antwort = request.getParameter("download");
		System.out.println("DownloadRequest: "+antwort);

		Gson gsn = new Gson();
		JsonObject jobj; 

		jobj = gsn.fromJson(antwort, JsonObject.class);
		String idObj = jobj.get("ID").getAsString();
		fileName = jobj.get("Name").getAsString();
		System.out.println("ID: "+ idObj);
		
		try {
			DBManager dbm = new DBManager();
			Connection con = dbm.getConnection();
			fileType = dbm.getDateiTyp(idObj,con);
			buffer = dbm.getBlob(idObj,con);
			
			
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
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


		File my_file = new File(fileName); //dateinamen?  https://stackoverflow.com/questions/1442893/implementing-a-simple-file-download-servlet

		OutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(my_file);

				int length;
		
				while ((length = in.read(buffer)) > 0){
					out.write(buffer, 0, length);
				}
		
				out.flush();
				in.close();
//out close ?

		}

	}
}
