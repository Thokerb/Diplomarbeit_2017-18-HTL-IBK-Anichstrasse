
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.DBManager;
import model.Datenbank3;
import model.FunktionenDB;
import model.Uploaddaten;
import model.geordneteAusgabe;

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
		doPost(request, response);
	}


//	public static void setsortiertAutorASC()
//	{
//		sortiertAutorASC = geordneteAusgabe.autorASC();
//
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//------------------------------------------
		//https://datatables.net/manual/server-side
		//zum Nachlesen welche daten empfangen werden
		//----------------------------------------------

		PrintWriter out = response.getWriter();
		
//		Enumeration<String> en = request.getParameterNames();
//		System.out.println("Alle ELEMENTE");
//		while(en.hasMoreElements()){
//			System.out.println(en.nextElement());
//		}
		
		
		
		String search = request.getParameter("search[value]");
		String draw = request.getParameter("draw");

		String order_art = null;

		String start = request.getParameter("start");
		String length = request.getParameter("length");
System.out.println("Erstes Element:"+start+" Einträge pro Seite: "+length);

		
		String order =request.getParameter("order[0][column]");

		order_art = request.getParameter("order[0][dir]");


		System.out.println(order+order_art);

		//		String antwort = "{\"data\":[{\"Name\":\"Schuh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"},{\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"}, {\"Name\":\"FLasche Luggi\",\"Autor\":\"HTL\",\"UploadDatum\":\"übermorgen\",\"DokumentDatum\":\"2013\"},{\"]}";
		//		System.out.println(antwort);
		//		out.println(antwort);
		//		System.out.println("Die Transaktionsnummer ist: " +draw+". Der Suchbegriff ist: "+search+".");
		//		

		/**
		 * TODO Hier sollte irgendwann einmal die Daten aus der DB geholt werden, welche die Daten an dem DataTable schickt.
		 * Formatierung siehe antwort2
		 */
		String antwort2 = "{\"draw\":"+draw+",\"recordsTotal\":2,\"recordsFiltered\":2,\"data\":[{\"DateiTyp\":\"PDF\",\"Name\":\"Schuh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"},{\"DateiTyp\":\"DOC\",\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"}]}";
		String antwort2_0 = "{\"draw\":"+draw+",\"recordsTotal\":1,\"recordsFiltered\":1,\"data\":[{\"DateiTyp\":\"PDF\",\"Name\":\"Schuh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"}]}";


		Connection conn=null;
		List<Uploaddaten> list = null;

		ArrayList<String[]> daten = new ArrayList<String[]>();
		int anzahl = 0;
		
		try {
			DBManager db = new DBManager();
			conn=db.getConnection();

			//list = db.readDaten(conn);

			daten=db.autorASC(conn);
			for(int i=0;i<daten.size();i++)
			{
				System.out.println(daten.get(i)[1]);
			}
			anzahl=db.AnzahlEinträge(conn);


			db.releaseConnection(conn);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		String antwortautorASC = "{\"draw\":"+draw+",\"recordsTotal\":"+anzahl +",\"recordsFiltered\":"+anzahl +",\"data\":[";
		for(int i=0;i<daten.size();i++)
		{
			antwortautorASC += "{\"DateiTyp\":\""+daten.get(i)[0]+"\",\"Name\":\""+daten.get(i)[1]+"\",\"Autor\":\""+daten.get(i)[2]+"\",\"UploadDatum\":\""+daten.get(i)[3]+"\",\"DokumentDatum\":\""+daten.get(i)[4]+"\"}";
			if(i!=daten.size()-1)
			{
				antwortautorASC+=",";
			}
		}
		antwortautorASC += "]}";
		//String antwortautorASC = "{\"draw\":"+draw+",\"recordsTotal\":1,\"recordsFiltered\":1,\"data\":[{\"DateiTyp\":\""+daten.get(0)[0]+"\",\"Name\":\""+daten.get(0)[1]+"\",\"Autor\":\""+daten.get(0)[2]+"\",\"UploadDatum\":\""+daten.get(0)[3]+"\",\"DokumentDatum\":\""+daten.get(0)[4]+"\"}]}";
		//String antwortautorASC = "{\"draw\":"+draw+",\"recordsTotal\":"+anzahl +",\"recordsFiltered\":"+anzahl +",\"data\":[{\"DateiTyp\":\""+sortiertAutorASC.get(0)[0]+"\",\"Name\":\""+sortiertAutorASC.get(0)[1]+"\",\"Autor\":\""+sortiertAutorASC.get(0)[2]+"\",\"UploadDatum\":\""+sortiertAutorASC.get(0)[3]+"\",\"DokumentDatum\":\""+sortiertAutorASC.get(0)[4]+"\"}";


		String antwort3 = "{\"draw\":"+draw+",\"recorawdasdasdtal\":2,\"recoawdasdastered\":2,\"data\":[{\"DateiTyp\":\"PDF\",\"awdsad\":\"Schuwadasdh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"},{\"DateiTyp\":\"DOC\",\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"}]}";
		String antwort4 = "{\"draw\":"+draw+",\"recordsTotal\":2,\"recordsFiltered\":2,\"data\":[{\"Name\":\"Schuh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\",\"Download\":\"Downloadlink\",\"Delete\":\"Deletelink?\"},{\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\",\"Download\":\"Downloadlink\",\"Delete\":\"Deletelink?\"}]}";

		System.out.println("Die Transaktionsnummer ist: " +draw+". Der Suchbegriff ist: "+search+".");
		System.out.println(antwortautorASC);
		out.println(antwortautorASC);

	}

}
