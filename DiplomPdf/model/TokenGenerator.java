import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerator {
	
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String lower = upper.toLowerCase(Locale.ROOT);
    public static final String digits = "0123456789";
    public static final String alphanum = upper + lower + digits;

//    public String generateCode(int length){
//    	String code = "";
//    	SecureRandom ran = new SecureRandom();
//    	System.out.println(ran);
//    	
//    	for(int i = 0;i < length;i++){
//    		int z = ran.nextInt(62);
//    		code += alphanum.substring(z, z+1);
//    		System.out.println(code) ;
//    	}
//    	return code;
//    }
    
    
    public String generateMD5Hash(){

    	MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	StringBuffer hexString = new StringBuffer();
    	
    	byte[] data = md.digest(RandomStringUtils.randomAlphabetic(10).getBytes());
    	for (int i=0; i<data.length; i++){
    		hexString.append(Integer.toHexString( (data[i]>> 4) & 0x0F ) );
    		hexString.append(Integer.toHexString( data[i] & 0x0F ) );
    	}
    	return hexString.toString();
    }
  
    
//    public String generateCode(){
//    	return generateCode(20);
//    }
    
//    public static void main(String[] args) {
//    	
//    	TokenGenerator tk = new TokenGenerator();
//    	tk.generateCode(); 
//    	System.out.println("MD5: ");
//    	tk.generateMD5Hash();
//    	
//    }

}
