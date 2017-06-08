package FeatureExtraction;


import java.io.IOException;

public class Analyser {
	
	static String fileName = "H://L4_Final Year Project//Dataset//Analyser//Analyser.txt";

	public static void main(String arg[]) throws IOException {
			try {
				DS_corpusgenerator.generateFeatureVector(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
