import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Erstellt aus angegebenen String einen MD5 Hash um die Sicherheit zu erhöhen
 *
 */
public class PasswordHash{
	
	 static String passwordToHash = "sara";
	 String generatedPassword = null;
	 
     SecureRandom random = new SecureRandom();
     byte bytes[] = new byte[20];
     
    public static void main(String[] args){
    	
    	PasswordHash pwh = new PasswordHash();
    	pwh.passwordToHash(passwordToHash);
    }
    
    
    public String passwordToHash(String pw){
    	
        try {
       
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pw.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
        return generatedPassword;
    }
}