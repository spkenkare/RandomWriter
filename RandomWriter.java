package assignment;

import java.io.*;
import java.util.*;

/*
 * CS 314H Assignment 2 - Random Writing
 *
 * Your task is to implement this RandomWriter class
 */
public class RandomWriter implements TextProcessor {

	private static String source;
	private static String result;
	private static int k;
	private static String text;
	private static String seed;
	private static File file;

	public static void main(String[] args) {

		try {
			source = args[0];
			result = args[1];
			k = Integer.parseInt(args[2]);
			int length = Integer.parseInt(args[3]);

			if (k < 0) {
				System.err.println("k is invalid. Please enter a nonnegative value.");
				System.exit(0);
			}
			if (length < 0) {
				System.err.println("Length is invalid. Please enter a nonnegative value.");
				System.exit(0);
			}

			TextProcessor proc = createProcessor(k);
			file = new File(result);
			proc.readText(source);
			proc.writeText(result, length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Unless you need extra logic here, you might not have to touch this method
	public static TextProcessor createProcessor(int level) {
		return new RandomWriter(level);
	}

	private RandomWriter(int level) {
		// Do whatever you want here
		level = k;
	}

	public void readText(String inputFilename) throws IOException {
		try {
			FileReader fr = new FileReader(inputFilename);
			BufferedReader br = new BufferedReader(fr);
			String line = new String();

			text = "";
			while ((line = br.readLine()) != null) {
				// a space is added after every line because I did not want to
				// deal with the new line character
				text += line;
				text += " ";
			}

			fr.close();
			if (text.length() <= k) {
				System.err.println(
						"The length of the input file in characters must be greater than k. Please choose a different input or a smaller k.");
				System.exit(0);
			}

		} catch (FileNotFoundException e) {
			System.err.println("The file could not be found. Please provide a valid input file name.");
			System.exit(0);
		}

	}

	public void writeText(String outputFilename, int length) throws IOException {

		FileWriter writer = new FileWriter(file, false);
		Random rand = new Random();

		if (k == 0) {

			for (int i = 0; i < length; i++) {

				int index = rand.nextInt(text.length() - 1);
				seed = Character.toString(text.charAt(index));
				writer.write(seed);

			}
		}

		else {
			int index = rand.nextInt(text.length() - k);
			seed = text.substring(index, index + k);
			int num = 0;
			while (num < length) {

				ArrayList<Character> list = new ArrayList<Character>();
				for (int i = 0; i < text.length() - k - 1; i++) {
					if (text.substring(i, i + k).equals(seed)) {
						list.add(text.charAt(i + k));
					}
				}

				if (list.size() == 0) {
					index = rand.nextInt(text.length() - k);
					seed = text.substring(index, index + k);
					writer.write(" ");
					writer.write(seed);
				}

				else {
					int j = rand.nextInt(list.size());
					seed += list.get(j);
					seed = seed.substring(1);
					writer.write(seed.charAt(k - 1));
				}
				num++;

			}

		}
		writer.close();
	}
}
