import java.io.FileNotFoundException;
import java.io.IOException;

public interface IStrategy {

	public String textAuslesen(String filename) throws IllegalArgumentException, FileNotFoundException, IOException;
}
