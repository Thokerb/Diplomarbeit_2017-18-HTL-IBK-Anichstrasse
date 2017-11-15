import java.io.IOException;

public class PDFboxLesen {

	public static String text = "text";

//	public static void main(String[] args) throws IOException {  TODO vllt für Testweck oder Fehlersuche
//
//		PDFmanager pdfM = new PDFmanager();
//		pdfM.setFilePath("C:\\Users\\Sara\\Dropbox\\Diplomarbeit\\PDf.pdf");
//
//		text = pdfM.pdfToText();
//		System.out.println(pdfM.pdfToText());     
//	}
	
	public static String lesenPDF(String filename){
		
		PDFmanager pdfM = new PDFmanager();
		pdfM.setFilePath(filename);
		try {
			text = pdfM.pdfToText();
			System.out.println(pdfM.pdfToText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Achtung - Fehler! Die Datei konnte nicht gelesen werden ";
		}		
		return text;
		
	}
}