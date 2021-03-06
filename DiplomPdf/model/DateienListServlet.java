import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.DBManager;

/**
 * Servlet implementation class DateienListServlet
 */
public class DateienListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Hier werden von der Datenbank als String Array eine Liste mit den Name der bereits vorhandenen Dateien geschickt 
		 * F�r ein Beipsiel siehe String[] namen
		 */

		String[] namen = new String[0];
		HttpSession ses = request.getSession(false);
		String username = null;
		
		if(ses.getAttribute("user") == null){
			response.sendRedirect("Login.jsp");
			ses.invalidate();
		}else {
			 username = (String) ses.getAttribute("user"); //Username wird vom vorherigen Servlet genommen
		}
		
						DBManager dbm = null;
						Connection conn = null;
						int anzahl=0;
						
						try {
							dbm=new DBManager();
							conn=dbm.getConnection();
							anzahl=dbm.AnzahlEintr�ge(conn, username);
				
							if(anzahl != 0)
							namen = new String[anzahl-1];
							else
								namen = new String[anzahl];
							namen=dbm.Dateiname(conn,username);
				
						}catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch(SQLException e){
							e.printStackTrace();
						}finally{
							dbm.releaseConnection(conn);
						}
						System.out.println("Kontrolle:");
						for(int i=0;i<=anzahl-1;i++)
						{
							System.out.println(namen[i]);
						}
						
						Gson gson = new Gson();
						String answer = gson.toJson(namen);
				
						response.setContentType("application/json;charset=UTF-8");  
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(answer);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}