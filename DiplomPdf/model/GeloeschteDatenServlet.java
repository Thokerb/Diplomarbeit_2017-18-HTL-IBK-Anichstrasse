

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.DBManager;

/**
 * Servlet implementation class GeloeschteDatenServlet
 */
@WebServlet("/GeloeschteDatenServlet")
public class GeloeschteDatenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GeloeschteDatenServlet() {
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
		response.setContentType("application/json;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		//------------------------------------------
		//https://datatables.net/manual/server-side
		//zum Nachlesen welche daten empfangen werden
		//----------------------------------------------

		PrintWriter out = response.getWriter();

		//						Enumeration<String> en = request.getParameterNames();
		//						System.out.println("Alle ELEMENTE");
		//						while(en.hasMoreElements()){
		//							System.out.println(en.nextElement());
		//						}


		HttpSession ses = request.getSession(false);

		String username = (String) ses.getAttribute("user"); //Username wird schon vom vorherigen Servlet genommen
		String search = request.getParameter("search[value]");
		String draw = request.getParameter("draw");
		String order_art = null;
		//	String user = request.getParameter("user");
		String table = request.getParameter("table");
		System.out.println(table);
		System.out.println("user: "+username);
		String start = request.getParameter("start");
		String length = request.getParameter("length");
		System.out.println("Erstes Element:"+start+" Einträge pro Seite: "+length);


		String order =request.getParameter("order[0][column]");

		order_art = request.getParameter("order[0][dir]");

		String sortierparameter=order+order_art;
		String anzahlsuch="";

		System.out.println(order+order_art);

		//		String antwort = "{\"data\":[{\"Name\":\"Schuh des Manitu\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"},{\"Name\":\"Traumschiff Surprise\",\"Autor\":\"Internet\",\"UploadDatum\":\"morgen\",\"DokumentDatum\":\"gestern\"}, {\"Name\":\"FLasche Luggi\",\"Autor\":\"HTL\",\"UploadDatum\":\"übermorgen\",\"DokumentDatum\":\"2013\"},{\"]}";
		//		System.out.println(antwort);
		//		out.println(antwort);
		//		System.out.println("Die Transaktionsnummer ist: " +draw+". Der Suchbegriff ist: "+search+".");

		/**
		 * Hier sollte je nach dem welcher button zum sortieren der Daten die Antwort anders sein, sortierparameter einstellen
		 */

		String sortierspalte="";
		String sortierdings="";
		String methode="";
		sortierdings=username;

		ArrayList<String[]> daten = new ArrayList<String[]>();
		int anzahl = 0;
		
		DBManager db = null;
		Connection conn = null;

		try {
			db = new DBManager();
			conn=db.getConnection();

			//list = db.readDaten(conn);

			if(search!=null&&!search.isEmpty())
			{
				sortierparameter="suchwort";
			}

			switch(sortierparameter){

			case "2asc"  :{

				daten=db.geloeschteDaten(conn,sortierdings,"dateiname","ASC");

				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break;
			}

			case "2desc"  :{

				daten=db.geloeschteDaten(conn,sortierdings,"dateiname","DESC");

				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "3asc"  :{

				daten=db.geloeschteDaten(conn,sortierdings,"autor","ASC");


				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "3desc"  :{

				daten=db.geloeschteDaten(conn,sortierdings,"autor","DESC");

				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "4asc"  :{

				daten=db.geloeschteDaten(conn,sortierdings,"uploaddatum","ASC");

				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "4desc"  :{

				daten=db.geloeschteDaten(conn,sortierdings,"deletedatum","DESC");

				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}
			case "5asc"  :{

				daten=db.geloeschteDaten(conn,sortierdings,"deletedatum","ASC");

				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "5desc"  :{

				daten=db.geloeschteDaten(conn,sortierdings,"uploaddatum","DESC");

				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "6asc"  :{

				daten=db.geloeschteDaten(conn,sortierdings,"dokumentdatum","ASC");


				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "6desc"  :{

				daten=db.geloeschteDaten(conn,sortierdings,"dokumentdatum","DESC");

				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "suchwort" :{
				System.out.println("Suchwortsuche aktiv");
				db.writeStichwörter(conn, search);

				daten=db.ranking3(conn,search,username);

				for(int i=0;i<daten.size();i++)
				{
					System.out.println("Suchwortsuche aktiv");
				}
				//				anzahlsuch=daten.get(0)[0];
				//				anzahls=Integer.parseInt(anzahlsuch);

				break;
			}

			default:{

				daten=db.geloeschteDaten(conn,sortierdings,"dateiname","ASC");

				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				System.out.println("Die Daten wurden nach dem Dateiname alphabetisch geordnet");
			}
			}

			String spalte="uploader";
			String spalteninhalt=username;

			if(sortierparameter=="suchwort")
			{
				anzahl=daten.size();
				System.out.println("Anzhal an gesuchten Dokumenten: "+anzahl);
			}
			else
			{
				anzahl=db.AnzahlEinträge1(conn,spalte,spalteninhalt,"geloeschtedaten","loeschid");
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			db.releaseConnection(conn);
		}


		int wh;
		int startwert=Integer.parseInt(start);
		int laenge=Integer.parseInt(length);
		wh=anzahl-startwert;
		System.out.println("--- Ausgabe DataTableSiteServlet: ---");
		System.out.println("startwert: "+startwert);
		System.out.println("lÄnGeee: "+laenge);
		System.out.println("wert: "+wh);

		int total=wh-1;
		String antwort=" ";

		JsonObject all = new JsonObject();
		all.addProperty("draw", draw);
		all.addProperty("recordsTotal", anzahl);
		all.addProperty("recordsFiltered", anzahl);

		JsonArray data = new JsonArray();


		antwort = "{\"draw\":"+draw+",\"recordsTotal\":"+anzahl+",\"recordsFiltered\":"+anzahl+",\"data\":[";

		//antwort += "{\"ID\":\""+"1"+"\",\"DateiTyp\":\""+"PDF"+"\",\"Name\":\""+"NAME"+"\",\"Autor\":\""+"AUTOR"+"\",\"UploadDatum\":\""+"FREITAG"+"\",\"DokumentDatum\":\""+"SAMSTAG"+"\",\"ZUGANG\":\""+"public"+"\"}";

		if(startwert+laenge>anzahl)
		{
			wh=anzahl-1;
			System.out.println("wert: "+wh);
		}
		else
		{
			wh=startwert+laenge-1;
		}

		//		if(anzahls>0)
		//		{
		//			startwert++;
		//			wh+=2;
		//			System.out.println("Wiederholungen in for-Schleife: "+wh);
		//		}

		for(int i=startwert;i<=wh;i++)
		{

			antwort += "{\"ID\":\""+daten.get(i)[0]+"\",\"DateiTyp\":\""+daten.get(i)[1]+"\",\"Name\":\""+daten.get(i)[2]+"\",\"Autor\":\""+daten.get(i)[3]+"\",\"UploadDatum\":\""+daten.get(i)[4]+"\",\"DokumentDatum\":\""+daten.get(i)[5]+"\",\"ZUGANG\":\""+daten.get(i)[6]+"\"}";
			JsonObject test = new JsonObject();
			test.addProperty("ID", daten.get(i)[0]);
			test.addProperty("DateiTyp", daten.get(i)[1]);
			test.addProperty("Name", daten.get(i)[2]);
			test.addProperty("Autor", daten.get(i)[3]);
			test.addProperty("DeleteDatum", daten.get(i)[4]);
			test.addProperty("UploadDatum", daten.get(i)[5]);
			test.addProperty("DokumentDatum", daten.get(i)[6]);
			test.addProperty("ZUGANG", daten.get(i)[7]);

			data.add(test);

			if(i!=wh)
			{

				antwort+=",";
			}
		}

		antwort+="]}";
		all.add("data", data);
		//		if(anzahl==0)
		//		{
		//			antwort = "{\"draw\":"+draw+",\"recordsTotal\":0,\"recordsFiltered\":0,\"data\":[]}";
		//		}

		System.out.println("all: "+all);
		System.out.println("Die Transaktionsnummer ist: " +draw+". Der Suchbegriff ist: "+search+".");
		System.out.println(antwort);
		out.println(all);
	}

}
