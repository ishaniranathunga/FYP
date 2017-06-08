package FeatureExtraction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DS_FeatureVectorGenerator {
	private static final String corpus = "H:\\L4_Final Year Project\\Dataset\\DS_Testing\\Training Corpus(N,P).txt";
	private static final String negativelexical = "H:\\L4_Final Year Project\\Dataset\\Testing\\Negative_Lexical.txt";
	private static final String positiveLexical = "H:\\L4_Final Year Project\\Dataset\\Testing\\Positive_Lexical.txt";
	
	public static HashMap<String, Integer> getFeaturevector(String document) throws IOException {
//		String Document = "H:\\L4_Final Year Project\\Dataset\\Testing\\001.txt";		
		Tokenizer tokenizer = new Tokenizer();	
		Lemmatizer lemmatizer = new Lemmatizer();
		
		// Tokenising the documents
		String[] documentwordlist = tokenizer.sentenceTokenizerCustom(document);
		String[] corpusword = tokenizer.sentenceTokenizer(corpus);
		String[] lecxicalnegativeword = tokenizer.sentenceTokenizer(negativelexical);
    	String[] lecxicalpositiveword = tokenizer.sentenceTokenizer(positiveLexical);
		
		List<String> negativeLecxicalTemp = new ArrayList();
		List<String> positiveLecxicalTemp = new ArrayList();
		List<String> corpusnegativewordTemp = new ArrayList();
		List<String> corpuspositivewordTemp = new ArrayList();
		
		Collections.addAll(negativeLecxicalTemp, lecxicalnegativeword);
		Collections.addAll(positiveLecxicalTemp, lecxicalpositiveword);
		Collections.addAll(corpusnegativewordTemp, corpusword);
		Collections.addAll(corpuspositivewordTemp, corpusword);
				
		negativeLecxicalTemp.retainAll(corpusnegativewordTemp);
		positiveLecxicalTemp.retainAll(corpuspositivewordTemp);
		
//		System.out.println(negativeLecxicalTemp.toString());
//		System.out.println(positiveLecxicalTemp.toString());
		
		HashMap<String, Integer> featureMatrix  = new HashMap<String, Integer>();
		for(String a : positiveLecxicalTemp){
			featureMatrix.put(a, 0);
		}
		for(String a : negativeLecxicalTemp){
			featureMatrix.put(a, 0);
		}
		
		for(String a : documentwordlist){
			if(positiveLecxicalTemp.contains(a)){
				int val = featureMatrix.getOrDefault(a, 0);
				featureMatrix.put(a, val+1);
				
			}
			else if(negativeLecxicalTemp.contains(a)){
				int val = featureMatrix.getOrDefault(a, 0);
				featureMatrix.put(a, val-1);
			}			
		}
		
//		System.out.println(featureMatrix.toString());
		// Identify Sentiment words 
		return featureMatrix;
		
		
		
		
		

	}

}
