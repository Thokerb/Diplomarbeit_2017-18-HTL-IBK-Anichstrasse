import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class PDFLesen implements IStrategy{
	
	private String autor ;
	private String date ;
	private File file;

	String info;

	public String text = "text";
	
	public String textAuslesen(String filePath) throws IOException {
		PDFParser parser = null;
		PDFTextStripper pdfStripper = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;

		file = new File(filePath);
		RandomAccessFile f = new RandomAccessFile(file,"r");
		parser = new PDFParser(f); // update for PDFBox V 2.0
		parser.parse();
		
		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		pdDoc = new PDDocument(cosDoc);
		pdDoc.getNumberOfPages();

		PDDocumentInformation info = pdDoc.getDocumentInformation();

		autor = info.getAuthor();
		date =  convDatum(info.getCreationDate());
		System.out.println(date);

		pdfStripper.setStartPage(1);
		pdfStripper.setEndPage(pdDoc.getNumberOfPages());

		text = pdfStripper.getText(pdDoc);
			
		releaseRessoruces(parser, pdfStripper, pdDoc, cosDoc, f);
	
		System.out.println("--------------- TEXT aus PDFLesen: -------------");
		System.out.println(text);
		System.out.println("--------------- TEXT -------------");
		
		return text;
	}
	
	public void releaseRessoruces(PDFParser p, PDFTextStripper pdfStripper, PDDocument pdDoc , COSDocument cosDoc, RandomAccessFile f) {
	
		try {
			cosDoc.close();
			pdDoc.close();
			f.close();
			
			p = null;
			pdfStripper = null;
			pdDoc = null;
			cosDoc = null;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private String convDatum(Calendar cal){

		SimpleDateFormat d = new SimpleDateFormat("dd.MM.yyyy");
		//		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
		String pD = d.format(cal.getTime());
		return pD;
	}
	
	public String getAutor(){ 
		return autor; 
	}

	public String getDatum(){ 
		return date; 
	}

//	public static void main(String[] args) { 
//	
//		PDFLesen pdfL = new PDFLesen();
//		try {
//			pdfL.pdfToText("C://Users//Sara//Dropbox//Diplomarbeit//KillerDoc.pdf");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
}