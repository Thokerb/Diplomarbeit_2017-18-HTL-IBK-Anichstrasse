import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFLesen {
	
	private PDFParser parser;
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc ;
	private COSDocument cosDoc ;
//	private String Text ;
	private String autor ;
	private String date ;
	private File file;

	String info;

	public String text = "text";
	
//	public void setFilePath(String filePath) {
//		this.filePath = filePath;
//	}
	
	public String pdfToText(String filePath) throws IOException {

		this.pdfStripper = null;
		this.pdDoc = null;
		this.cosDoc = null;

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
		

		cosDoc.close();
		pdDoc.close();
		f.close();
		
		
		this.pdfStripper = null;
		this.pdDoc = null;
		this.cosDoc = null;
		
		
		
		System.out.println("--------------- TEXT aus PDFLesen: -------------");
		System.out.println(text);
		System.out.println("--------------- TEXT -------------");
		
		
		return text;
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


	

//	public static void main(String[] args) throws IOException { 
//	
//		PDFLesen pdfL = new PDFLesen();
//		pdfL.pdfToText("C:\\Users\\Sara\\Desktop\\AbschlussberichtGr3.pdf");
//
//	}


}