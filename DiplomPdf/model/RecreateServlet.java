

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
 * Servlet implementation class RecreateServlet
 */
@WebServlet("/RecreateServlet")
public class RecreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		String username = (String) ses.getAttribute("user"); //Username wird vom vorherigen Servlet genommen
		
		Daten uploaddaten = new Daten();

		if(username.equals(autor)){
			try {				
				DBManager db = new DBManager();
				Connection conn=db.getConnection();
				uploaddaten=db.readgeloeschteDatei(conn,id);
				db.Datenlöschen(conn,id,"geloeschtedaten");
				db.writeDaten(conn, uploaddaten);
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
