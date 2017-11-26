import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;



public class PDFmanager {

	private PDFParser parser;
	private PDFTextStripper pdfStripper;
	private PDDocument pdDoc ;
	private COSDocument cosDoc ;

	private String Text ;
	private static String autor ;
	private static String datum ;
	private String filePath;
	private File file;
	String info;

	public PDFmanager() {}


	private String getDatum(Calendar cal){

		SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
		String pD = d.format(cal.getTime());
		return pD;
	}

	public String pdfToText() throws IOException {

		this.pdfStripper = null;
		this.pdDoc = null;
		this.cosDoc = null;

		file = new File(filePath);
		parser = new PDFParser(new RandomAccessFile(file,"r")); // update for PDFBox V 2.0
		parser.parse();
		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		pdDoc = new PDDocument(cosDoc);
		pdDoc.getNumberOfPages();

		PDDocumentInformation info = pdDoc.getDocumentInformation();
		
		autor = info.getAuthor();
		datum =  getDatum(info.getCreationDate());
		
//		System.out.println( "Autor: " + info.getAuthor() );
//		System.out.println( "Autor: " + info.getCreator()); gibt zB nur Microsoft zurück
//		   System.out.println("Erstelldatum: " + getDatum(info.getCreationDate()));

		pdfStripper.setStartPage(1);
		pdfStripper.setEndPage(pdDoc.getNumberOfPages());

		Text = pdfStripper.getText(pdDoc);
		pdDoc.close();
		
		return Text;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public static void getInfoPDF(){
		System.out.println("	Autor: "+ autor);
		System.out.println("	Erstelldatum: "+ datum);
		
	}
	
	public static String getAutor(){ 
		return autor; 
	}
	
	public static String getDatum(){ 
		return datum; 
	}
}

