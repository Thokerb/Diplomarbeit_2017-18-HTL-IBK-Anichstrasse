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

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.postgresql.util.PSQLException;

import model.DBManager;
import model.Daten;

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

		DBManager dbm = null;
		Connection conn = null;

		if(overwrite){
			try {
				dbm = new DBManager();
				conn = dbm.getConnection();	//TODO: OVERWRITE TESTEN
				dbm.deletebyname(dateiname,username,conn);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dbm.releaseConnection(conn);
			}
		}


		switch(dateityp){

		case "PDF"  :{

			System.out.println("angemeldeter Username: "+username);

			try {

				PDFLesen pdfL = new PDFLesen();

				StrategyContext context = new StrategyContext();
				context.setStrategy(pdfL);

				String inhalttext = context.executeStrategy(pfad+dateiname);

				dbm=new DBManager();
				conn=dbm.getConnection();
				String stichworttext=dbm.Stichtextgenerator(conn,inhalttext);
				//tag, inhalttext, uploader, autor, dateiname, uploaddatum, stichworttext, dateityp
				Daten daten =new Daten();
				daten.setInhalttext(inhalttext);
				daten.setUploader(username);
				daten.setAutor(pdfL.getAutor()); 
				daten.setDateiname(dateiname);
				daten.setStichworttext(stichworttext);
				daten.setDateityp(dateityp);
				daten.setDokumentdatum(pdfL.getDatum());

				DBManager.writeDaten(conn,daten,filePart);

			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			} finally {
				dbm.releaseConnection(conn);
			}
			System.out.println("PDF - Datei wurde in Text umgewandelt + Weitergabe an DB");
			break;
		}

		case "TXT"  :{

			System.out.println("angemeldeter Username: "+username);

			try {

				TextdateiLesen txtL = new TextdateiLesen();

				StrategyContext context = new StrategyContext();
				context.setStrategy(txtL);

				String inhalttext = context.executeStrategy(pfad+dateiname);

				dbm = new DBManager();
				conn = dbm.getConnection();
				String stichworttext = dbm.Stichtextgenerator(conn,inhalttext);
				//tag, inhalttext, uploader, autor, dateiname, uploaddatum, stichworttext, dateityp
				Daten daten =new Daten();
				daten.setInhalttext(inhalttext);
				daten.setUploader(username);
				daten.setAutor(txtL.getAutor()); 
				daten.setDateiname(dateiname);
				daten.setStichworttext(stichworttext);
				daten.setDateityp(dateityp);

				if(!DBManager.writeDaten(conn,daten,filePart)) {
					response.setStatus(HttpServletResponse.SC_CONFLICT);
					response.getWriter().println("Fehlerhafte Datei");
					break;
				}

			} catch(PSQLException e){
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				response.getWriter().println("Fehlerhafte Datei");
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			} finally {
				dbm.releaseConnection(conn);
			}
			System.out.println("TXT - Datei wurde in Text umgewandelt + an DB weitergegeben ");
			break; 
		}

		case "DOC"  :{

			DocLesen docL = new DocLesen();

			try {

				StrategyContext context = new StrategyContext();
				context.setStrategy(docL);

				String inhalttext = context.executeStrategy(pfad+dateiname);

				dbm=new DBManager();
				conn=dbm.getConnection();
				String stichworttext=dbm.Stichtextgenerator(conn,inhalttext);
				//tag, inhalttext, uploader, autor, dateiname, uploaddatum, stichworttext, dateityp
				Daten daten =new Daten();
				daten.setInhalttext(inhalttext);
				daten.setUploader(username);
				daten.setAutor(docL.getAutor()); 
				daten.setDateiname(dateiname);
				daten.setStichworttext(stichworttext);
				daten.setDateityp(dateityp);
				daten.setDokumentdatum(docL.getDatum());

				DBManager.writeDaten(conn,daten,filePart);

			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			} catch(IllegalArgumentException e){
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				response.getWriter().println("Fehlerhafte Datei");
				e.printStackTrace();
			} finally {
				dbm.releaseConnection(conn);
			}

			System.out.println("Doc - Datei wurde in Text umgewandelt + an DB weitergegeben ");
			break; 
		}

		case "DOCX"  :{

			DocxLesen docxL = new DocxLesen();

			try {

				StrategyContext context = new StrategyContext();
				context.setStrategy(docxL);

				String inhalttext = context.executeStrategy(pfad+dateiname);

				dbm=new DBManager();
				conn=dbm.getConnection();
				String stichworttext=dbm.Stichtextgenerator(conn,inhalttext);
				//tag, inhalttext, uploader, autor, dateiname, uploaddatum, stichworttext, dateityp
				//aus writeDaten: tag, inhalttext, uploader, autor, dateiname, stichworttext, dateityp, status, dokumentdatum, uploaddatum, blobdatei
				Daten daten =new Daten();
				daten.setInhalttext(inhalttext);
				daten.setUploader(username);
				daten.setAutor(docxL.getAutor()); 
				daten.setDateiname(dateiname);
				daten.setStichworttext(stichworttext);
				daten.setDateityp(dateityp);
				daten.setDokumentdatum(docxL.getDatum());

				DBManager.writeDaten(conn,daten,filePart);


			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch(SQLException e){
				e.printStackTrace();
			} catch(NotOfficeXmlFileException e){
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				response.getWriter().println("Fehlerhafte Datei");
				e.printStackTrace();
			}catch(IllegalArgumentException e) {
				e.printStackTrace();
			} finally {
				dbm.releaseConnection(conn);
			}

			System.out.println("Docx - Datei wurde in Text umgewandelt + an DB weitergegeben ");
			break; 
		}

		default:{

			System.out.println("Datei kann nicht gespeichert werden, Dateityp "+ dateityp +"wird nicht unterstützt");
		}

		}
		try {

			System.out.println("Is writeable: "+Files.isWritable(Paths.get(pfad+dateiname)));
			System.out.println("IS: "+Files.exists(Paths.get(pfad+dateiname)));
			Files.deleteIfExists(Paths.get(pfad+dateiname));

		}catch(Exception e){
			e.printStackTrace();
		}


	}

	private void uploader(InputStream fileContent, String dateiname,int nummer){

		String pfad = getInitParameter("Pfad");
		File file = createFile(pfad, dateiname);
		System.out.println("sys prop:");
		String d = 	System.getProperty("user.dir");
		System.out.println(d);
		try{
			Files.copy(fileContent, file.toPath());
			System.out.println("Datei gespeichert. Sie war bisher "+nummer+" mal vorhanden");


		}	
		catch(IOException ex){
			ex.printStackTrace();
		}	

		finally {
			try {
				fileContent.close();
				fileContent = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private File createFile(String pfad, String name){
		File uploads = new File(pfad);
		uploads.mkdirs();
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
