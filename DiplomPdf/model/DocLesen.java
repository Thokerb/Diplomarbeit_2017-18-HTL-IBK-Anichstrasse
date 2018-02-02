import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
/**
 * @author Sara
 * 
 * Extrahiert Text (und hoffendlich bald auch Datum und Autor) aus einer .doc Datei 
 * Verwendet dabei die Apache POI Library, welche im Builthpath eingebettet ist 
 */

public class DocLesen {

	
	static String text;
	static String aut; 
	static Date date; 
	static DateFormat formatter;
	static String d; 
	
	public static String lesenDoc(String filename){

		try {
			
//			System.out.println("Verwendete Datei: "+filename);  //Kontrolle
			FileInputStream fis = new FileInputStream(filename); //allgemein		

			HWPFDocument doc = new HWPFDocument(fis);
			
			aut = doc.getSummaryInformation().getAuthor();
			date = doc.getSummaryInformation().getCreateDateTime();
			
			System.out.println(date);
		
	        formatter = new SimpleDateFormat("yyyy-MM-dd");
	        d = formatter.format(date);
			
			WordExtractor extractor = new WordExtractor(doc);
			String[] fileData = extractor.getParagraphText();
			text = extractor.getText();
			System.out.println("----------------- Text aus DOC Lesen: -----------------");
			System.out.println(text); 
			System.out.println("----------------- INFO: -----------------");
			System.out.println(aut);
			System.out.println(d);
			System.out.println("----------------------------------");
			
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
		return "Datei konnte nicht gelesen werden!";

	}
	
	public static String getAutor() {
		return aut; 
	}
	
	public static String getDatum() {
		return d; 
	}

		public static void main(String[] args) {
			DocLesen l1 = new DocLesen();
			l1.lesenDoc("C://Users//Sara//Dropbox//Diplomarbeit//Doc.doc");
		}
	
}