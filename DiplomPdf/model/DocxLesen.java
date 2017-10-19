import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class DocxLesen { 
	
	public static void main(String[] args) {
		
		
		try {
			FileInputStream fis = new FileInputStream("C://Users//Sara//Dropbox//Diplomarbeit//test.docx");
			XWPFDocument docx = new XWPFDocument(fis);
			List<XWPFParagraph> paragrafListe = docx.getParagraphs();
			
			
			for(XWPFParagraph paragraph: paragrafListe){
				String text = paragraph.getText();
				System.out.println(text);
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
