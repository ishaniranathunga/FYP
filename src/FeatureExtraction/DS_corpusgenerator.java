package FeatureExtraction;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import org.ejml.EjmlParameters;
import org.ejml.simple.SimpleMatrix;

public class DS_corpusgenerator {

	private static final char DEFAULT_SEPARATOR = ',';
	private static final char DEFAULT_QUOTE = '"';

	public static void generateFeatureVector(String filename) throws Exception {

//		String csvFile = "H://L4_Final Year Project//Dataset//DS_Testing//qq.csv";
		Scanner scanner = new Scanner(new File(filename));
		List<String> line;
		int[][] table = new int[62][250];
		PrintWriter pw = new PrintWriter("H://L4_Final Year Project//Dataset//Training_Module//myfeatures.txt", "UTF-8");
		int i=0;
		while (scanner.hasNext()) {
			line = parseLine(scanner.nextLine());
			HashMap<String, Integer> temp = DS_FeatureVectorGenerator.getFeaturevector(line.get(1));
			System.out.println(temp.keySet());
			int j=0;
			for (String key: temp.keySet()){
				table[i][j] = temp.get(key);

				j+=1;
			}
			j=0;
			i+=1;
			System.out.println(line.get(0) + " print :  " +temp.size());
			
		}
		for(int i1=0;i1<table.length;i1++){
			pw.write(i1+" values =  "+Arrays.toString(table[i1])+"\n");
			System.out.println(i1 +" ::: "+Arrays.toString(table[i1]));
		}
		
		scanner.close();
	}

	public static List<String> parseLine(String cvsLine) {
		return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
	}

	public static List<String> parseLine(String cvsLine, char separators) {
		return parseLine(cvsLine, separators, DEFAULT_QUOTE);
	}

	public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

		List<String> result = new ArrayList<>();

		// if empty, return!
		if (cvsLine == null && cvsLine.isEmpty()) {
			return result;
		}

		if (customQuote == ' ') {
			customQuote = DEFAULT_QUOTE;
		}

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuffer curVal = new StringBuffer();
		boolean inQuotes = false;
		boolean startCollectChar = false;
		boolean doubleQuotesInColumn = false;

		char[] chars = cvsLine.toCharArray();

		for (char ch : chars) {

			if (inQuotes) {
				startCollectChar = true;
				if (ch == customQuote) {
					inQuotes = false;
					doubleQuotesInColumn = false;
				} else {

					// Fixed : allow "" in custom quote enclosed
					if (ch == '\"') {
						if (!doubleQuotesInColumn) {
							curVal.append(ch);
							doubleQuotesInColumn = true;
						}
					} else {
						curVal.append(ch);
					}

				}
			} else {
				if (ch == customQuote) {

					inQuotes = true;

					// Fixed : allow "" in empty quote enclosed
					if (chars[0] != '"' && customQuote == '\"') {
						curVal.append('"');
					}

					// double quotes in column will hit this!
					if (startCollectChar) {
						curVal.append('"');
					}

				} else if (ch == separators) {

					result.add(curVal.toString());

					curVal = new StringBuffer();
					startCollectChar = false;

				} else if (ch == '\r') {
					// ignore LF characters
					continue;
				} else if (ch == '\n') {
					// the end, break!
					break;
				} else {
					curVal.append(ch);
				}
			}

		}

		result.add(curVal.toString());

		return result;
	}
}