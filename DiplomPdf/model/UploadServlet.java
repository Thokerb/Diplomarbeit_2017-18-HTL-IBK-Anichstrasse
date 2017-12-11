import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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

		doPost(request, response); //? Ok? 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Aktuell wird die Datei tempor�r auf C:/Temp gespeichert --> sp�ter dann alles �ber DB
		 * wenn boolean overwrite true ist, dann gibt es die datei bereit und sie soll �berschrieben werden
		 * TODO: �bergabe in DATENBANK
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
		System.out.println("Es handelt sich um eine ' "+name.substring(name.lastIndexOf('.')+1,name.length())+" ' Datei: ");
		System.out.println("-----------------------------------------");

		switch(name.substring(name.lastIndexOf('.')+1,name.length())){

		case "pdf"  :{
			String wort=PDFboxLesen.lesenPDF("C://Temp//"+dateiname);
			model.Datenbank3.VerbindungAufbauen();
			model.Datenbank3.Stichworttextgenerator(wort);
			System.out.println("PDF - Datei wurde in Text umgewandelt -> Weitergeben an DB");
			break;
		}

		case "txt"  :{

			TextdateiLesen.textdateiLesen("C://Temp//"+dateiname);
			System.out.println("Txt - Datei wurde in Text umgewandelt -> Weitergeben an DB");
			break; 
		}

		case "doc"  :{

			DocLesen.lesenDoc("C://Temp//"+dateiname);
			System.out.println("Doc - Datei wurde in Text umgewandelt -> Weitergeben an DB");
			break; 
		}

		case "docx"  :{

			DocxLesen.lesenDocx("C://Temp//"+dateiname);
			System.out.println("Docx - Datei wurde in Text umgewandelt -> Weitergeben an DB");
			break; 
		}
		default:{
			System.out.println("Datei kann nicht gespeichert werden, Dateityp wird nicht unterst�tzt");
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
		System.out.println("Datei fertig eingelesen (noch nicht ganz)");
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

		switch(name.substring(name.lastIndexOf('.')+1,name.length())) {
		case "pdf": {
			nameneu = name;
			nameneu = name.substring(0,name.length()-4);
			nameneu = nameneu+"("+nummer+")"+".pdf";
			return nameneu; 
		}
		case "doc": {
			nameneu = name;
			nameneu = name.substring(0,name.length()-4);
			nameneu = nameneu+"("+nummer+")"+".doc";
			return nameneu; 
		}
		case "docx": {
			nameneu = name;
			nameneu = name.substring(0,name.length()-4);
			nameneu = nameneu+"("+nummer+")"+".docx";
			return nameneu; 
		}
		case "txt": {
			nameneu = name;
			nameneu = name.substring(0,name.length()-4);
			nameneu = nameneu+"("+nummer+")"+".txt";
			return nameneu;
		}
		default:{
			System.out.println("Neuer Name konnte nicht vergeben werden, Dateityp wird nicht unterst�tzt");
		}

		}
		return null;
	}
}
