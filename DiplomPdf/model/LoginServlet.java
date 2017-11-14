

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {       
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname = request.getParameter("username");
		String pwd = request.getParameter("password");
		
		if(uname.equals("sara") && pwd.equals("1234")){
			
			HttpSession session = request.getSession();
			session.setAttribute("username", uname);
			response.sendRedirect("Startseite.html");
		}
		else{
			response.sendRedirect("Login.html");
		}
	}

}
