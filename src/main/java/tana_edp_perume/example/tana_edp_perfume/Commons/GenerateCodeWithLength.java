package tana_edp_perume.example.tana_edp_perfume.Commons;
import java.util.*;
import java.nio.charset.*;
public class GenerateCodeWithLength {

        public String GenerateCode(int length) {
            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < length) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String generatedString = salt.toString();
            return generatedString;
        }

}
