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

public class DocLesen implements IStrategy {

	static String text;
	static String aut; 
	static Date date; 
	static DateFormat formatter;
	static String d; 
	
	public String textAuslesen(String filename) throws IllegalArgumentException, FileNotFoundException, IOException{

			FileInputStream fis = new FileInputStream(filename);		

			HWPFDocument doc = new HWPFDocument(fis);
			
			aut = doc.getSummaryInformation().getAuthor();
			date = doc.getSummaryInformation().getCreateDateTime();
		
	        formatter = new SimpleDateFormat("dd.MM.yyyy");
	        d = formatter.format(date);
			
			WordExtractor extractor = new WordExtractor(doc);
			String[] fileData = extractor.getParagraphText();
			text = extractor.getText();
			System.out.println("----------------- Text aus DOC Lesen: -----------------");
			System.out.println(text); 
			System.out.println("----------------- INFO: -----------------");
			System.out.println(aut);
			System.out.println(d);

			System.out.println("----------------ENDE DOC -----------------");

			releaseRessoruces(fis, extractor);
		
			return text;

	}
	
	public void releaseRessoruces(FileInputStream fis, WordExtractor extractor) {
		try {
			fis.close();
			extractor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public String getAutor() {
		return aut; 
	}
	
	public String getDatum() {
		return d; 
	}


//		public static void main(String[] args) {
//			DocLesen l1 = new DocLesen();
//			try {
//				l1.textAuslesen("C://Users//Sara//Dropbox//Diplomarbeit//KillerDoc.doc");
//				
//			} catch (IllegalArgumentException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	
}