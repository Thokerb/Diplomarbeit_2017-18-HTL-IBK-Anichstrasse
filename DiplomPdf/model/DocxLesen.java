import java.io.*;
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
	
	static String autor;
	static Date datum;

	public static String lesenDocx(String filename){

		try {

			FileInputStream fis = new FileInputStream(filename);

//			POIFSFileSystem poifs = new POIFSFileSystem(fis);
//
//			DirectoryEntry dir = poifs.getRoot();
//			SummaryInformation si;
//			try {
//				DocumentEntry siEntry = (DocumentEntry) dir.getEntry(SummaryInformation.DEFAULT_STREAM_NAME);
//				DocumentInputStream dis = new DocumentInputStream(siEntry);
//				PropertySet ps = new PropertySet(dis);
//				dis.close();
//				si = new SummaryInformation(ps);
//			}catch (FileNotFoundException | UnexpectedPropertySetTypeException | NoPropertySetStreamException | MarkUnsupportedException ex)
//			{
//				si = PropertySetFactory.newSummaryInformation();
//			}
			
			XWPFWordExtractor oleTextExtractor = new XWPFWordExtractor(new XWPFDocument(fis));
			String text = null; 

			text = oleTextExtractor.getText();
			System.out.println("----------------- Text aus DOCX Lesen: -----------------");
			System.out.println(text);
			System.out.println("---------------- Text -----------------");

//
//			autor = si.getAuthor();
//			datum = si.getCreateDateTime();
//			System.out.println("Autor: "+autor +"Datum: "+ datum);

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


	public static void main(String[] args) {
		DocxLesen l1 = new DocxLesen();
		l1.lesenDocx("C://Users//Sara//Dropbox//Diplomarbeit//test.docx");
	}

}
