import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;


public class DocLesen { 

	public static void main(String[] args) {

		try {
			FileInputStream fis = new FileInputStream("C://Users//Sara//Dropbox//Diplomarbeit//Doc.doc");
			HWPFDocument doc = new HWPFDocument(fis);
			WordExtractor extractor = new WordExtractor(doc);
			String[] fileData = extractor.getParagraphText();
			String text = extractor.getText();
			System.out.println(text);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
