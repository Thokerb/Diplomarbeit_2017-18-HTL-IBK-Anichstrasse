
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import model.DBManager;
import model.Daten;


/**
 * Servlet implementation class DataTableServlet
 */
public class DataTableServlet extends HttpServlet {

	public static ArrayList<String[]> sortiertAutorASC = new ArrayList<String[]>();

	private static final long serialVersionUID = 1L;

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
		response.setContentType("application/json;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		
		//------------------------------------------
		//https://datatables.net/manual/server-side
		//zum Nachlesen welche daten empfangen werden
		//----------------------------------------------

		PrintWriter out = response.getWriter();

		HttpSession ses = request.getSession(false);

		String username = (String) ses.getAttribute("user");
		String search = request.getParameter("search[value]");
		String draw = request.getParameter("draw");
		String order_art = null;
		String table = request.getParameter("table");
		System.out.println(table);
		System.out.println("user: "+username);
		String start = request.getParameter("start");
		String length = request.getParameter("length");
		
		System.out.println("Erstes Element: "+start+" Einträge pro Seite: "+length);

		String order =request.getParameter("order[0][column]");

		order_art = request.getParameter("order[0][dir]");

		String sortierparameter=order+order_art;

		System.out.println(order+order_art);
		
		/**
		 * Hier sollte je nach dem welcher button zum sortieren der Daten die Antwort anders sein, sortierparameter einstellen
		 */

		String sortierdings="";
		if(table.equals("table1"))
		{
			sortierdings=username;
		}

		//sortierparameter muss Spalte+ASC oder DESC sein
		Connection conn=null;
		DBManager db = null;

		ArrayList<Daten> daten = new ArrayList<Daten>();
		int anzahl = 0;

		try {
			db = new DBManager();
			conn=db.getConnection();

			if(search!=null&&!search.isEmpty())
			{
				sortierparameter="suchwort";
			}

			switch(sortierparameter){

			case "2asc"  :{
				System.out.println("Sortieren nach Dateiname aufsteigend");
				if(table.equals("table1"))
				{
					daten=db.meineDaten(conn,sortierdings,"dateiname","ASC");
				}else{
					daten=db.publicDaten(conn,"dateiname","ASC");
					
				}

				break;
			}

			case "2desc"  :{
				System.out.println("Sortieren nach Dateiname absteigend");
				if(table.equals("table1"))
				{
					daten=db.meineDaten(conn,sortierdings,"dateiname","DESC");
				}else{
					daten=db.publicDaten(conn,"dateiname","DESC");
				}

				break; 
			}

			case "3asc"  :{
				
				if(table.equals("table1"))
				{
					System.out.println("Sortieren nach Autor aufsteigend");
					daten=db.meineDaten(conn,sortierdings,"autor","ASC");
				}else{
					System.out.println("Sortieren nach Uploader aufsteigend");
					daten=db.publicDaten(conn,"uploader","ASC");
				}

				break; 
			}

			case "3desc"  :{
				
				if(table.equals("table1"))
				{
					System.out.println("Sortieren nach Autor absteigend");
					daten=db.meineDaten(conn,sortierdings,"autor","DESC");
				}else{
					System.out.println("Sortieren nach Uploader absteigend");
					daten=db.publicDaten(conn,"uploader","DESC");
				}

				break; 
			}

			case "4asc"  :{
				
				if(table.equals("table1"))
				{
					System.out.println("Sortieren nach Uploaddatum aufsteigend");
					daten=db.meineDaten(conn,sortierdings,"uploaddatum","ASC");
				}else{
					System.out.println("Sortieren nach Autor aufsteigend");
					daten=db.publicDaten(conn,"autor","ASC");
				}

				break; 
			}

			case "4desc"  :{
				if(table.equals("table1"))
				{
					System.out.println("Sortieren nach Uploaddatum absteigend");
					daten=db.meineDaten(conn,sortierdings,"uploaddatum","DESC");
				}else{
					System.out.println("Sortieren nach Autor absteigend");
					daten=db.publicDaten(conn,"autor","DESC");
				}


				break; 
			}

			case "5asc"  :{
				if(table.equals("table1"))
				{
					System.out.println("Sortieren nach Dokumentdatum aufsteigend");
					daten=db.meineDaten(conn,sortierdings,"dokumentdatum","ASC");
				}else{
					System.out.println("Sortieren nach Uploaddatum aufsteigend");
					daten=db.publicDaten(conn,"uploaddatum","ASC");
				}

				break; 
			}

			case "5desc"  :{
				if(table.equals("table1"))
				{
					System.out.println("Sortieren nach Dokumentdatum absteigend");
					daten=db.meineDaten(conn,sortierdings,"dokumentdatum","DESC");
				}else{
					System.out.println("Sortieren nach Uploaddatum absteigend");
					daten=db.publicDaten(conn,"uploaddatum","DESC");
				}

				break; 
			}
			case "6asc"  :{
				if(table.equals("table1"))
				{
					System.out.println("Sortieren nach Zugriffserlaubnis aufsteigend");
					daten=db.meineDaten(conn,sortierdings,"status","ASC");
				}else{
					System.out.println("Sortieren nach Dokumentdatum aufsteigend");
					daten=db.publicDaten(conn,"dokumentdatum","ASC");
				}
				
				break; 
			}
			case "6desc"  :{
				if(table.equals("table1"))
				{
					System.out.println("Sortieren nach Zugriffserlaubnis absteigend");
					daten=db.meineDaten(conn,sortierdings,"status","DESC");
				}else{
					System.out.println("Sortieren nach Dokumentdatum absteigend");
					daten=db.publicDaten(conn,"dokumentdatum","DESC");
				}
				
				break; 
			}
			
			case "suchwort" :{
				System.out.println("Suchwortsuche aktiv");
				db.writeStichwörter(conn, search,username);
				if(table.equals("table1"))
				{
					daten=db.durchsuchenPrivate(conn,search,username);
				}else{
					daten=db.durchsuchenPublic(conn,search);
				}
			
				for(int i=0;i<daten.size();i++)
				{
					System.out.println("Suchwortsuche aktiv");
				}
				
				break;
			}

			default:{
				if(table.equals("table1"))
				{
					daten=db.meineDaten(conn,sortierdings,"dateiname","ASC");
				}else{
					daten=db.publicDaten(conn,"dateiname","ASC");

				}

				System.out.println("Die Daten wurden nach dem Dateiname alphabetisch geordnet");
			}
			}

			String spalte="";
			String spalteninhalt="";

			if(table.equals("table1"))
			{
				spalte="uploader";
				spalteninhalt=username;
			}
			else
			{
				spalte="status";
				spalteninhalt="public";
			}

			if(sortierparameter=="suchwort")
			{
				anzahl=daten.size();
				System.out.println("Anzhal an gesuchten Dokumenten: "+anzahl);
			}
			else
			{
				boolean zustand = true;
				anzahl=db.AnzahlEinträgeDaten(conn,spalte,spalteninhalt,zustand);
			}

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		}finally{
			db.releaseConnection(conn);
		}


		int wh;
		int startwert=Integer.parseInt(start);
		int laenge=Integer.parseInt(length);
		wh=anzahl-startwert;
		System.out.println("--- Ausgabe DataTableSiteServlet: ---");
		System.out.println("Startwert: "+startwert);
		System.out.println("Länge: "+laenge);
		System.out.println("Wert: "+wh);

		String antwort=" ";

		JsonObject all = new JsonObject();
		all.addProperty("draw", draw);
		all.addProperty("recordsTotal", anzahl);
		all.addProperty("recordsFiltered", anzahl);

		JsonArray data = new JsonArray();
		
		
		antwort = "{\"draw\":"+draw+",\"recordsTotal\":"+anzahl+",\"recordsFiltered\":"+anzahl+",\"data\":[";

		if(startwert+laenge>anzahl)
		{
			wh=anzahl-1;
			System.out.println("Wert: "+wh);
		}
		else
		{
			wh=startwert+laenge-1;
		}


		if(table.equals("table1"))
		{
			for(int i=startwert;i<=wh;i++)
			{
				//antwort += "{\"ID\":\""+daten.get(i)[0]+"\",\"DateiTyp\":\""+daten.get(i)[1]+"\",\"Name\":\""+daten.get(i)[2]+"\",\"Autor\":\""+daten.get(i)[3]+"\",\"UploadDatum\":\""+daten.get(i)[4]+"\",\"DokumentDatum\":\""+daten.get(i)[5]+"\",\"ZUGANG\":\""+daten.get(i)[6]+"\"}";
				JsonObject test = new JsonObject();
				test.addProperty("ID", daten.get(i).getUploadid());
				test.addProperty("DateiTyp", daten.get(i).getDateityp());
				test.addProperty("Name", daten.get(i).getDateiname());
				test.addProperty("Autor", daten.get(i).getAutor());
				test.addProperty("UploadDatum", daten.get(i).getUploaddatum());
				test.addProperty("DokumentDatum", daten.get(i).getDokumentdatum());
				test.addProperty("ZUGANG", daten.get(i).getStatus());

				data.add(test);
				
				if(i!=wh)
				{
					
					antwort+=",";
				}
			}
		}else{
			for(int i=startwert;i<=wh;i++)
			{

				//antwort += "{\"ID\":\""+daten.get(i)[0]+"\",\"DateiTyp\":\""+daten.get(i)[1]+"\",\"Name\":\""+daten.get(i)[2]+"\",\"Uploader\":\""+daten.get(i)[3]+"\",\"Autor\":\""+daten.get(i)[4]+"\",\"UploadDatum\":\""+daten.get(i)[5]+"\",\"DokumentDatum\":\""+daten.get(i)[6]+"\",\"ZUGANG\":\""+daten.get(i)[7]+"\"}";

				JsonObject test = new JsonObject();
				test.addProperty("ID", daten.get(i).getUploadid());
				test.addProperty("DateiTyp", daten.get(i).getDateityp());
				test.addProperty("Name", daten.get(i).getDateiname());
				test.addProperty("Uploader", daten.get(i).getUploader());
				test.addProperty("Autor", daten.get(i).getAutor());
				test.addProperty("UploadDatum", daten.get(i).getUploaddatum());
				test.addProperty("DokumentDatum", daten.get(i).getDokumentdatum());
				test.addProperty("ZUGANG", daten.get(i).getStatus());
				data.add(test);
				if(i!=wh)
				{
					antwort+=",";
				}
			}
			
		}
		 antwort+="]}";
		 all.add("data", data);

		System.out.println("all: "+all);
		System.out.println("Die Transaktionsnummer ist: " +draw+". Der Suchbegriff ist: "+search+".");
		System.out.println(antwort);
		out.println(all);
	}
}