import java.io.*;

public class PDFboxLesen {

	public static String text = "text";

//	public static void main(String[] args) throws IOException { 
	
//		PDFmanager pdfM = new PDFmanager();
//		pdfM.setFilePath("C:\\Users\\Sara\\Desktop\\Einführung in das Projektmanagement.pdf");
//		text = pdfM.pdfToText();
//		PDFmanager.getInfoPDF();
//		System.out.println(pdfM.pdfToText());     
//	}

	public static String lesenPDF(String filename){
		
		PDFmanager pdfM = new PDFmanager();
	
		try {
			
			text = pdfM.pdfToText(filename);
			System.out.println("--------------- TEXT aus PDFboxLesen: -------------");
			System.out.println(text);
			System.out.println("--------------- TEXT -------------");
			System.out.println("PDF Info: ");
			PDFmanager.getInfoPDF();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Achtung - Fehler! Die Datei konnte nicht gelesen werden ";
		}		
		return text;	
	}
}