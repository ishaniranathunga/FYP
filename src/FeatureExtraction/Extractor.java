package FeatureExtraction;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;

public class Extractor {
	
	static CRFClassifier<CoreLabel> classifier;
	
	
	// ************************************************Entity Extraction ****************************************************************************************************
	
	public static LinkedHashMap<String, LinkedHashSet<String>> identifyNER(String text, String model) {
		LinkedHashMap<String, LinkedHashSet<String>> map = new<String, LinkedHashSet<String>> LinkedHashMap();
		String serializedClassifier = model;
		System.out.println(serializedClassifier);
		classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
		List<List<CoreLabel>> classify = classifier.classify(text);
		for (List<CoreLabel> coreLabels : classify) {
			for (CoreLabel coreLabel : coreLabels) {
				String word = coreLabel.word();
				String category = coreLabel.get(CoreAnnotations.AnswerAnnotation.class);
				if (!"O".equals(category)) {
					if (map.containsKey(category)) {
						// key is already their just insert in arraylist
						map.get(category).add(word);
					} else {
						LinkedHashSet<String> temp = new LinkedHashSet<String>();
						temp.add(word);
						map.put(category, temp);
					}
					System.out.println(word + ":" + category);
				}
			}
		}
		return map;
	}

	static String textPath = "E:\\newsoutput.txt";
	private static final Path WRITEFILE = FileSystems.getDefault().getPath(textPath);
	static String output;
	// static Charset charset = Charset.forName("US-ASCII");

	public  LinkedHashMap<String, LinkedHashSet<String>> extractEntity(String FILENAME) throws IOException {

		String content = readFile(FILENAME, StandardCharsets.UTF_8);
		LinkedHashMap<String, LinkedHashSet<String>> a = identifyNER(content,
				"H:\\L4_Final Year Project\\Implementation\\Stanford-coreNLP\\stanford-ner-2016-10-31\\classifiers\\english.all.3class.distsim.crf.ser");		
//		System.out.println(a.toString());
//		System.out.println(classifier.classifyToString(content));
// 		output = classifier.classifyWithInlineXML(content);
//		System.out.println(classifier.classifyToString(content, "xml", true));
//		writeFile(WRITEFILE, StandardCharsets.UTF_8);
		return a;
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	private static String writeFile(Path writefile, Charset encoding) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(writefile, encoding)) {
			writer.write(output, 0, output.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		return "Successful";
	}
	
	
	
	
	// ************************************************Precedence ****************************************************************************************************
	
	
	

	public HashMap<String, ArrayList<String>> checkPrecedance(String[] docsentencewords, String[] entitylist) {
		HashMap map = new HashMap<String, ArrayList<String>>();
		final String MAIN = "Main";
		final String SUB = "Sub";
		map.put(MAIN,new ArrayList());
		map.put(SUB,new ArrayList());

		if (entitylist.length == 1) {
			map.put(entitylist[0], MAIN);
		} else if (entitylist.length > 1) {
			List alist = Arrays.asList(docsentencewords);
			if ((alist.contains("than"))) {
				int thanIndex = alist.indexOf("than");
				HashMap<String, Integer> blist = getIndexOfEntityInSentence(docsentencewords, entitylist);
				for (String ent : entitylist) {
					if (blist.get(ent) < thanIndex) {
						ArrayList<String> a = (ArrayList<String>) map.get(MAIN);
						a.add(ent);
						map.put(MAIN,a );
					} else {
						ArrayList<String> b = (ArrayList<String>) map.get(SUB);
						b.add(ent);
						map.put(SUB, b);
					}
				}
			} else {
				for (String ent : entitylist) {
					map.put(ent, MAIN);
				}
			}
		}
		return map;
	}
	
	
	public HashMap<String, String> checkPrecedance1(String[] docsentencewords, String[] entitylist) {
		HashMap map = new HashMap<String, String>();
		final String MAIN = "Main";
		final String SUB = "Sub";

		if (entitylist.length == 1) {
			map.put(entitylist[0], MAIN);
		} else if (entitylist.length > 1) {
			List alist = Arrays.asList(docsentencewords);
			if ((alist.contains("than"))) {
				int thanIndex = alist.indexOf("than");
				HashMap<String, Integer> blist = getIndexOfEntityInSentence(docsentencewords, entitylist);
				for (String ent : entitylist) {
					if (blist.get(ent) < thanIndex) {
						map.put(ent, MAIN);
					} else {
						map.put(ent, SUB);
					}
				}
			} else {
				for (String ent : entitylist) {
					map.put(ent, MAIN);
				}
			}
		}
		return map;
	
	
	}
	private HashMap<String, Integer> getIndexOfEntityInSentence(String[] docsentencewords, String[] entitylist) {
		HashMap dic = new HashMap<String, Integer>();
		List alist = Arrays.asList(docsentencewords);
		for (String ent : entitylist) {
			dic.put(ent, alist.indexOf(ent));
		}
		return dic;
	}
	
	
	
	
	// ************************************************Sentiment word Identification ******************************************************************************
	
	
	
	
	public HashMap<String, List<String>> sentimentWordIdentifier(String[] wordsofsentence, String[] negativewordlist, String[] positivewordlist) throws IOException {

		HashMap<String, List<String>> arraynew = new HashMap<String, List<String>> ();
		List nCorpus = Arrays.asList(negativewordlist);
		List pCorpus = Arrays.asList(positivewordlist);
		
		List<String> positive = new ArrayList<String>();
		List<String> negative = new ArrayList<String>();
				
		for (String words : wordsofsentence) {
			if (nCorpus.contains(words)) {
				negative.add(words);
			}
			else if(pCorpus.contains(words)){
				positive.add(words);
			}
		}		
		arraynew.put("Positive", positive);
		arraynew.put("Negative", negative);
		
		return arraynew;
	}

}

	
	
	
