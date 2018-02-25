package model;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
		
		
		private static Date date ;
		private static String d;
		
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
	
	System.out.println("Test test test");
			Connection conn=null;
			List<Daten> list = null;
		
		try {
			DBManager db = new DBManager();
			conn=db.getConnection();

			
			db.readgeloeschteDatei(conn,98 , "uploaddaten");
			
			
			//list = db.readDaten(conn);
			
			//db.VereinfachtesSuchwortgenerator(conn, "Hallo");
			
//			ArrayList<String[]> daten = new ArrayList<String[]>();
//			daten=db.ranking2(conn, "Zwiebel");
//			for(int i=0;i<daten.size();i++)
//			{
//				System.out.println(daten.get(i)[1]);
//			}
//			String anzahl=daten.get(0)[0];
//			System.out.println("yooooo: "+anzahl);
//			System.out.println("Anzahl an Einträge zu Suchwort: "+daten.get(0)[0]);
			
			db.releaseConnection(conn);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch(SQLException e){
			e.printStackTrace();
		}
		

		
//		GregorianCalendar now = new GregorianCalendar(); 
//		
//		  SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
//		  String pD = d.format(now.getTime());
//		  System.out.println(pD);
//		
//		
//		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
//		System.out.println("Datum vom Typ Calender");
//		System.out.println(df.format(now.getTime())); 
//		String datumcool=df.format(now.getTime());
//		System.out.println("Datum vom Typ String");
//		System.out.println(datumcool);
		
	//	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

//	     d = formatter.format(now);
//	     System.out.println(d);
//		
//		String[] datum = new String[1];
//		datum[0]=datumcool;
//		System.out.println("Datum vom Typ Array");
//		System.out.println(datum[0]);
//		String[] datumsTeile = new String[3];
//		datumsTeile = datum[0].split(".");
//		Date datumbesser=new Date(Integer.parseInt(datumsTeile[2]), Integer.parseInt(datumsTeile[1]), Integer.parseInt(datumsTeile[0]));
//		
//		System.out.println(datumbesser);
		
		
		
//		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//		 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//	     d = formatter.format(now);
//	     System.out.println(d);
	     
//		d = format1.format(now);
//		System.out.println(d);

//		 GregorianCalendar now = new GregorianCalendar(); 
//		 DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
//		 System.out.println(df.format(now.getTime())); 
//		 
//		// dd-mm-yyyy
//		// yyyy-mm-dd
//		Date 
//		String[] datumsTeile = testzeile2[i-1].split(".");
//		Date datum=PDFmanager.getDatum(); 
//		Date datum=	new Date(Integer.parseInt(datumsTeile[2]), Integer.parseInt(datumsTeile[1]), Integer.parseInt(datumsTeile[0]));
//		System.out.println(datum);
		
		
		//Date date =  calendar.getTime();

	}
	
	static void printGregorianCalendarDate() { 
        GregorianCalendar now = new GregorianCalendar(); 
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);   // 14.04.12 
        df = DateFormat.getDateInstance(DateFormat.MEDIUM);             // 14.04.2012 
       // df = DateFormat.getDateInstance(DateFormat.LONG);               // 14. April 2012 
        System.out.println(df.format(now.getTime())); 
    } 
	
//	static void printSimpleDateFormat() { 
//        GregorianCalendar now = new GregorianCalendar(); 
//        SimpleDateFormat formatter = new SimpleDateFormat( 
//                "yyyy-MM-dd"); 
//        Date currentTime = new Date(); 
//        System.out.println(formatter.format(currentTime));        // 2012.04.14 - 21:34:07  
//    } 

}
