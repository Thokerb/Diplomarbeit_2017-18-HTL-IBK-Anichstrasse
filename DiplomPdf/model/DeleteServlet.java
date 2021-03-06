

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.DBManager;
import model.Daten;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	response.getWriter().append("Served at: ").append(request.getContextPath());
		/**
		 * Hier werden die Daten der Datei geschickt, welche gel�scht werden sollen
		 * F�r ein Beispiel testjquery.html �ffnen und auf den delete button klicken4
		 * TODO: aus DB l�schen aber darauf achten, dass nicht zu schnell gel�scht wird.
		 * evtl reihenfolge vorgeben zuerst db dann aus seite l�schen? 
		 */
		String todelete = request.getParameter("todelete");
		System.out.println("todelete: "+ todelete);

		Gson gsn = new Gson();
		JsonObject jobj; 

		jobj = gsn.fromJson(todelete, JsonObject.class);
		String idObj = jobj.get("ID").getAsString();
		int id = Integer.parseInt(idObj);
		System.out.println("todelete:" +id);

		//	String autor = jobj.get("Autor").getAsString();
		HttpSession ses = request.getSession(false);
		String username = (String) ses.getAttribute("user"); //Username wird vom vorherigen Servlet genommen

		Daten uploaddaten = new Daten();

		DBManager db = null;
		Connection conn = null;
		String uploader = null;
		try {
			db = new DBManager();
			conn = db.getConnection();
			uploader = db.getDateiinfo(id, conn);
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			db.releaseConnection(conn);
		}

		System.out.println(username +"|"+ uploader);

		if(username.equals(uploader)){
			try {
				db = new DBManager();
				conn=db.getConnection();
				db.updateZustandloeschen(conn, id);

			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				db.releaseConnection(conn);
			}
		}
		else{
			System.out.println("Verschieben nicht erlaubt");
			//TODO:Antwort an Client senden? 
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
