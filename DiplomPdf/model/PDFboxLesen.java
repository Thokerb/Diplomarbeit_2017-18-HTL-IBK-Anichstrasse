import java.io.*;

public class PDFboxLesen {

	public static String text = "text";

	public static void main(String[] args) throws IOException { 
	
		PDFmanager pdfM = new PDFmanager();
		pdfM.setFilePath("D:\\Diplomarbeit\\TestPDFs\\Allgemeines.pdf");
		text = pdfM.pdfToText();
		PDFmanager.getInfoPDF();

	}

	public static String lesenPDF(String filename){
		
		PDFmanager pdfM = new PDFmanager();
		
		pdfM.setFilePath(filename);
		try {
			
			text = pdfM.pdfToText();
			System.out.println("--------------- TEXT aus PDFboxLesen: -------------");
			System.out.println(text);
			System.out.println("--------------- TEXT -------------");
			System.out.println("PDF Info: ");
			PDFmanager.getInfoPDF();
			System.out.println("aus");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Achtung - Fehler! Die Datei konnte nicht gelesen werden (PDFboxLesen)";
		}		
		return text;	
	}
}