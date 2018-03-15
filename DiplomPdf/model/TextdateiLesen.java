import java.io.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym; 

public class TextdateiLesen {

	public static String textdateiLesen(String filename){
		
		//	File f = new File("C://Users//Sara//Dropbox//Diplomarbeit//Text.txt");
		
		try {
			String text = null; 
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer strText = new StringBuffer();
			
			while((text = bufferedReader.readLine()) != null) {
				strText.append(text + "\n");
//				System.out.println(text);
			} 
			text = strText.toString();
			bufferedReader.close();
			System.out.println("------------TEXT-------------");
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