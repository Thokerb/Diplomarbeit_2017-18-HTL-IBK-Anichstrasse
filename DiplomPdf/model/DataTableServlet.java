
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
import model.HineinschreibenDB;
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

		String sortierparameter=order+order_art;

		System.out.println(order+order_art);

		//		String antwort = "{\"data\":[{\"Name\":\"Schuh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"},{\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"}, {\"Name\":\"FLasche Luggi\",\"Autor\":\"HTL\",\"UploadDatum\":\"übermorgen\",\"DokumentDatum\":\"2013\"},{\"]}";
		//		System.out.println(antwort);
		//		out.println(antwort);
		//		System.out.println("Die Transaktionsnummer ist: " +draw+". Der Suchbegriff ist: "+search+".");
		//		

		/**
		 * TODO Hier sollte je nach dem welcher button zum sortieren der Daten die Antowrt anders sein, sortierparameter einstellen
		 */

		//sortierparameter muss Spalte+ASC oder DESC sein
		



		Connection conn=null;
		List<Uploaddaten> list = null;

		ArrayList<String[]> daten = new ArrayList<String[]>();
		int anzahl = 0;

		try {
			DBManager db = new DBManager();
			conn=db.getConnection();

			//list = db.readDaten(conn);
			
			switch(sortierparameter){

			case "1asc"  :{
				daten=db.dateinameASC(conn);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break;
			}

			case "1desc"  :{
				daten=db.dateinameDESC(conn);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "2asc"  :{
				daten=db.autorASC(conn);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "2desc"  :{
				daten=db.autorDESC(conn);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "3asc"  :{
				daten=db.uploaddatumASC(conn);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "3desc"  :{
				daten=db.uploaddatumDESC(conn);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "4asc"  :{
				daten=db.uploaddatumASC(conn);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "4desc"  :{
				daten=db.uploaddatumDESC(conn);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}
/*
			case "suchwort" :{
				daten=db.ranking2(search);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break;
			}*/
			
			default:{

				daten=db.dateinameASC(conn);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				System.out.println("Die Daten wurden nach Autor alphabetisch geordnet");
			}
			}
			anzahl=db.AnzahlEinträge(conn);


			db.releaseConnection(conn);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		}

		String antwort = "{\"draw\":"+draw+",\"recordsTotal\":"+anzahl +",\"recordsFiltered\":"+anzahl +",\"data\":[";
		for(int i=0;i<daten.size();i++)
		{
			antwort += "{\"DateiTyp\":\""+daten.get(i)[0]+"\",\"Name\":\""+daten.get(i)[1]+"\",\"Autor\":\""+daten.get(i)[2]+"\",\"UploadDatum\":\""+daten.get(i)[3]+"\",\"DokumentDatum\":\""+daten.get(i)[4]+"\"}";
			if(i!=daten.size()-1)
			{
				antwort+=",";
			}
		}
		antwort += "]}";

		System.out.println("Die Transaktionsnummer ist: " +draw+". Der Suchbegriff ist: "+search+".");
		System.out.println(antwort);
		out.println(antwort);
	}

}
