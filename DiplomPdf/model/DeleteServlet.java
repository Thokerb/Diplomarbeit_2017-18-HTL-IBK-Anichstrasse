

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
		// TODO Auto-generated method stub
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
		/**
		 * Hier werden die Daten der Datei geschickt, welche gelöscht werden sollen
		 * Für ein Beispiel testjquery.html öffnen und auf den delete button klicken4
		 * TODO: aus DB löschen aber darauf achten, dass nicht zu schnell gelöscht wird.
		 * evtl reihenfolge vorgeben zuerst db dann aus seite löschen? 
		 */
		String toshift = request.getParameter("todelete");
		System.out.println("toshift: "+toshift);
		
		Gson gsn = new Gson();
		JsonObject jobj; 

		jobj = gsn.fromJson(toshift, JsonObject.class);
		String idObj = jobj.get("ID").getAsString();
		int id = Integer.parseInt(idObj);
		System.out.println("toshift:"+id);
		String autor = jobj.get("Autor").getAsString();

	//	String autor = jobj.get("Autor").getAsString();
		HttpSession ses = request.getSession(false);
		String username = (String) ses.getAttribute("user"); //Username wird vom vorherigen Servlet genommen
		
		Daten uploaddaten = new Daten();

		DBManager db;
		String uploader = null;

		System.out.println(username+"|"+uploader);
		
		if(username.equals(uploader)){
			try {
				System.out.println("PENIS PENIS PENis");
				
				DBManager db1 = new DBManager();
				Connection conn=db1.getConnection();
				uploaddaten=db1.readzuloeschendeDatei(conn,id);
				db1.Datenlöschen(conn,id,"uploaddaten");
				db1.writegeloeschteDaten(conn, uploaddaten);
				System.out.println("PENIS PENIS PENSI");
				
				db1.Datenlöschen(conn,id,"uploaddaten");

			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
