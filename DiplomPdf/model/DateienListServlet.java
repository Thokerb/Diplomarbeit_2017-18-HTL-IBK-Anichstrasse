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
		 * Hier sollten von der Datenbank als String Array eine Liste mit den Name der bereits vorhandenen Dateien geschickt werden.
		 * Für ein Beipsiel siehe String[] namen
		 */

		String[] namen = new String[0];
		HttpSession ses = request.getSession(false);
		
		String username = (String) ses.getAttribute("user"); //Username wird vom vorherigen Servlet genommen
				
						int anzahl=0;
						try {
							DBManager dbm=new DBManager();
							Connection conn=dbm.getConnection();
							anzahl=dbm.AnzahlEinträge(conn, username,"uploaddaten");
				
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
						}
						System.out.println("Kontrolle:");
						for(int i=0;i<=anzahl-1;i++)
						{
							System.out.println(namen[i]);
						}
						
						Gson gson = new Gson();
						String answer = gson.toJson(namen);
				
						//		response.getWriter().append("Served at: ").append(request.getContextPath());
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
