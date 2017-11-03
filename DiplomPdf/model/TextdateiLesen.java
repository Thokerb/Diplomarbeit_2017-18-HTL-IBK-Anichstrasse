import java.io.*; 

public class TextdateiLesen {

	public String textdateiLesen(String filename){


		// Hier Datei angeben, wessen Text gelesen werden sollte 
		String line = null;

		//	File f = new File("C://Users//Sara//Dropbox//Diplomarbeit//Text.txt");

		File f = new File(filename);
		String name = f.getName();
		System.out.println("Es handelt sich um eine ' "+name.substring(name.lastIndexOf('.')+1,name.length())+" ' Datei: ");
		System.out.println("-----------------------------------------");

		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}   
			bufferedReader.close();         
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