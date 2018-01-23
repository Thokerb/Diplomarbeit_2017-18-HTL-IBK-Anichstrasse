

import java.io.IOException;
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String Hashcode = request.getParameter("authcode");
		
		//TODO check datenbank auf Code
		boolean check =DBManager.CodeCheck();
		if(check){
			HttpSession session = request.getSession();
			session.setAttribute("authcode", Hashcode);
			
			//TODO von Datenbank Benutzernamen bekommen
			String username = DBManager.getBenutzerviaHashcode(Hashcode);
			
			session.setAttribute("username", username);
			
			response.sendRedirect("localhost:8080/DiplomPdf/NewPassword.jsp");
			
			session.setAttribute("hashcodeverified", true);

		}
		else{
			
		}
		
	}

}
