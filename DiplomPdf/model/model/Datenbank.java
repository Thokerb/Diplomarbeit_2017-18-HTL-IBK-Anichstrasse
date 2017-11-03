package model;

import java.sql.*;
import java.util.*;

public class Datenbank {

	private static String dbUrl = "jdbc:postgresql://localhost:5432/diplomarbeit?user=postgres&password=DB201718";


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readDaten();
		readAutoren();
		readDateinamen();
		readTags();

	}

	public static ArrayList<String[]> readDaten() {
		ArrayList<String[]> daten = new ArrayList<String[]>();
		//Tabellenzeilen aus Datenbank einlesen
		String READ_DATA_SQL_DATEN = "SELECT language, tag, blobdatei, stichworttext, inhalttext, uploader, autor, dateiname,uploadid FROM uploaddaten";
		try (Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(READ_DATA_SQL_DATEN);
				ResultSet resultSet = pStatement.executeQuery()) {
			while (resultSet.next()) {
				String[] zeile = new String[9];
				System.out.print("Gelesen wurde: ");
				for (int i = 0; i < 9; i++) {
					zeile[i] = resultSet.getString(i+1);
					System.out.print(" '" + zeile[i] + "'");	//zur Kontrolle
				}
				daten.add(zeile);
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return daten;
	}

	public static ArrayList<String[]> readAutoren() {
		
		ArrayList<String[]> Autoren = new ArrayList<String[]>();

		String READ_DATA_SQL_AUTOREN = "select autor from uploaddaten group by autor";
		try (Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(READ_DATA_SQL_AUTOREN);
				ResultSet resultSet = pStatement.executeQuery()) {

			while (resultSet.next()) {
				String[] spalten = new String[1];
				System.out.print("Gelesen wurde: ");
				spalten[0] = resultSet.getString(1);

				Autoren.add(spalten);
				System.out.println(" '" + spalten[0] + "'");	//zur Kontrolle
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Autoren;

	}

	public static ArrayList<String[]> readDateinamen() {
		
		ArrayList<String[]> dateinamen = new ArrayList<String[]>();

		String READ_DATA_SQL_DATEINAMEN = "select dateiname from uploaddaten group by dateiname";
		try (Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(READ_DATA_SQL_DATEINAMEN);
				ResultSet resultSet = pStatement.executeQuery()) {

			while (resultSet.next()) {
				String[] spalten = new String[1];
				System.out.print("Gelesen wurde: ");
				spalten[0] = resultSet.getString(1);

				dateinamen.add(spalten);
				System.out.println(" '" + spalten[0] + "'");	//zur Kontrolle
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dateinamen;

	}

	public static ArrayList<String[]> readTags() {
		
		ArrayList<String[]> Tags = new ArrayList<String[]>();

		String READ_DATA_SQL_AUTOREN = "select tag from uploaddaten group by tag";
		try (Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(READ_DATA_SQL_AUTOREN);
				ResultSet resultSet = pStatement.executeQuery()) {

			while (resultSet.next()) {
				String[] spalten = new String[1];
				System.out.print("Gelesen wurde: ");
				spalten[0] = resultSet.getString(1);

				Tags.add(spalten);
				System.out.println(" '" + spalten[0] + "'");	//zur Kontrolle
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return Tags;

	}

	public static boolean writeDaten(ArrayList<String[]> eintraege) {
		
		boolean erfolg = true;
		//Neue Daten in Datebantabelle schreiben.
		String INSERT_DATA_SQL = "INSERT INTO uploaddaten (language, tag, blobdatei, stichworttext, inhalttext, uploader, autor, dateiname,uploadid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DriverManager.getConnection(dbUrl);
				PreparedStatement pStatement = connection.prepareStatement(INSERT_DATA_SQL);) {
			for (String[] zeile : eintraege) {
				for (int i = 1; i <= 9; i++) {
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
