package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Test {
	
	//zum Testen
		private static String[] testzeile = new String[]{"Nachhilfe","fhjdskfhdsuifhusdifhdshfisfhjisd fjdisfjhsidf sfjidsofhisd fjdisofhjisdo hdisofhsid","Verena","Verena","Wow a kastanie", "2017-11-25"};
		private static String easySuchwort2;
		//private static date;

		//Variablen relevant für Methoden
		
		//zum Testen
		private static String easyText;
		private static String text="Das ist ein wunderschönes Haus, ich liebe dein Haus";
		private static String suchwort="Helm";
		private static String easySuchwort;
		private static boolean wert=false;
		private static int anzahl;
		private static String wort="haus";
		private static float relevanz;
		
		public static ArrayList<String[]> sortiertAutorASC = new ArrayList<String[]>();


	public static void main(String[] args) {
		
		/*
		String such=FunktionenDB.getSuchwort();
		FunktionenDB.VereinfachtesSuchwortgenerator(such);
		String suchwort=FunktionenDB.getEasySuchwort();
		Suchwort(suchwort);
		System.out.println(easySuchwort2);
	 */
	//writeStichwörter(easySuchwort2);

	//date=convertDate("2017-11-25");
	//writeDaten(testzeile);
		
			Connection conn=null;
			List<Uploaddaten> list = null;
		
		try {
			DBManager db = new DBManager();
			conn=db.getConnection();
			
			//list = db.readDaten(conn);
			
			ArrayList<String[]> daten = new ArrayList<String[]>();
			daten=db.autorASC(conn);
			for(int i=0;i<daten.size();i++)
			{
				System.out.println(daten.get(i)[1]);
			}
			
			
			db.releaseConnection(conn);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		}

	}

}
