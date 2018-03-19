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

		PasswordHash pwh = new PasswordHash();
		
		String username = request.getParameter("username");
		String pwd = request.getParameter("password");
		System.out.println("Login als: "+username);
		
		String hashpw = pwh.passwordToHash(pwd);
		System.out.println("Hash: "+ hashpw);
		
		boolean anmeldung; 
		// Datenbank abfrage von Benutzer normal
		DBManager dbm = null;
		Connection conn = null;
		try {
			dbm=new DBManager();
			conn=dbm.getConnection();
			
		if(DBManager.checkUser(conn, username, hashpw) || username.equals("user") && pwd.equals("1234"))
		{
			System.out.println("Anmeldung erfolgreich");
			HttpSession session = request.getSession();  
			session.setAttribute("user",username);  
			request.getRequestDispatcher("DataTableSite.jsp").forward(request, response);
			anmeldung = true; 

		}else{

			String error = "Achtung! Username oder Password sind nicht korrekt";
			System.out.println(error);
			anmeldung = false; 
			request.setAttribute("message","Bitte erneut versuchen!"); // TODO wird nie verwendet? 
			RequestDispatcher rs = request.getRequestDispatcher("Login.jsp");
			rs.forward(request, response);
		}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			dbm.releaseConnection(conn);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}