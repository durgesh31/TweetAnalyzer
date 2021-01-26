package com.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TweetAnalyzerApp {
	

	public static void main(String[] args) {
		
		List<String> tweetCollection = new ArrayList<>();
		HashMap<String,Integer> hashTagCounts = new HashMap<>();
		
		BufferedReader reader;
		
		Predicate<String> isHashTag = (String word) -> word.startsWith("#");
		Predicate<String> containsHashTag = (String word) -> word.contains("#");
		
		try {
			
			System.out.println("Reading file");
			reader= new BufferedReader(new FileReader("C:/Users/dprasadn.PARTNERS/Downloads/tweets.txt"));
			
			String line = reader.readLine();
			
			while(line!=null) {
				tweetCollection.add(line);
			    line= reader.readLine(); 	
			}
			
			reader.close();
				
		}catch(IOException e) {
			System.out.println("IO Exception in reading file");
		}
		
		
		
	//	String tweet = "#TeamIndia breached #FortressGabba. Won Test Series 2-1 #AusVsInd";
		
		for (String tweet: tweetCollection) {	
				
				String[] tweetTokens= tweet.split(" ");
				
				Stream<String> tokenStream = Stream.of(tweetTokens);
				
				tokenStream.forEach( word -> {
					if(isHashTag.test(word) || containsHashTag.test(word)){
						
						String[] splits = word.split("#");
						String tag = splits[1];
						
						if(hashTagCounts.containsKey(tag))
							hashTagCounts.put(tag,hashTagCounts.get(tag)+1);
						else
							hashTagCounts.put(tag,1);				 	
					}
				});
		
		}	
		
		System.out.println(hashTagCounts);
		 	
		HashMap<String,Integer> sortedCounts = sortByHighest(hashTagCounts);
		
	
		System.out.println();
		System.out.println("Top 10 hashtags");
		System.out.println("----------------------");
		
		Iterator<Entry<String, Integer>> itr = sortedCounts.entrySet().iterator();
		
		
		for(int i=0; i<10 && itr.hasNext()  ;i++) {
			
			Map.Entry<String, Integer> entry = itr.next();
			System.out.println(entry.getKey()+"   ---     "+entry.getValue());
		}
		
		
	}
	
	public static HashMap<String,Integer> sortByHighest(HashMap<String,Integer> hm) {
		
		
		List<Entry<String, Integer>> list = 
				new LinkedList<Entry<String,Integer>>(hm.entrySet());
		
		
		Collections.sort(list, new Comparator<Entry<String,Integer>> (){
			public int compare(Entry<String,Integer> o1,
								Entry<String,Integer> o2)
			{
			  return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		
		HashMap<String,Integer> temp = new LinkedHashMap<String,Integer>();
		for(Entry<String,Integer> m: list)
			temp.put(m.getKey(),m.getValue());
		
	//	System.out.println("Printing temp"+temp);
		
		return temp;
	}

}
