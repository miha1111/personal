package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileOperations {

	public static final String DELIMITER = ";";
	public static final String NEW_LINE_SEPARATOR = "\n";

	public int numOfLines(String fileName) { // returns numbers of lines in file "fileName
		int num = 0;
		BufferedReader in = null;

		try {
			in = new BufferedReader(new FileReader(fileName));
			while ((in.readLine()) != null) {
				num++;
			}
		} catch (IOException ex) {
			System.out.println("Problem reading file.\n" + ex.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ignore) {
			}
		}

		System.out.println("There are " + num + " lines in " + fileName);
		return num;
	}

	// this method write all information from list's of list named "all" into file
	// "fileName"
	public void generateFile(String fileName, List<List<String>> all) {
		System.out.println("Started writing data into " + fileName + ". ");
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
			// Write the CSV file header
			// writer.append(FILE_HEADER.toString());

			// Add a new line separator after the header
			// writer.append(NEW_LINE_SEPARATOR);

			// Write a list of all familly members to the CSV file
			for (List<String> fm : all) {
				for (String fm_att : fm) {
					writer.append(fm_att);
					writer.append(DELIMITER);
				}
				writer.append(NEW_LINE_SEPARATOR);
			}
			System.out.println("Content successfully writen to file " + fileName + "! ");

		} catch (IOException ex) {
			System.out.println("Error opening/writing to file " + fileName + "!");
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				System.out.println("Error closing file!");
			}
		}

	}

	public void generateFileFromString(String fileName, String text) {
		System.out.println("Started writing data into " + fileName + ". ");
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));

			writer.append(text);
			System.out.println("Content successfully writen to file " + fileName + "! ");

		} catch (IOException ex) {
			System.out.println("Error opening/writing to file " + fileName + "!");
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {
				System.out.println("Error closing file!");
			}
		}

	}

	public int numOfLines(String fileName, List<List<String>> all) { // returns numbers of lines and save file
																		// "fileName" to cache as variable "all"
		int num = 0;
		BufferedReader in = null;
		String str = null;
		List<String> listOfStr = null;
		// HashMap<String, List<List<String>>> allHash = new HashMap<String,
		// List<List<String>>>();

		try {
			in = new BufferedReader(new FileReader(fileName));
			while ((str = in.readLine()) != null) {
				String[] lineString = str.split(";");
				listOfStr = Arrays.asList(lineString);
				all.add(listOfStr);
				num++;
			}
		} catch (IOException ex) {
			System.out.println("Problem reading file.\n" + ex.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ignore) {
			}
		}

		System.out.println("There are " + num + " lines in " + fileName);
		System.out.println("Whole document is saved into cache.");
		return num;
	}

	public int numOfLinesHash(String fileName, HashMap<String, String> allGospodinjciHash, int hashIDColumn,
			int hashDataColumn) { // returns numbers of lines and save file "fileName" to HashMap as variable
									// "allGospodinjciHash"
		int num = 0;
		BufferedReader in = null;
		String str = null;
		List<String> listOfStr = null;
		// HashMap<String, List<List<String>>> allHash = new HashMap<String,
		// List<List<String>>>();

		try {
			in = new BufferedReader(new FileReader(fileName));
			while ((str = in.readLine()) != null) {
				String[] lineString = str.split(";");
				listOfStr = Arrays.asList(lineString);
				allGospodinjciHash.put(listOfStr.get(hashIDColumn), listOfStr.get(hashDataColumn));
				num++;
			}
		} catch (IOException ex) {
			System.out.println("Problem reading file.\n" + ex.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ignore) {
			}
		}

		System.out.println("There are " + num + " lines in " + fileName);
		System.out.println("Whole document is saved into cache.");
		return num;
	}

	public static List<String> findLines(String fileName, int lineNum, int startRowNum, int endRowNum) {
		String line = null;
		int currentLineNo = 0;
		List<String> lineList = new ArrayList<>();
		List<String> cellList = new ArrayList<>();

		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fileName));

			// read all lines till the "lineNum"
			while (currentLineNo < lineNum) {
				if (in.readLine() == null) {
					// oops, early end of file
					throw new IOException("File too small");
				}
				currentLineNo++;
			}

			// read "lineNum"

			line = in.readLine();
			if (line == null) {
				return null;
			}

			String[] lineString = line.split(";");
			lineList = Arrays.asList(lineString);
			cellList.add(lineList.get(startRowNum));
			cellList.add(lineList.get(endRowNum));

		} catch (IOException ex) {
			System.out.println("Problem reading file.\n" + ex.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ignore) {
			}
		}
		return cellList;
	}

}
