import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.FunktionenDB;
import model.HineinschreibenDB;

/**
 * Servlet implementation class UploadServlet
 */
//@WebServlet("/UploadServlet")
//@MultipartConfig

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//		response.getWriter().append("Served at: ").append(request.getContextPath());
		//		begriffe = gson.fromJson(antwort, String[].class);

		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Aktuell wird die Datei temporär auf C:/Temp gespeichert --> später dann alles über DB
		 * wenn boolean overwrite true ist, dann gibt es die datei bereit und sie soll überschrieben werden
		 * TODO: übergabe in DATENBANK
		 */


		int nummer = 1;
		Part filePart = request.getPart("pdffile"); // Retrieves <input type="file" name="file">	    
		InputStream fileContent = filePart.getInputStream();
		System.out.println("was steht da: "+filePart.getHeader("content-disposition").substring(36));  //TODO substring 36?

		boolean overwrite = Boolean.parseBoolean(request.getParameter("overwrite"));	//nimmt den String und wandelt ihn in ein boolean um
		String dateiname = request.getParameter("dateiname");
		System.out.println("Name der Datei: "+dateiname+" overwrite: "+overwrite);

		uploader(fileContent,dateiname,0);

		//		uploader(fileContent,dateiname,0);
		File f = new File(dateiname);
		String name = f.getName();
		String endung = name.substring(name.lastIndexOf('.')+1,name.length());
		endung.toUpperCase();
		
		System.out.println("Es handelt sich um eine ' "+ endung +" ' Datei: ");
		System.out.println("-----------------------------------------");

		switch(endung){

		case "PDF"  :{
			String inhalttext=PDFmanager.pdfToText("C://Temp//"+dateiname);
			// TODO Verena ist am workn hier
			try {
				FunktionenDB fdb=new FunktionenDB();
				Connection conn1=fdb.getConnection();
				HineinschreibenDB hdb=new HineinschreibenDB();
				Connection conn2=hdb.getConnection();
				String stichworttext=fdb.Stichtextgenerator(inhalttext);
				String[] daten =new String[3];
				daten[0]="tag";
				daten[1]=inhalttext;
				daten[2]=PDFmanager.getAutor();
				daten[3]=PDFmanager.getAutor();
				daten[4]="dateiname";
				daten[5]=PDFmanager.getDatum();
				hdb.writeDaten(daten);
				fdb.releaseConnection(conn1);
				fdb.releaseConnection(conn2);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			}
			
			System.out.println("PDF - Datei wurde in Text umgewandelt -> Weitergeben an DB");
			break;
		}

		case "TXT"  :{

			TextdateiLesen.textdateiLesen("C://Temp//"+dateiname);
			System.out.println("Txt - Datei wurde in Text umgewandelt -> Weitergeben an DB");
			break; 
		}

		case "DOC"  :{

			DocLesen.lesenDoc("C://Temp//"+dateiname);
			System.out.println("Doc - Datei wurde in Text umgewandelt -> Weitergeben an DB");
			break; 
		}

		case "DOCX"  :{

			DocxLesen.lesenDocx("C://Temp//"+dateiname);
			System.out.println("Docx - Datei wurde in Text umgewandelt -> Weitergeben an DB");
			break; 
		}
		default:{
			System.out.println("Datei kann nicht gespeichert werden, Dateityp wird nicht unterstützt");
		}



		}
		/*
        String pfad =getInitParameter("Pfad");
        File file = createFile(pfad, dateiname);
        try{
            Files.copy(fileContent, file.toPath());
        }	
        catch(Exception ex){
        	System.out.println("ERROR DATEI BEREITS VORHANDEN");
        }
		 */
		System.out.println("Datei fertig eingelesen (noch nicht ganz DB speicherung fehlt bis jetzt )");
	}

	private void uploader(InputStream fileContent, String dateiname,int nummer){

		String pfad = getInitParameter("Pfad");
		File file = createFile(pfad, dateiname);

		try{
			Files.copy(fileContent, file.toPath());
			System.out.println("Datei gespeichert. Sie war bisher "+nummer+" mal vorhanden");

		}	
		catch(Exception ex){
			System.out.println("ERROR DATEI BEREITS VORHANDEN");
			nummer++;
			uploader(fileContent, NamensNummerierer(dateiname,nummer),nummer);
		}
	}

	private File createFile(String pfad, String name){
		File uploads = new File(pfad);
		File file = new File(uploads, name);
		return file;

	}

	private String NamensNummerierer(String name,int nummer){

		String nameneu;
		String endungneu = name.substring(name.lastIndexOf('.')+1,name.length());
		endungneu.toUpperCase();
	
		switch(endungneu) {
		case "PDF": {
			nameneu = name;
			nameneu = name.substring(0,name.length()-4);
			nameneu = nameneu+"("+nummer+")"+".pdf";
			return nameneu; 
		}
		case "DOC": {
			nameneu = name;
			nameneu = name.substring(0,name.length()-4);
			nameneu = nameneu+"("+nummer+")"+".doc";
			return nameneu; 
		}
		case "DOCX": {
			nameneu = name;
			nameneu = name.substring(0,name.length()-4);
			nameneu = nameneu+"("+nummer+")"+".docx";
			return nameneu; 
		}
		case "TXT": {
			nameneu = name;
			nameneu = name.substring(0,name.length()-4);
			nameneu = nameneu+"("+nummer+")"+".txt";
			return nameneu;
		}
		default:{
			System.out.println("Neuer Name konnte nicht vergeben werden, Dateityp wird nicht unterstützt");
		}

		}
		return null;
	}
}
