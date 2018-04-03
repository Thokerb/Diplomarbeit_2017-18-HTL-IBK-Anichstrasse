import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.DBManager;

/**
 * Servlet implementation class DownloadServlet
 * 
 * Servlet erzeugt aus BlobDatei von DB einen Stream welcher durch setzten eines Headers Datei ausgibt
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String antwort = request.getParameter("download");
		System.out.println(antwort);

		System.out.println("DownloadRequest: "+antwort);
		Gson gsn = new Gson();
		JsonObject jobj; 

		jobj = gsn.fromJson(antwort, JsonObject.class);
		int idObj = jobj.get("ID").getAsInt();
		String fileName = jobj.get("Name").getAsString();
		System.out.println("ID: "+ idObj);
		byte [] byteData = null;
		
		DBManager dbm = null;
		Connection conn = null;
		
		try {
			dbm = new DBManager();
			conn=dbm.getConnection();
			byteData = dbm.BLOBauslesen(conn,idObj);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbm.releaseConnection(conn);
		}
		
	    response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition","attachment;filename="+fileName);
	    
	    ServletOutputStream out = response.getOutputStream();
	    out.write(byteData);
	    out.flush();
	    out.close();

		}
	}