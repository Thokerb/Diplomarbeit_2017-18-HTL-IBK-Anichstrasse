

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
 * 
 * Reset Passwort Servlet dient dazu, wenn der Nutzer die Seite zum Zurücksetzten aufruft und seine neuen Daten eingibt, 
 * den Nutzer der dies anfordert ausfindig zu machen, die neuen PW zu checken und dann zu hashen um es anschließend in die DB einzutragen 
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
		
		System.out.println("User: "+ username +" PW1: "+pw+" PW2: "+pw2);
		
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

						request.setAttribute("message", "Passwort konnte erfolgreich geändert werden ");
						RequestDispatcher rd = request.getRequestDispatcher("NewPassword.jsp");
						rd.forward(request, response);
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

				}
				else {
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