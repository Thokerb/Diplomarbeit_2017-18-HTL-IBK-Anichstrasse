import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DocxLesen { 	

	public static String lesenDocx(String filename){

		try {

			FileInputStream fis = new FileInputStream(filename);

			XWPFWordExtractor oleTextExtractor = new XWPFWordExtractor(new XWPFDocument(fis));
			String text = null; 
			text = oleTextExtractor.getText();
			System.out.println("----------------Text-----------------");
			System.out.println(text);
			System.out.println("----------------Text-----------------");
			
//			oleTextExtractor.close();
			return text; 

			//			FileInputStream fis = new FileInputStream("C://Users//Sara//Dropbox//Diplomarbeit//test.docx");
			//
			//			XWPFDocument docx = new XWPFDocument(fis);
			//			List<XWPFParagraph> paragrafListe = docx.getParagraphs();
			//
			//
			//			for(XWPFParagraph paragraph: paragrafListe){
			//				String text = paragraph.getText();
			//				System.out.println(text);
			//			}


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


//	public static void main(String[] args) {
//		DocxLesen l1 = new DocxLesen();
//		l1.lesenDocx("C://Users//Sara//Dropbox//Diplomarbeit//test.docx");
//	}

}
