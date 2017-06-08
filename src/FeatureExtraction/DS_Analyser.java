package FeatureExtraction;

import java.io.IOException;

public class DS_Analyser {
	
	static String fileName = "H://L4_Final Year Project//Dataset//DS_Testing//Training Data Set(N,P).csv";

	public static void main(String[] args) throws IOException {
		try {
			DS_corpusgenerator.generateFeatureVector(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
