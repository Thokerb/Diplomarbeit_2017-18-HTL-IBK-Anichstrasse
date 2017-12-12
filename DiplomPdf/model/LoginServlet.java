

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String pwd = request.getParameter("password");


		// Datenbank abfrage von Benutzer normal

		//if(AnmeldungValidate.checkUser(username, pwd) || username.equals("user") && pwd.equals("1234"))
		if(username.equals("user") && pwd.equals("1234"))
		{
			System.out.println("Anmeldung OK");
			RequestDispatcher rs = request.getRequestDispatcher("DataTableSite.html");
			rs.forward(request,response);
			
		}else{
			
			System.out.println("Achtung! Username oder Password stimmen nicht überein");

			PrintWriter out = response.getWriter();  
			response.setContentType("text/html");  
			out.println("<script type=\"text/javascript\">");  
			out.println("alert('Achtung! Username oder Password stimmen nicht überein');");  
			out.println("</script>");

			RequestDispatcher rs = request.getRequestDispatcher("Login.jsp");
			rs.include(request, response);
		}


	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


}
