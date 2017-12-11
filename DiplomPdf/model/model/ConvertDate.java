package model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;

public class ConvertDate {

	public static void main(String[] args) {
		convertDate("06-24-2000");
	}

	public static void convertDate(String s){
		// Print out the inserted string
		System.out.println(s);

		// Split the String into year, month and date
		// and save it into an array
		String arrayString[] = s.split("-");
		for(int i = 0; i < arrayString.length; i++){
			System.out.println(arrayString[i]);
		}

		//Only for out-printing because it is only the format 
		// in which it is shown
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		System.out.println(sdf);

		// Creating the Integer values of the date
		// and setting them to the values saved in
		// the array
		int year = Integer.parseInt(arrayString[2]);
		int month = Integer.parseInt(arrayString[0]);
		int day = Integer.parseInt(arrayString[1]);

		// Output to check the values are set correctly
		System.out.println(year);
		System.out.println(month);
		System.out.println(day);

		// Creating a calendar object
		// and setting the year, day and month
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); // <-- months start at 0.
		cal.set(Calendar.DAY_OF_MONTH, day);

		// Converting 
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());

		// Only output to check if everything worked correctly
		System.out.println(sdf.format(date));
	}

}

