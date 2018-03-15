import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordHash{
	
	 static String passwordToHash = "sara";
	 String generatedPassword = null;
	 
     SecureRandom random = new SecureRandom();
     byte bytes[] = new byte[20];
     
//     random.nextBytes(bytes);
//     random.
	
    public static void main(String[] args){
    	
    	PasswordHash pwh = new PasswordHash();
    	pwh.passwordToHash(passwordToHash);
    }
    
   
    
    public String passwordToHash(String pw){
    	
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(pw.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
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