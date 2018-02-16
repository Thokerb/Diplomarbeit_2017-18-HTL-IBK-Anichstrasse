import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * @author Sara
 * 
 * Extrahiert Text (und hoffendlich bald auch Datum und Autor) aus einer .docx Datei 
 * Verwendet dabei die Apache POI Library, welche im Builthpath eingebettet ist 
 */

public class DocxLesen { 	
	
	static String text = null;
	static String aut = null; 
	static Date date = null;
	static DateFormat formatter;
	static String d; 

	public String lesenDocx(String filename){

		FileInputStream fis;
		XWPFWordExtractor oleTextExtractor;
		
		try {

			fis = new FileInputStream(filename);
			oleTextExtractor = new XWPFWordExtractor(new XWPFDocument(fis));
			
			aut = oleTextExtractor.getCoreProperties().getCreator();
			date = oleTextExtractor.getCoreProperties().getCreated();
			
			formatter = new SimpleDateFormat("dd.MM.yyyy");
		    d = formatter.format(date);
		    
			text = oleTextExtractor.getText();
			System.out.println("----------------- Text aus DOCX Lesen: -----------------");
			System.out.println(text);
			System.out.println("---------------- INFO: -----------------");

			System.out.println(aut);
			System.out.println(d);
			System.out.println("---------------- ENDE DOCX -----------------");

			oleTextExtractor.close(); //os.filesystem.delete file, mit pfad
			fis.close();
			fis = null;
			return text; 

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Fehler! Datei konnte nicht gefunden werden");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Fehler! Datei konnte nicht gelesen werden!");
		}
		
		return "Achtung - Fehler! Datei Konnte nicht gelesen werden"; 
	}

	public String getDatum() {
		return d;
	}

	public String getAutor() {
		return aut;
	}

//	public static void main(String[] args) {
//		DocxLesen l1 = new DocxLesen();
//		l1.lesenDocx("C://Users//Sara//Dropbox//Diplomarbeit//TestfälleSara.docx");
//	}

}
