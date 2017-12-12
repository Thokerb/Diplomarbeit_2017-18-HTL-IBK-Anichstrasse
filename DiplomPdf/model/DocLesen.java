import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
/**
 * @author Sara
 * 
 * Extrahiert Text (und hoffendlich bald auch Datum und Autor) aus einer .doc Datei 
 * Verwendet dabei die Apache POI Library, welche im Builthpath eingebettet ist 
 */

public class DocLesen {

	public static String lesenDoc(String filename){

		try {
			System.out.println("Verwendete Datei: "+filename);  //Kontrolle
//						FileInputStream fis = new FileInputStream("C://Users//Sara//Dropbox//Diplomarbeit//Doc.doc"); //testzweck
			FileInputStream fis = new FileInputStream(filename); //allgemein		

			HWPFDocument doc = new HWPFDocument(fis);
			WordExtractor extractor = new WordExtractor(doc);
			String[] fileData = extractor.getParagraphText();
			String text = extractor.getText();
			System.out.println(text); 

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

//		public static void main(String[] args) {
//			DocLesen l1 = new DocLesen();
//			l1.lesenDoc("C://Users//Sara//Dropbox//Diplomarbeit//Doc.doc");
//		}
	
}