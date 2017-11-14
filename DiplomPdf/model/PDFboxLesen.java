import java.io.IOException;

public class PDFboxLesen {

	public static String text = "text";

	public static void main(String[] args) throws IOException {

		PDFmanager pdfM = new PDFmanager();
		pdfM.setFilePath("C:\\Users\\Sara\\Dropbox\\Diplomarbeit\\PDf.pdf");

		text = pdfM.pdfToText();
		System.out.println(pdfM.pdfToText());     
	}
}