import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserPrincipal;
import java.text.SimpleDateFormat;
/**
 * liest Text aus .txt File aus, da diese keine Metadaten besitzen wird als Autor der Nutzer von PC extrahiert und als Erstelldatum das aktuelle gesetzt
 *
 */

public class TextdateiLesen implements IStrategy {

	String dateCreated;
	UserPrincipal aut;
	StringBuilder sb;


	public String textAuslesen(String filename) throws IOException{

		Path path = Paths.get(filename);
		File fileDir = new File(filename);
		
		BufferedReader in;
		InputStreamReader isr;
		FileInputStream fis;
		
		fis = new FileInputStream(fileDir);		
		isr = new InputStreamReader(fis, "UTF8");
		in = new BufferedReader(isr);
		
//		in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));

		aut =  Files.getOwner(path);
	
		FileTime date =  Files.getLastModifiedTime(path);
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		dateCreated = df.format(date.toMillis()); 

		String text;

		while ((text = in.readLine()) != null) {
			sb = new StringBuilder();
			sb.append(text);
			System.out.println(sb);
		}

		releaseRessoruces(fis, isr, in);
		
		return sb.toString();
	}


	private void releaseRessoruces(FileInputStream fis, InputStreamReader isr, BufferedReader in) {
		try {
			fis.close();
			isr.close();
			in.close();
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


//		public static void main(String[] args) {
//			TextdateiLesen l1 = new TextdateiLesen();
//			try {
//				l1.textAuslesen("C://Users//Sara//Dropbox//Diplomarbeit//Text.txt");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			l1.getDatum();
//			l1.getAutor();
//		}

}
