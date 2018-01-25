import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TestServlet")

public class TestServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//performTask(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		performTask(request, response);
//		System.out.println("called");
//		 String message = "Hello World";
//	        request.setAttribute("message", message); // This will be available as ${message}
//	        request.getRequestDispatcher("LoginRD.jsp").forward(request, response);
	
	}

	private void performTask(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("TestServlet says hi <br/>");

		String action = request.getParameter("username");
		
		String anmeldungok = "hi";
		String anmeldungnitok = "bye";
		
		if (action != null) {
			
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			
			if ("include".equalsIgnoreCase(action)) {
				
				request.setAttribute("action", anmeldungok);
				rd.include(request, response);
				
			} else if ("forward".equalsIgnoreCase(action)) {
				
				request.setAttribute("action", anmeldungnitok);
				rd.forward(request, response);
			}
		}

	}

}