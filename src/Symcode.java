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
import java.io.FileWriter;
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
		// Scan for input and output paths
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter input file path:");
		String inputPath = sc.nextLine();
		System.out.println("Enter output file path:");
		String outputPath = sc.nextLine();

		File input = new File(inputPath);
		BufferedReader br = new BufferedReader(new FileReader(input));

		File output = new File(outputPath);
		FileWriter fw = new FileWriter(output);

		String line;
		ArrayList<String> hex = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			line = line.trim();

			if (line.isEmpty()) {
				fw.write(System.lineSeparator());
			} else {
				hex = textToHex(line);
				String binary = hexToBits(hex);

				Object[] indices = bitsToIndex(binary);
				ArrayList<Integer> keys = (ArrayList<Integer>) indices[0];
				int padding = (int) indices[1];

				String code = indexToCode(keys, padding);
				fw.write(code);

			}
		}
		fw.close();
		System.out.println("Successfully encoded to: " + outputPath);


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
			// Add 0's so bin is 8 bits
			bin.append(len == RADIX ? s : "00000000".substring(len) + s);
		}
		return bin.toString();
	}

	/* Convert 3 bit binary to integer, add padding
	*
	* @param bin
	* 		Binary String
	* @returns indices
	* 		Object array where indices[0] is an ArrayList of chars,
	*     indices[1] is an int representing the number of padded 0's added
	*/
	private static Object[] bitsToIndex(String bin){
		Object[] indices = new Object[2];
		ArrayList<Integer> codeVals = new ArrayList<>();
		int len = bin.length();
		int padding = 0;

		// To be use for substrings of bin
		int start = 0; int end = 3;

		// add 0's for padding
		while (!(len % BITS == 0 && len % RADIX == 0)){
			bin += "0";
			len = bin.length();
			padding++;
		}

		// Padding set to # of 0's added, then reduced to 0's represented by '$'
		padding -= padding % BITS;
		len -= padding;

		// Convert 3 bit substrings to ints, store in codeVals
		while (end != len + BITS){
			String sub = bin.substring(start, end);
			codeVals.add(Integer.parseInt(sub, 2));
			start += BITS;
			end += BITS;
		}

		indices[0] = codeVals;
		indices[1] = padding;

		return indices;
	}

	/* Convert key to corresponding symcode and add $'s
	*
	* @param indices
	* 		ArrayList of int keys to be checked against @indexMap
	* @param padding
	*     Number of padded 0's
	* @returns code
	* 		line of symcode
	*
	*/
	private static String indexToCode(ArrayList<Integer> indices, int padding){
		int numSymbols = padding / BITS;
		StringBuilder code = new StringBuilder();
		for (int i : indices){
			code.append(indexMap.get(i));
		}
		for (int i = 0; i < numSymbols; i++){
			code.append("$");
		}

		return code.toString();
	}

}
