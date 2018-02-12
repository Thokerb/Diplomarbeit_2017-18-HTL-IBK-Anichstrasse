import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.DBManager;

/**
 * Servlet implementation class UploadServlet
 */
//@WebServlet("/UploadServlet")
//@MultipartConfig

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Aktuell wird die Datei temporär auf C:/Temp gespeichert --> später dann alles über DB
		 * wenn boolean overwrite true ist, dann gibt es die datei bereit und sie soll überschrieben werden
		 * TODO: übergabe in DATENBANK
		 */
		
		int nummer = 1;
	
		String pfad = getInitParameter("Pfad");
		System.out.println("Pfad: "+pfad);

		request.setCharacterEncoding("UTF-8");

		Part filePart = request.getPart("pdffile"); // Retrieves <input type="file" name="file">	
		System.out.println(filePart);
		InputStream fileContent = filePart.getInputStream();
		System.out.println("was steht da: "+filePart.getHeader("content-disposition").substring(36));  //TODO substring 36?
		String user = request.getParameter("username");
		System.out.println(user);
		
		boolean overwrite = Boolean.parseBoolean(request.getParameter("overwrite"));	//nimmt den String und wandelt ihn in ein boolean um
		String dateiname = request.getParameter("dateiname");
		System.out.println("Name der Datei: "+dateiname+" overwrite: "+overwrite);
		dateiname = java.net.URLDecoder.decode(dateiname, "UTF-8");
		System.out.println("utf8"+dateiname);

		uploader(fileContent,dateiname,0);
		fileContent.close();
		fileContent = null;
		File f = new File(dateiname);
		String name = f.getName();

		String dateityp=name.substring(name.lastIndexOf('.')+1,name.length());
		dateityp = dateityp.toUpperCase();
		System.out.println("Es handelt sich um eine ' "+ dateityp +" ' Datei: ");
		System.out.println("-----------------------------------------");
		
		HttpSession ses = request.getSession(false); //TODO: if 
		String username = (String) ses.getAttribute("user"); 
		
		if(overwrite){
			try {
				DBManager dbm = new DBManager();
				Connection conn = dbm.getConnection();	//TODO: OVERWRITE TESTEN
				dbm.deletebyname(dateiname,username,conn);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		switch(dateityp){

		case "PDF"  :{
			
			PDFLesen pdfL = new PDFLesen();
		
			String inhalttext = pdfL.pdfToText(pfad+dateiname); 
			System.out.println("angemeldeter Username: "+username);
			
			try {
				DBManager dbm=new DBManager();
				Connection conn1=dbm.getConnection();
				String stichworttext=dbm.Stichtextgenerator(conn1,inhalttext);
				//tag, inhalttext, uploader, autor, dateiname, uploaddatum, stichworttext, dateityp
				String[] daten =new String[7];
				daten[0]="tag";
				daten[1]=inhalttext;
				daten[2]=username;
				daten[3]=pdfL.getAutor(); 
				daten[4]=dateiname;
				daten[5]=stichworttext;
				daten[6]=dateityp;
				
				for(String s : daten) {
					System.out.print("Gelesen wurde: ");
					System.out.println(s);
				}
				DBManager.writeDaten(conn1,daten,filePart,pdfL.getDatum());
				//DBManager.Blobeinfuegen(filePart,stichworttext);
				
				dbm.releaseConnection(conn1);
				System.out.println(inhalttext);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			}
			System.out.println("PDF - Datei wurde in Text umgewandelt + Weitergabe an DB");
			break;
		}

		case "TXT"  :{

			TextdateiLesen txtL = new TextdateiLesen();
			String inhalttext = txtL.textdateiLesen(pfad+dateiname);

			System.out.println("angemeldeter Username: "+username);

			try {
				DBManager dbm = new DBManager();
				Connection conn1 = dbm.getConnection();
				String stichworttext = dbm.Stichtextgenerator(conn1,inhalttext);
				//tag, inhalttext, uploader, autor, dateiname, uploaddatum, stichworttext, dateityp
				String[] daten =new String[8];
				daten[0]="tag";
				daten[1]=inhalttext;
				daten[2]=username;
				daten[3]=txtL.getAutor();
				daten[4]=dateiname;
				daten[5]=stichworttext; 
				daten[6]=dateityp;

				for(String s : daten) {
					System.out.print("Gelesen wurde: ");
					System.out.println(s);
				}
				DBManager.writeDaten(conn1,daten,filePart,txtL.getDatum());
				//DBManager.Blobeinfuegen(filePart,stichworttext);
				
				dbm.releaseConnection(conn1);
				System.out.println("inhalttext");
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			}

			System.out.println("TXT - Datei wurde in Text umgewandelt -> Weitergeben an DB (Achtung, keine Metadaten vorhanden)");
			break; 
		}

		case "DOC"  :{

			DocLesen docL = new DocLesen();
			String inhalttext = docL.lesenDoc(pfad+dateiname);

			//TODO alles ausbessern
			try {
				DBManager dbm=new DBManager();
				Connection conn1=dbm.getConnection();
				String stichworttext=dbm.Stichtextgenerator(conn1,inhalttext);
				//tag, inhalttext, uploader, autor, dateiname, uploaddatum, stichworttext, dateityp
				String[] daten =new String[7];
				daten[0]="tag";
				daten[1]=inhalttext;
				daten[2]=username;
				daten[3]=docL.getAutor(); 
				daten[4]=dateiname;
				daten[5]=stichworttext; 
				daten[6]=dateityp;

				for(String s : daten) {
					System.out.print("Gelesen wurde: ");
					System.out.println(s);
				}
				DBManager.writeDaten(conn1,daten,filePart,docL.getDatum());
				//DBManager.Blobeinfuegen(filePart,stichworttext);
				
				dbm.releaseConnection(conn1);
				System.out.println("inhalttext");
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			}


			System.out.println("Doc - Datei wurde in Text umgewandelt -> Weitergeben an DB");
			break; 
		}

		case "DOCX"  :{

			DocxLesen docxL = new DocxLesen();
			String inhalttext = docxL.lesenDocx(pfad+dateiname);
			
			System.out.println("Inhalttext in Dokument: "+inhalttext);

			try {
				DBManager dbm=new DBManager();
				Connection conn1=dbm.getConnection();
				String stichworttext=dbm.Stichtextgenerator(conn1,inhalttext);
				//tag, inhalttext, uploader, autor, dateiname, uploaddatum, stichworttext, dateityp
				//aus writeDaten: tag, inhalttext, uploader, autor, dateiname, stichworttext, dateityp, status, dokumentdatum, uploaddatum, blobdatei
				String[] daten =new String[7];
				daten[0]="tag";
				daten[1]=inhalttext;
				daten[2]=username;
				daten[3]=docxL.getAutor(); 
				daten[4]=dateiname;
				daten[5]=stichworttext;
				daten[6]=dateityp;


				for(String s : daten) {
					System.out.print("Gelesen wurde: ");
					System.out.println(s);
				}
				DBManager.writeDaten(conn1,daten,filePart,docxL.getDatum());
				//DBManager.Blobeinfuegen(filePart,stichworttext);
				
				dbm.releaseConnection(conn1);
				System.out.println("inhalttext");
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			}

			System.out.println("Docx - Datei wurde in Text umgewandelt -> Weitergeben an DB");
			break; 
		}

		default:{

			System.out.println("Datei kann nicht gespeichert werden, Dateityp "+ dateityp +"wird nicht unterstützt");
		}



		}
		
		System.out.println("Is writeable: "+Files.isWritable(Paths.get(pfad+dateiname)));
		System.out.println("IS: "+Files.exists(Paths.get(pfad+dateiname)));
		
		Files.deleteIfExists(Paths.get(pfad+dateiname));
	
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
		finally {
			try {
				fileContent.close();
				fileContent = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private File createFile(String pfad, String name){
		File uploads = new File(pfad);
		File file = new File(uploads, name);
		return file;

	}

	private String NamensNummerierer(String name,int nummer){

		String nameneu;
		String dateitypneu = name.substring(name.lastIndexOf('.')+1,name.length());
		dateitypneu = dateitypneu.toUpperCase();

		switch(dateitypneu) {
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
			nameneu = name.substring(0,name.length()-5); // 5 ?
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
			//TODO Message senden an Client
			System.out.println("Neuer Name konnte nicht vergeben werden, Dateityp wird nicht unterstuetzt");
		}

		}
		return null;
	}
}
