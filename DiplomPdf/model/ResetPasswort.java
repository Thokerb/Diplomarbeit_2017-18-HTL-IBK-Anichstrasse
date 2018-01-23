

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
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
		System.out.println("reseter called");
		HttpSession ses = request.getSession(false);
		String username = (String) ses.getAttribute("username"); //Username wird schon vom vorherigen Servlet genommen
		String pw = request.getParameter("password");
		String pw2 = request.getParameter("password2");
		String auth = (String) ses.getAttribute("hashcodeverified");
		
		if(auth.equalsIgnoreCase("yes")) {

			if(pw.equals(pw2)) {
				DBManager.PasswortNeuSetzen(username, pw);
				
				response.setContentType("text/plain");
			    PrintWriter out = response.getWriter();
			    out.print("pwok");
			    
			}
			else {
				response.setContentType("text/plain");
			    PrintWriter out = response.getWriter();
			    out.print("notsamesame");
			}

		}
		else {
			response.sendRedirect("ErrorPage.jsp");
		}




	}

}
