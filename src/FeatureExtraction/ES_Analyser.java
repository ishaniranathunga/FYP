package FeatureExtraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class ES_Analyser {

	private static final String sentence = "H:\\L4_Final Year Project\\Dataset\\Testing\\001Demo.txt";
	private static final String negativewordsList = "H:\\L4_Final Year Project\\Dataset\\Testing\\Negative_Lexical.txt";
	private static final String positivewordsList = "H:\\L4_Final Year Project\\Dataset\\Testing\\Positive_Lexical.txt";

	public static void main(String[] args) {

		Tokenizer tokenizer = new Tokenizer();
		Extractor extractor = new Extractor();
		ES_Classifier classifier = new ES_Classifier();
		
		try {
			// tokenizes the words in the sentence **| Amazon| is | worse| than|Wal-mart| and| Google
			String[] docsentencewords = tokenizer.sentenceTokenizer(sentence);
			List<String> list = Arrays.asList(docsentencewords);
			
			// Extracting "#2 Entity Precedence"
			LinkedHashMap<String, LinkedHashSet<String>> temp = extractor.extractEntity(sentence);
			System.out.println("This is the temp value :"+ temp);
			
			HashMap<String, ArrayList<String>> precedancevalues = null;
			if (temp.containsKey("ORGANIZATION")) {
				LinkedHashSet<String> orglist = temp.get("ORGANIZATION");
				precedancevalues =extractor.checkPrecedance(docsentencewords,orglist.toArray(new String[orglist.size()]));	
				
				System.out.println(precedancevalues);
			}
			// Extracting "#3 Sentiment word"
			String[] negativewords = tokenizer.sentenceTokenizer(negativewordsList);
			String[] positivewords = tokenizer.sentenceTokenizer(positivewordsList);
			HashMap<String, List<String>> wordSentimentDetails = extractor.sentimentWordIdentifier(docsentencewords, negativewords, positivewords);
			String sentimentword=wordSentimentDetails.toString();
			System.out.println(sentimentword);
			
			classifier.rulebaseAnalyser(docsentencewords,list ,precedancevalues, wordSentimentDetails ,sentimentword);

		} catch (Exception e) { 
			e.getMessage();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
