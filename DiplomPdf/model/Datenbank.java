
import java.sql.*;
import java.util.*;

public class Datenbank {

	private static String dbUrl = "jdbc:postgresql://localhost:5432/diplomarbeit?user=postgres&password=DB201718";
	private static final int ANZ_EINTRAEGE = 9;	// Anzahl der Spalten in der Tabelle für die Daten


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readDaten();

	}

	public static ArrayList<String[]> readDaten() {
		ArrayList<String[]> tabelle = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String READ_DATA_SQL = "SELECT language, tag, blobdatei, stichworttext, inhalttext, uploader, autor, dateiname,uploadid FROM uploaddaten";
		try (Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(READ_DATA_SQL);
				ResultSet resultSet = pStatement.executeQuery()) {
			while (resultSet.next()) {
				String[] zeile = new String[ANZ_EINTRAEGE];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < ANZ_EINTRAEGE; i++) {
					zeile[i] = resultSet.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				tabelle.add(zeile);
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tabelle;
	}
	
	public static boolean writeKontakte(ArrayList<String[]> tabelle) {
		boolean erfolg = true;
		//Neue Daten in Datebantabelle schreiben.
		String INSERT_DATA_SQL = "INSERT INTO uploaddaten (language, tag, blobdatei, stichworttext, inhalttext, uploader, autor, dateiname,uploadid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(dbUrl);
			  PreparedStatement pStatement = connection.prepareStatement(INSERT_DATA_SQL);) {
			for (String[] zeile : tabelle) {
				for (int i = 1; i <= ANZ_EINTRAEGE; i++) {
					pStatement.setString(i, zeile[i-1]);
				}
				pStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			erfolg = false;
		}
		return erfolg;
	}	


}
