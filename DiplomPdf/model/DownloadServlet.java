

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

	// size of byte buffer to send file
	private static final int BUFFER_SIZE = 4096;   

	// database connection settings
	static final String JDBC_DRIVER = "org.postgresql.Driver"; 
	private String dbURL =  "jdbc:postgresql://localhost/diplomarbeit";
	private String dbUser = "postgres";
	private String dbPass = "password";


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/**
		 * Hier werden die Daten der Datei geschickt, welche gedownloaded werden sollen
		 * Für ein Beispiel testjquery.html öffnen und auf den download button klicken
		 */
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn=null;

		String antwort = request.getParameter("download");
		System.out.println("DOWNLOADREQUEST: "+antwort);

		// int uploadId = Integer.parseInt(request.getParameter("id"));
		//         System.out.println("id: "+uploadId);
		//	        Connection conn = null; // connection to the database
		//	         
		//	        try {
		//	        	
		//	        	//Verena:
		//	        	DBManager db=new DBManager();
		//	        	conn=db.getConnection();
		//	        	
		//	        	//TODO den richtigen dateinamen angeben, Achtung in da Methode no speicherort ändern bzw vielleicht als Methodenparameter übergeben
		//				db.BLOBauslesen("dateinamen");
		//	        	
		//	        	
		//	        	
		//	        	//Saras Werk
		//	            // connects to the database
		//	            DriverManager.registerDriver(new org.postgresql.Driver());
		//	            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
		//	 
		//	            // queries the database TODO wie schaut tabelle aus
		//	            String sql = "SELECT * FROM files_upload WHERE upload_id = ?";
		//	            PreparedStatement statement = conn.prepareStatement(sql);
		//	            statement.setInt(1, uploadId);
		//	 
		//	            ResultSet result = statement.executeQuery();
		//	            if (result.next()) {
		//	                // gets file name and file blob data
		//	                String fileName = result.getString("file_name");
		//	                Blob blob = result.getBlob("file_data");
		//	                InputStream inputStream = blob.getBinaryStream();
		//	                int fileLength = inputStream.available();
		//	                 
		//	                System.out.println("fileLength = " + fileLength);
		//	 
		//	                ServletContext context = getServletContext();
		//	 
		//	                // sets MIME type for the file download
		//	                String mimeType = context.getMimeType(fileName);
		//	                
		//	                if (mimeType == null) {  
		//	                	//TODO: wurde vonskypekopiert
		//	                    mimeType = "\"application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,text/plain";
		//	                }              
		//	                 
		//	                // set content properties and header attributes for the response
		//	                response.setContentType(mimeType);
		//	                response.setContentLength(fileLength);
		//	                String headerKey = "Content-Disposition";
		//	                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		//	                response.setHeader(headerKey, headerValue);
		//	 
		//	                // writes the file to the client
		//	                OutputStream outStream = response.getOutputStream();
		//	                 
		//	                byte[] buffer = new byte[BUFFER_SIZE];
		//	                int bytesRead = -1;
		//	                 
		//	                while ((bytesRead = inputStream.read(buffer)) != -1) {
		//	                    outStream.write(buffer, 0, bytesRead);
		//	                }
		//	                 
		//	                inputStream.close();
		//	                outStream.close();             
		//	            } else {
		//	                // no file found
		//	                response.getWriter().print("Datei wurde nicht gefunden: " + uploadId);  
		//	            }
		//	            
		//	            db.releaseConnection(conn);
		//	        } catch (SQLException ex) {
		//	            ex.printStackTrace();
		//	            response.getWriter().print("SQL Error: " + ex.getMessage());
		//	        } catch (IOException ex) {
		//	            ex.printStackTrace();
		//	            response.getWriter().print("IO Error: " + ex.getMessage());
		//	        } catch (InstantiationException e) {
		//				e.printStackTrace();
		//			} catch (IllegalAccessException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			} finally {
		//	            if (conn != null) {
		//	                // closes the database connection
		//	                try {
		//	                    conn.close();
		//	                } catch (SQLException ex) {
		//	                    ex.printStackTrace();
		//	                }
		//	            }          
		//	        }
		//         System.out.println("id: "+uploadId);

		try {

			//Verena:
			DBManager db=new DBManager();
			conn=db.getConnection();

			//TODO den richtigen dateinamen angeben und inhalttext
			db.BLOBauslesen("dateinamen","inhalttext");



			//	        	//Saras Werk
			//	            // connects to the database
			//	            DriverManager.registerDriver(new org.postgresql.Driver());
			//	            conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			//	 
			//	            // queries the database TODO wie schaut tabelle aus
			//	            String sql = "SELECT * FROM files_upload WHERE upload_id = ?";
			//	            PreparedStatement statement = conn.prepareStatement(sql);
			//	            statement.setInt(1, uploadId);
			//	 
			//	            ResultSet result = statement.executeQuery();
			//	            if (result.next()) {
			//	                // gets file name and file blob data
			//	                String fileName = result.getString("file_name");
			//	                Blob blob = result.getBlob("file_data");
			//	                InputStream inputStream = blob.getBinaryStream();
			//	                int fileLength = inputStream.available();
			//	                 
			//	                System.out.println("fileLength = " + fileLength);
			//	 
			//	                ServletContext context = getServletContext();
			//	 
			//	                // sets MIME type for the file download
			//	                String mimeType = context.getMimeType(fileName);
			//	                
			//	                if (mimeType == null) {  
			//	                	//TODO: wurde vonskypekopiert
			//	                    mimeType = "\"application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,text/plain";
			//	                }              
			//	                 
			//	                // set content properties and header attributes for the response
			//	                response.setContentType(mimeType);
			//	                response.setContentLength(fileLength);
			//	                String headerKey = "Content-Disposition";
			//	                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
			//	                response.setHeader(headerKey, headerValue);
			//	 
			//	                // writes the file to the client
			//	                OutputStream outStream = response.getOutputStream();
			//	                 
			//	                byte[] buffer = new byte[BUFFER_SIZE];
			//	                int bytesRead = -1;
			//	                 
			//	                while ((bytesRead = inputStream.read(buffer)) != -1) {
			//	                    outStream.write(buffer, 0, bytesRead);
			//	                }
			//	                 
			//	                inputStream.close();
			//	                outStream.close();             
			//	            } else {
			//	                // no file found
			//	                response.getWriter().print("Datei wurde nicht gefunden: " + uploadId);  
			//	            }
			//	            
			db.releaseConnection(conn);
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
	}

}
