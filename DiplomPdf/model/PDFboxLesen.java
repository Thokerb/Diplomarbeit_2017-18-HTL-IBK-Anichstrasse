import java.io.IOException;
import java.util.Scanner;

public class PDFboxLesen {

	public static String text = "text";

	public static void main(String[] args) throws IOException {

		PDFmanager pdfM = new PDFmanager();
		pdfM.setFilePath("C:\\Users\\Sara\\Documents\\UFW-V\\ZaunerHindelang.pdf");

		System.out.print("Bitte hier Wort, nachdem gesucht werden sollte eingeben:  ");
		Scanner scanner = new Scanner(System.in);
		String gesucht = scanner.nextLine();

		System.out.println("Wort nachdem gesucht wird: " + gesucht);
		text = pdfM.ToText();
		System.out.println(pdfM.ToText());     
		
		System.out.println("War das Wort vorhanden? " + text.contains(gesucht));
		System.out.println("War das Wort vorhanden? " + text.equals(gesucht));
		
	}    
}
