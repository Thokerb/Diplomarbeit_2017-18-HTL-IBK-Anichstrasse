

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
		String todelete = request.getParameter("todelete");
		System.out.println("todelete: "+todelete);
		
		Gson gsn = new Gson();
		JsonObject jobj; 

		jobj = gsn.fromJson(todelete, JsonObject.class);
		String idObj = jobj.get("ID").getAsString();
		int id = Integer.parseInt(idObj);
		System.out.println("todeleted:"+id);
		String autor = jobj.get("Autor").getAsString();
		HttpSession ses = request.getSession(false);
		String username = (String) ses.getAttribute("user"); //Username wird schon vom vorherigen Servlet genommen

		if(username.equals(autor)){
			try {
				DBManager db = new DBManager();
				Connection conn=db.getConnection();
				db.Datenlöschen(conn,id);
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
			System.out.println("Löschen nicht erlaubt");
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
