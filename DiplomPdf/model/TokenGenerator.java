import java.security.SecureRandom;
import java.util.Locale;

public class TokenGenerator {
	
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";

    public static final String alphanum = upper + lower + digits;

    public String generateCode(int length){
    	String code = "";
    	SecureRandom ran = new SecureRandom();
    	for(int i = 0;i < length;i++){
    		int z = ran.nextInt(62);
    		code += alphanum.substring(z, z+1);
    	}
    	return code;
    }

}
