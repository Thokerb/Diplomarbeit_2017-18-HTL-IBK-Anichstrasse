import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Datenbank3;

/**
 * Servlet implementation class DataTableServlet
 */
public class DataTableServlet extends HttpServlet {

	public static ArrayList<String[]> sortiertAutorASC = new ArrayList<String[]>();

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataTableServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	public static void setsortiertAutorASC()
	{
		sortiertAutorASC = Datenbank3.autorASC();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		//------------------------------------------
		//https://datatables.net/manual/server-side
		//zum Nachlesen welche daten empfangen werden
		//----------------------------------------------
		PrintWriter out = response.getWriter();
		String search = request.getParameter("search[value]");
		String draw = request.getParameter("draw");

		String order_art = null;

		String order =request.getParameter("order[0][column]");


		order_art = request.getParameter("order[0][dir]");


		System.out.println(order+order_art);

		//		String antwort = "{\"data\":[{\"Name\":\"Schuh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"},{\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"}, {\"Name\":\"FLasche Luggi\",\"Autor\":\"HTL\",\"UploadDatum\":\"übermorgen\",\"DokumentDatum\":\"2013\"},{\"]}";
		//		System.out.println(antwort);
		//		out.println(antwort);
		//		System.out.println("Die Transaktionsnummer ist: " +draw+". Der Suchbegriff ist: "+search+".");
		//		

		/**
		 * Hier sollte irgendwann einmal die Daten aus der DB geholt werden, welche die Daten an dem DataTable schickt.
		 * Formatierung siehe antwort2
		 */
		String antwort2 = "{\"draw\":"+draw+",\"recordsTotal\":2,\"recordsFiltered\":2,\"data\":[{\"DateiTyp\":\"PDF\",\"Name\":\"Schuh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"},{\"DateiTyp\":\"DOC\",\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"}]}";

		/*
		String json = new Gson().toJson(sortiertAutorASC);

		setsortiertAutorASC();

		System.out.println("HIIII");

		for(int i=0;i<=sortiertAutorASC.size();i++)
		{
			//System.out.println(sortiertAutorASC.get(i)[0]);
		}
		*/

		String antwort = "{\"draw\":"+draw+",\"recordsTotal\":2,\"recordsFiltered\":2,\"data\":[{\"Name\":\""+sortiertAutorASC.get(0)[0]+"\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\",\"Download\":\"Downloadlink\",\"Delete\":\"Deletelink?\"},{\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\",\"Download\":\"Downloadlink\",\"Delete\":\"Deletelink?\"}]}";
		//String antwortautorASC = "{\"draw\":"+draw+",\"recordsTotal\":4,\"recordsFiltered\":4,\"data\":[{\"Name\":\""+DatennachAutorASC[0]+""\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\",\"Download\":\"Downloadlink\",\"Delete\":\"Deletelink?\"},{\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\",\"Download\":\"Downloadlink\",\"Delete\":\"Deletelink?\"}]}";


		String antwort3 = "{\"draw\":"+draw+",\"recorawdasdasdtal\":2,\"recoawdasdastered\":2,\"data\":[{\"DateiTyp\":\"PDF\",\"awdsad\":\"Schuwadasdh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"},{\"DateiTyp\":\"DOC\",\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"}]}";
		String antwort4 = "{\"draw\":"+draw+",\"recordsTotal\":2,\"recordsFiltered\":2,\"data\":[{\"Name\":\"Schuh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\",\"Download\":\"Downloadlink\",\"Delete\":\"Deletelink?\"},{\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\",\"Download\":\"Downloadlink\",\"Delete\":\"Deletelink?\"}]}";

		System.out.println("Die Transaktionsnummer ist: " +draw+". Der Suchbegriff ist: "+search+".");
		System.out.println(antwort2);
		out.println(antwort2);

	}

	public String toJSON(ArrayList<String> sortiertAutorASC) {
		Gson gson = new Gson();
		StringBuilder sb = new StringBuilder();
		for(String d : sortiertAutorASC) {
			sb.append(gson.toJson(d));
		}
		return sb.toString();
	}
}