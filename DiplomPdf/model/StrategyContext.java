import java.io.FileNotFoundException;
import java.io.IOException;

public class StrategyContext {
	
	private IStrategy strategy;
	
	public void setStrategy (IStrategy strategy)
	{
		this.strategy = strategy;
	}
	
	public String executeStrategy(String filename)
	{
		String text = null; 
		try {
			text = this.strategy.textAuslesen(filename);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}


}
