import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.UserPrincipal; 

public class TextdateiLesen {

	public String textdateiLesen(String filename){

		try {
			String text = null; 
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
		
//			BasicFileAttributes attr = Files.readAttributes((Path)fileReader, BasicFileAttributes.class);
//
//			System.out.println("creationTime: " + attr.creationTime());
			UserPrincipal owner = Files.getOwner((Path)fileReader);
			System.out.println("Owner: " + owner);

			StringBuffer strText = new StringBuffer();
			
			while((text = bufferedReader.readLine()) != null) {
				strText.append(text + "\n");
			} 
			text = strText.toString();
			bufferedReader.close();
			System.out.println("------------TEXT - TXT Datei:-------------");
			System.out.print(text);
			System.out.println("------------TEXT-------------");
			return text; 
		}
		catch(FileNotFoundException ex) {
			System.out.println("Datei  '" + filename + "' kann nicht geöffnet werden ");                
		}
		catch(IOException ex) {
			System.out.println("Datei '" + filename + "' konnte nicht gelesen werden");                  
			ex.printStackTrace();
		}
		return "Achtung - Fehler! Die Datei konnte nicht gelesen werden ";
	}

	public static void main(String[] args) {
		TextdateiLesen l1 = new TextdateiLesen();
		l1.textdateiLesen("C://Users//Sara//Dropbox//Diplomarbeit//Text.txt");
	}
}
