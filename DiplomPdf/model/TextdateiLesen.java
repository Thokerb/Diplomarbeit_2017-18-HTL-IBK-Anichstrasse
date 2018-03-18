import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.text.SimpleDateFormat;

import org.apache.commons.io.IOUtils;


public class TextdateiLesen implements IStrategy {

	String dateCreated;
	UserPrincipal aut;

	
	public String textAuslesen(String filename) throws IOException{
	
			Path path = Paths.get(filename);
			
			FileInputStream fisTargetFile = new FileInputStream(new File(filename));

			String text = IOUtils.toString(fisTargetFile, "UTF-8");
//			System.out.println(targetFileStr);
		
		    aut =  Files.getOwner(path);
				    
			FileTime date =  Files.getLastModifiedTime(path);
			SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	        dateCreated = df.format(date.toMillis()); 
	        
	        releaseRessoruces(fisTargetFile);
	        
			return text;
	}

	public void releaseRessoruces(FileInputStream fisTargetFile) {
		 try {
			 fisTargetFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String getDatum() {
		System.out.println(dateCreated);
		return dateCreated;
	}
	
	public String getAutor() {
		String name = aut.getName();
		System.out.println(name);
		return name;
	}
	

//	public static void main(String[] args) {
//		TextdateiLesen l1 = new TextdateiLesen();
//		try {
//			l1.textAuslesen("C://Users//Sara//Dropbox//Diplomarbeit//Text.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		l1.getDatum();
//		l1.getAutor();
//	}
	
}
