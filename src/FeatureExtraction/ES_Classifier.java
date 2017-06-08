 package FeatureExtraction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ES_Classifier {

	public void rulebaseAnalyser(String[] docsentencewords, List<String> list,
			HashMap<String, ArrayList<String>> precedancevalues, HashMap<String, List<String>> wordSentimentDetails,
			String sentimentword) {
		int[] count = wordCount(wordSentimentDetails);
		if (precedancevalues.size() == 1) {

			if (list.contains("not") == true) {
				System.out.println("Sentence contains -not");
				if (list.indexOf("not") > list.indexOf(sentimentword)) {
					if (count[0] > count[1]) {
						System.out.println(
								"Entity has + ve value" + precedancevalues.get("Main").toString() + " : " + "++1");
					} else {
						System.out.println(
								"Entity has - ve value" + precedancevalues.get("Main").toString() + " : " + "--1");
					}
				} else {
					System.out.println("not considerd ");
				}

			} else {
				System.out.println("Sentence does not contains -not ");
				if (count[0] < count[1]) {
					System.out.println("Entity has + ve value" + precedancevalues.get("Main").toString() + " : " + "++1");
				} else {
					System.out.println("Entity has - ve value" + precedancevalues.get("Main").toString() + " : " + "--1");
				}
			}

		} else {
			if (list.contains("than") == true) {
				if (list.contains("not") == true && (list.indexOf("not") > list.indexOf(sentimentword))) {
					if (count[0] > count[1]) {
						System.out.println(precedancevalues.get("Main").toString() + "+1");
						System.out.println(precedancevalues.get("Sub").toString() + "-1");
					} else {
						System.out.println(precedancevalues.get("Main").toString() + "-1");
						System.out.println(precedancevalues.get("Sub").toString() + "1");
					}
				} else {
					System.out.println(precedancevalues.get("Sub").toString());
					if (count[0] > count[1]) {
						System.out.println(precedancevalues.get("Main").toString() + "-1");
						System.out.println(precedancevalues.get("Sub").toString() + "+1");
					} else {
						System.out.println(precedancevalues.get("Main").toString() + "+1");
						System.out.println(precedancevalues.get("Sub").toString() + "-1");
					}
				}

			} else {
				System.out.println("This not contains word -than");
			}
		}
	}

	public int[] wordCount(HashMap<String, List<String>> wordSentimentDetails) {
		int[] count = { wordSentimentDetails.get("Negative").size(), wordSentimentDetails.get("Positive").size() };
		return count;
	}

}
