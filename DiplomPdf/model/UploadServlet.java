import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.InputStream;


import com.google.gson.Gson;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());

		//		begriffe = gson.fromJson(antwort, String[].class);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Aktuell wird die Datei temporär auf C:/Temp gespeichert
		 * wenn boolean overwrite true ist, dann gibt es die datei bereit und sie soll überschrieben werden
		 * TODO: übergabe in Datenbank
		 */
		// TODO Auto-generated method stub
		int nummer = 1;
	    Part filePart = request.getPart("pdffile"); // Retrieves <input type="file" name="file">	    
	    InputStream fileContent = filePart.getInputStream();
	 System.out.println("was steht da: "+filePart.getHeader("content-disposition").substring(36));  

	 	boolean overwrite = Boolean.parseBoolean(request.getParameter("overwrite"));	//nimmt den String und wandelt ihn in ein boolean um
        String dateiname = request.getParameter("dateiname");
        System.out.println("Name der Datei: "+dateiname+" overwrite: "+overwrite);

        uploader(fileContent,dateiname,0);
        
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
        
        
        

        
        System.out.println("Datei fertig eingelesen");


		
		
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
		String nameneu = name;
		nameneu = name.substring(0,name.length()-4);
		nameneu = nameneu+"("+nummer+")"+".pdf";
		return nameneu;
	}
}
