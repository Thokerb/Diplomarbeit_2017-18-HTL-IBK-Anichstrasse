import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * StrategyInterface mit Methodenkopf der in Unterklassen zu implementieren und füllen ist
 */
public interface IStrategy {

	public String textAuslesen(String filename) throws IllegalArgumentException, FileNotFoundException, IOException;

}
