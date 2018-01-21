import java.security.SecureRandom;

public class HashTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TokenGenerator tg = new TokenGenerator();
		System.out.println(tg.generateCode(20));

	}

}
