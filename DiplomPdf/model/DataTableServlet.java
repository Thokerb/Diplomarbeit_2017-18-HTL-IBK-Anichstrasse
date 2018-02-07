
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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.DBManager;
import model.Uploaddaten;


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
		//		

		/**
		 * Hier sollte je nach dem welcher button zum sortieren der Daten die Antwort anders sein, sortierparameter einstellen
		 */

		String sortierspalte="";
		String sortierdings="";
		if(table.equals("table1"))
		{
			sortierspalte="uploader";
			sortierdings=username;
		}
		else
		{
			sortierspalte="status";
			sortierdings="public";
		}

		//sortierparameter muss Spalte+ASC oder DESC sein
		Connection conn=null;

		ArrayList<String[]> daten = new ArrayList<String[]>();
		int anzahl = 0;
		int anzahls=0;

		try {
			DBManager db = new DBManager();
			conn=db.getConnection();

			//list = db.readDaten(conn);

			if(search!=null&&!search.isEmpty())
			{
				sortierparameter="suchwort";
			}

			switch(sortierparameter){

			case "2asc"  :{
				daten=db.meineDaten(conn,sortierdings,"dateiname","ASC",sortierspalte);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break;
			}

			case "2desc"  :{
				daten=db.meineDaten(conn,sortierdings,"dateiname","DESC",sortierspalte);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "3asc"  :{
				daten=db.meineDaten(conn,sortierdings,"autor","ASC",sortierspalte);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "3desc"  :{
				daten=db.meineDaten(conn,sortierdings,"autor","DESC",sortierspalte);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "4asc"  :{
				daten=db.meineDaten(conn,sortierdings,"uploaddatum","ASC",sortierspalte);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "4desc"  :{
				daten=db.meineDaten(conn,sortierdings,"uploaddatum","DESC",sortierspalte);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "5asc"  :{
				daten=db.meineDaten(conn,sortierdings,"dokumentdatum","ASC",sortierspalte);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "5desc"  :{
				daten=db.meineDaten(conn,sortierdings,"dokumentdatum","DESC",sortierspalte);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
				}
				break; 
			}

			case "suchwort" :{
				System.out.println("Suchwortsuche aktiv");
				daten=db.ranking2(conn,search);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println("Suchwortsuche aktiv");
				}
				anzahlsuch=daten.get(0)[0];
				anzahls=Integer.parseInt(anzahlsuch);
				break;
			}

			default:{

				daten=db.meineDaten(conn,sortierdings,"dateiname","ASC",sortierspalte);
				for(int i=0;i<daten.size();i++)
				{
					System.out.println(daten.get(i)[1]);
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

			if(anzahls>0)
			{
				anzahl=anzahls;
				System.out.println("Anzahl der Ergebnisse bei Suchwortsuche: "+anzahl);
			}
			else
			{
				anzahl=db.AnzahlEinträge1(conn,spalte,spalteninhalt);
			}

			db.releaseConnection(conn);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		}


		int wh;
		int startwert=Integer.parseInt(start);
		int laenge=Integer.parseInt(length);
		wh=anzahl-startwert;
		System.out.println("startwert: "+startwert);
		System.out.println("lÄnGeee: "+laenge);
		System.out.println("wert: "+wh);

		int total=wh-1;
		String antwort=" ";


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
		
		if(anzahls==0)
		{
			
		}
		else
		{
			
		for(int i=startwert;i<=wh;i++)
		{
		
			if(anzahls>0)
			{
				antwort += "{\"ID\":\""+daten.get(i)[1]+"\",\"DateiTyp\":\""+daten.get(i)[2]+"\",\"Name\":\""+daten.get(i)[3]+"\",\"Autor\":\""+daten.get(i)[4]+"\",\"UploadDatum\":\""+daten.get(i)[5]+"\",\"DokumentDatum\":\""+daten.get(i)[6]+"\",\"ZUGANG\":\""+daten.get(i)[7]+"\"}";

			}
			else
			{
				antwort += "{\"ID\":\""+daten.get(i)[0]+"\",\"DateiTyp\":\""+daten.get(i)[1]+"\",\"Name\":\""+daten.get(i)[2]+"\",\"Autor\":\""+daten.get(i)[3]+"\",\"UploadDatum\":\""+daten.get(i)[4]+"\",\"DokumentDatum\":\""+daten.get(i)[5]+"\",\"ZUGANG\":\""+daten.get(i)[6]+"\"}";

			}
			if(i!=wh)
			{
				antwort+=",";
			}
		}
		antwort += "]}";
		}

		System.out.println("Die Transaktionsnummer ist: " +draw+". Der Suchbegriff ist: "+search+".");
		System.out.println(antwort);
		out.println(antwort);
	}

}
