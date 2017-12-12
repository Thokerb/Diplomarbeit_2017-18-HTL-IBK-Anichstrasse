import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;



public class PDFmanager {

	private static String text ;
	private static String autor ;
	private static String datum ;
	private static File file;
	String info;

	public PDFmanager() {}


	private static String getDatum(Calendar cal){

		SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
		String pD = d.format(cal.getTime());
		return pD;
	}

	public static String pdfToText(String filePath) throws IOException {

		file = new File(filePath);
		PDDocument document = PDDocument.load(file);
		
		PDFTextStripper pdfStripper = new PDFTextStripper();
		text = pdfStripper.getText(document);
		document.close();
		PDDocumentInformation info = document.getDocumentInformation();
		
		autor = info.getAuthor();
		datum =  getDatum(info.getCreationDate());
		
//		System.out.println( "Autor: " + info.getAuthor() );
//		System.out.println( "Autor: " + info.getCreator()); // gibt zB nur Microsoft zurück
//		System.out.println("Erstelldatum: " + getDatum(info.getCreationDate()));
		
		System.out.println("Ausgabe PDFmanager: \n "+text);
		
		return text;
	}
	
	public static void main(String[] args) {
		try {
			pdfToText("C:\\Users\\Sara\\Desktop\\Einführung in das Projektmanagement.pdf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void getInfoPDF(){
		System.out.println("	Autor: "+ autor);
		System.out.println("	Erstelldatum: "+ datum);
		
	}
	
	public static String getAutor(){ 
		return autor; 
	}
	
	public static String getDatum(){ 
		return datum; 
	}

}

