

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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String toshift = request.getParameter("toshift");
		System.out.println("toshift: "+toshift);
		
		Gson gsn = new Gson();
		JsonObject jobj; 

		jobj = gsn.fromJson(toshift, JsonObject.class);
		String idObj = jobj.get("ID").getAsString();
		int id = Integer.parseInt(idObj);
		System.out.println("toshift:"+id);
		HttpSession ses = request.getSession(false);
		String username = (String) ses.getAttribute("user"); //Username wird vom vorherigen Servlet genommen
		
		DBManager db;
		String uploader = null;
		try {
			db = new DBManager();
			Connection con = db.getConnection();
			uploader = db.getDateiinfo(id, con,"geloeschteDaten","loeschid");
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Daten uploaddaten = new Daten();

		System.out.println("Uploader: "+uploader+ " Username: "+username);
		if(username.equals(uploader)){
			try {				
				db = new DBManager();
				Connection conn=db.getConnection();
				uploaddaten=db.readgeloeschteDatei(conn,id);
				db.writeDaten(conn, uploaddaten);
				db.Datenlöschen(conn,id,"geloeschtedaten","loeschid");
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
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
