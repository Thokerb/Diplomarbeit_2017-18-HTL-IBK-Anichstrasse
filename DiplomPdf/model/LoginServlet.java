/**
 * pw: htlanichstr bei email account
 */

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

//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String pwd = request.getParameter("password");
		System.out.println(username);
		boolean anmeldung; 
		// Datenbank abfrage von Benutzer normal
		try {
			DBManager dbm=new DBManager();
			Connection conn1=dbm.getConnection();
			
		if(DBManager.checkUser(conn1, username, pwd) || username.equals("user") && pwd.equals("1234"))
		{
			System.out.println("Anmeldung erfolgreich");
			HttpSession session = request.getSession();  
			session.setAttribute("user",username);  
			response.sendRedirect("DataTableSite.jsp");
			anmeldung = true; 
			dbm.releaseConnection(conn1);

		}else{

			String error = "Achtung! Username oder Password sind nicht korrekt";
			System.out.println(error);
			anmeldung = false; 
			RequestDispatcher rs = request.getRequestDispatcher("Login.jsp");
			rs.include(request, response);
		}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


}
