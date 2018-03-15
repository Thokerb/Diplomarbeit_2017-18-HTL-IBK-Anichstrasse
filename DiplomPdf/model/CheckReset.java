

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DBManager;

/**
 * Servlet implementation class CheckReset
 */
@WebServlet("/CheckReset")
public class CheckReset extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckReset() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("CheckReset called");
		String Hashcode = request.getParameter("authcode");
		System.out.println("empf code:"+Hashcode);
		
		Boolean check = false;
		try {
			DBManager dbm = new DBManager();
			Connection conn=dbm.getConnection();
			 check = dbm.CodeCheck(conn,Hashcode);

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(check);

		if(check){
			System.out.println("Hash stimmt überein");
			HttpSession session = request.getSession(true);
			session.setAttribute("authcode", Hashcode);
			
			String user = "";
			try{
				DBManager dbm=new DBManager();
				Connection conn=dbm.getConnection();
				user = dbm.getUserbyHash(conn,Hashcode);
				System.out.println("Benutzer by Hash:" + user);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			
			session.setAttribute("username", user);
			session.setAttribute("hashcodeverified", "yes");
			response.sendRedirect("NewPassword.jsp");
		}
		else{
			response.sendRedirect("ErrorPage.html");
		}
		
	}

}
