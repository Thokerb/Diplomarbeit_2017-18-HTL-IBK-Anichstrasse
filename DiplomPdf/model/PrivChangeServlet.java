

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
 * Servlet implementation class PrivChangeServlet
 */
@WebServlet("/PrivChangeServlet")
public class PrivChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrivChangeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String antwort = request.getParameter("tochange");
		
		Gson gsn = new Gson();
		JsonObject jobj; 

		jobj = gsn.fromJson(antwort, JsonObject.class);
		String idObj = jobj.get("ID").getAsString();
		String status = request.getParameter("howto");
		int id = Integer.parseInt(idObj);
		//String autor = jobj.get("Autor").getAsString();
		
		HttpSession ses = request.getSession(false);
		String username = (String) ses.getAttribute("user"); //Username wird schon vom vorherigen Servlet genommen

		String uploader = null;
		DBManager db;
		Connection con;
		try {
			db = new DBManager();
			con = db.getConnection();
			uploader = db.getDateiinfo(id, con,"uploaddaten","uploadid");
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		System.out.println(uploader+"|"+username);
		
		if(uploader.equals(username)){
			DBManager dbm;
			try {
				dbm = new DBManager();
				Connection conn=dbm.getConnection();
				dbm.UpdateStatus(conn, id, status);
				
				dbm.releaseConnection(conn);
			} catch (InstantiationException | IllegalAccessException | SQLException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println("ERROR fremder User ändert Privacy-Settings");
		}
		

		
		
		System.out.println("ToChange: "+antwort);
	
	}
}
