

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class DateienListServlet
 */
public class DateienListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DateienListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Hier sollten von der Datenbank als String Array eine Liste mit den Name der bereits vorhandenen Dateien geschickt werden.
		 * Für ein Beipsiel siehe String[] namen
		 */

		// TODO Auto-generated method stub
		String[] namen = new String[2];
		namen[0] = "sinnlosePDF.pdf";
		namen[1] = "sinnlosesDOC.docx";
		Gson gson = new Gson();
		String answer = gson.toJson(namen);

		//		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("application/json");  
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(answer);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
