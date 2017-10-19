import java.io.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import com.sun.javafx.util.Utils; 

public class TextdateiLesen {

	public static void main(String[] args) {
		// Hier Datei angeben, wessen Text gelesen werden sollte 
		String fileName = "C://Users//Sara//Dropbox//Diplomarbeit//Text.txt"; 
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}   
			bufferedReader.close();         
		}
		catch(FileNotFoundException ex) {
			System.out.println("Datei  '" + fileName + "' kann nicht geöffnet werden ");                
		}
		catch(IOException ex) {
			System.out.println("Datei '" + fileName + "' konnte nicht gelesen werden");                  
			// ex.printStackTrace();
		}

	}
}