

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DBManager;

/**
 * Servlet implementation class ResetPasswort
 */
@WebServlet("/ResetPasswort")
public class ResetPasswort extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession ses = request.getSession(false);
		String username = (String) ses.getAttribute("username"); //Username wird schon vom vorherigen Servlet genommen
		String pw = request.getParameter("password");
		String pw2 = request.getParameter("password2");
		
		String auth = (String) ses.getAttribute("hashcodeverified");
		PasswordHash pwh = new PasswordHash();
		
	
		System.out.println("User: "+ username +" PW2: "+pw+" PW2: "+pw2);
		
		DBManager db = null;
		Connection conn = null;
		
		if(auth.equalsIgnoreCase("yes")) {
			if(pw.equals(pw2)) {
				if(RegisterServlet.pwdIsValid(pw)) {

					try {
						
						String hashpw = pwh.passwordToHash(pw);
						System.out.println("Hash: "+ hashpw);
						
						db = new DBManager();
						conn=db.getConnection();

						db.PasswortNeuSetzen(conn, username,hashpw);

						response.setContentType("text/plain");
						PrintWriter out = response.getWriter();
						out.print("pwok");

						//TODO message muss no in seite zugordnet werdn

						request.setAttribute("message", "Passwort konnte erfolgreich geändert werden ");
						RequestDispatcher rd = request.getRequestDispatcher("NewPassword.jsp");
						rd.forward(request, response);
						//TODO löschen des hash
//						DBManager m = new DBManager();
//						Connection conn2 = m.getConnection();
						
						db.deletehash(conn,username);

					}catch(SQLException e){
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} finally {
						db.releaseConnection(conn);
					}
//					response.setContentType("text/plain");
//					PrintWriter out = response.getWriter();
//					out.print("pwok");
				}
				else {
//					response.setContentType("text/plain");
//					PrintWriter out = response.getWriter();
//					out.print("notsamesame");
					request.setAttribute("message", "Passwörter stimmen nicht überein");
					RequestDispatcher rd = request.getRequestDispatcher("NewPassword.jsp");
					rd.forward(request, response);
				}
			}
			else {

				request.setAttribute("message", "Passwort konnte nicht geändert werden ");
				System.out.println("Passwort konnte nicht geändert werden ");
				RequestDispatcher rd = request.getRequestDispatcher("ErrorPage.jsp");
				rd.forward(request, response);

			}
		}
	}
}