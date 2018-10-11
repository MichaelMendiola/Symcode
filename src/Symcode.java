/*
 * Symcode project for Symantec challenge
 * Given a string of ASCII characters,
 * return the equivalent symcode with padding.
 * @author Michael Mendiola
 */
import java.math.BigInteger;
import java.util.*;

public class Symcode{
	// hex values to be formatted as 8 bits
	final static int bits = 8;
	private static final Map<Integer, String> index = new HashMap<Integer, String>() {{
	    put(0, "s");
	    put(1, "y");
	    put(2, "m");
	    put(3, "a");
	    put(4, "n");
	    put(5, "t");
	    put(6, "e");
	    put(7, "c");
	}};
	
	public static void main(String[] args){
		ArrayList<String> test = textToHex("gimble");
		System.out.println(test.toString());
		String binary = hexToBits(test);
		System.out.println(binary);
	}
	
	/* Convert ASCII text to hex values
	 * 
	 * @param text
	 * 		Text from input file
	 * 
	 * @returns hex
	 * 		ArrayList of hex values for each char of @text
	 */
	private static ArrayList<String> textToHex(String text){
		ArrayList<String> hex = new ArrayList<>();
		char[] chars = text.toCharArray();
		for (char ch : chars) {
			hex.add(Integer.toHexString((int) ch));
		}
		return hex;
	}

	/* Convert hex to 8 bit binary
	 * 
	 * @param hex
	 * 		Text from input file
	 * 
	 */
	private static String hexToBits(ArrayList<String> hex) {
		StringBuilder bin = new StringBuilder();
		for (String val : hex){
			String s = Integer.toBinaryString(Integer.parseInt(val, 16));
			int len = s.length();
			bin.append(len == bits ? s : "00000000".substring(len) + s);
		}
		
		return bin.toString();
	}
	private static int bitsToIndex(String bits){ // Could pass binary as string or int tbd
		return Integer.parseInt(bits, 2);
	}

	private static String indexToCode(int index){

		return null;
	}

}
