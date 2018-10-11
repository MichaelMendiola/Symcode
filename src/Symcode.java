/*
 * Symcode project for Symantec challenge
 * Given a string of ASCII characters,
 * return the equivalent symcode with padding.
 * @author Michael Mendiola
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Symcode{
	// Symcode is radix-8 with 3 bit characters
	final static int RADIX = 8;
	final static int BITS = 3;
	private static final Map<Integer, String> indexMap = new HashMap<Integer, String>() {{
	    put(0, "s");
	    put(1, "y");
	    put(2, "m");
	    put(3, "a");
	    put(4, "n");
	    put(5, "t");
	    put(6, "e");
	    put(7, "c");
	}};
	
	public static void main(String[] args) throws IOException{
		File file = new File("/Users/michaelmendiola/Documents/GitHub/Symcode/symcode_small_test.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line; 
		ArrayList<String> test = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.isEmpty()){
				System.out.println();
			}else {
				test = textToHex(line);
				String binary = hexToBits(test);
				Object[] arr1 = bitsToIndex(binary);
				ArrayList<Integer> shit = (ArrayList<Integer>) arr1[0];
				int padding = (int) arr1[1];
				String fuck = indexToCode(shit, padding);
			}		
		  } 
		
		//System.out.println(test.toString());
		

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
		//System.out.println("Hex: " + hex.toString());
		return hex;
	}

	/* Convert hex to 8 bit binary using Integer methods
	 * 
	 * @param hex
	 * 		Text from input file
	 * @returns bin
	 * 		String from bin StringBuilder object
	 * 
	 */
	private static String hexToBits(ArrayList<String> hex) {
		StringBuilder bin = new StringBuilder();
		for (String val : hex){
			String s = Integer.toBinaryString(Integer.parseInt(val, 16));
			int len = s.length();
			bin.append(len == RADIX ? s : "00000000".substring(len) + s);
		}
		//System.out.println("Binary: " + bin.toString());
		return bin.toString();
	}
	
	private static Object[] bitsToIndex(String bin){
		Object[] indices = new Object[2];
		ArrayList<Integer> codeVals = new ArrayList<>();
		int len = bin.length();
		int padding = 0;
		// To be use for substrings of bin
		int start = 0; int end = 3;
		while (!(len % BITS == 0 && len % RADIX == 0)){
			bin += "0";
			len = bin.length();
			padding++;
		}
		//System.out.println("Padded: " + bin + " # " + padding);
		// Padding set to # of 0's added then reduced to 0's represented by '$'
		padding -= padding % BITS;
		len -= padding;
		while (end != len + BITS){
			String sub = bin.substring(start, end);
			codeVals.add(Integer.parseInt(sub, 2));
			start += BITS;
			end += BITS;
		}
		indices[0] = codeVals;
		indices[1] = padding;
		//System.out.println("Indices: " + indices[0].toString());
		return indices;
	}

	private static String indexToCode(ArrayList<Integer> indices, int padding){
		int numSymbols = padding / BITS;
		StringBuilder code = new StringBuilder();
		for (int i : indices){
			code.append(indexMap.get(i));
		}
		for (int i = 0; i < numSymbols; i++){
			code.append("$");
		}
		System.out.println(code.toString());
		return code.toString();
	}

}
