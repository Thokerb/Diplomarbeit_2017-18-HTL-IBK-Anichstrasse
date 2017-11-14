import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;


public class DocLesen {

	public String lesenDoc(String filename){

		try {
			System.out.println("Verwendete Datei: "+filename);  //Kontrolle
			//			FileInputStream fis = new FileInputStream("C://Users//Sara//Dropbox//Diplomarbeit//Doc.doc"); //testzweck
			FileInputStream fis = new FileInputStream(filename); //allgemein		

			/**
			 * Hierbei handelt es sich nur um Testmethode welche festlegt, um welchen Typ es sich handelt
			 * (eher außerhalb verwenden für Prüfung)
			 *
			File f = new File("C://Users//Sara//Dropbox//Diplomarbeit//Doc.doc"); //testzweck 
			File f = new File(filename); //allgemein
			String name = f.getName();
			System.out.println("Es handelt sich um eine ' "+name.substring(name.lastIndexOf('.')+1,name.length())+" ' Datei:");
			System.out.println("-----------------------------------------");
			 **/
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

	public static void main(String[] args) {

		DocLesen l1 = new DocLesen();
		l1.lesenDoc("C://Users//Sara//Dropbox//Diplomarbeit//Doc.doc");


	}
}