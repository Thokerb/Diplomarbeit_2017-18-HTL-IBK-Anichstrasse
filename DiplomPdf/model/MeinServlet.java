import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class MeinServlet
 */
//@WebServlet("/MeinServlet")
public class MeinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public MeinServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out  = response.getWriter();
		String antwort = request.getParameter("answer");
		System.out.println("Die Antwort: "+antwort);
		Gson gson = new Gson();
		String[] begriffe;
		begriffe = gson.fromJson(antwort, String[].class);
		System.out.println("Hier sollte nicht null stehen: "+begriffe);
		for(int i = 0;i<begriffe.length;i++){
			System.out.println(begriffe[i]);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}