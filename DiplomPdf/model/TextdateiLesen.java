import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.text.SimpleDateFormat; 

public class TextdateiLesen {

	String dateCreated;
	String contents = null;
	UserPrincipal aut;
	String name;
	
	public String textdateiLesen(String filename){

	/*	try {
			String text = null; 
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
		
//			BasicFileAttributes attr = Files.readAttributes((Path)fileReader, BasicFileAttributes.class);
//			System.out.println("creationTime: " + attr.creationTime());
//			UserPrincipal owner = Files.getOwner((Path)fileReader);
//			System.out.println("Owner: " + owner);

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
	
	*/
		
		
		try {
	
			Path path = Paths.get(filename);
			contents = new String(Files.readAllBytes(path));
			System.out.println(contents);
			
		    aut =  Files.getOwner(path);
				    
			FileTime date =  Files.getLastModifiedTime(path);
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	        dateCreated = df.format(date.toMillis()); 
			
			return contents;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contents;
	}

	
	public String getDatum() {
		System.out.println(dateCreated);
		return dateCreated;
	}
	
	public String getAutor() {
		name = aut.getName();
		System.out.println(name);
		return name;
	}
	
//	public static void main(String[] args) {
//		TextdateiLesen l1 = new TextdateiLesen();
//		l1.textdateiLesen("C://Users//Sara//Documents//INFI-ERP//4.Klasse//INFI-ERP Mitschrift//Artikel.txt");
//		l1.getDatum();
//		l1.getAutor();
//	}
}
