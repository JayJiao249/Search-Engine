import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
public class Tokenizer {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scanner = new Scanner(System.in);  

		System.out.println("Please enter the directory in which the txt file dictionary is stored");
		String directory = scanner.nextLine(); 

		String[] commonWord = {"a","is","!",".", "?", ":", ",", ";" , "-" , "=", "\\", "[", "]", "{", "}", "'", "\"", "about" ,"all" ,"also" ,"and", "i" ,"as" ,"at" ,"be" ,"because" ,"but" ,"by" ,"can" ,"come" ,"could" ,"day" ,"do" ,"even" ,"find" ,"first" ,"for" ,"from" ,"get" ,"give" ,"go" ,"have" ,"he" ,"her" ,"here" ,"him" ,"his" ,"how" ,"I" ,"if" ,"in" ,"into" ,"it" ,"its" ,"just" ,"know" ,"like" ,"look" ,"make" ,"man" ,"many" ,"me" ,"more" ,"my" ,"new" ,"no" ,"not" ,"now" ,"of" ,"on" ,"one" ,"only" ,"or" ,"other" ,"our" ,"out" ,"people" ,"say" ,"see" ,"she" ,"so" ,"some" ,"take" ,"tell" ,"than" ,"that" ,"the" ,"their" ,"them" ,"then" ,"there" ,"these" ,"they" ,"thing" ,"think" ,"this" ,"those" ,"time" ,"to" ,"two" ,"up" ,"use" ,"very" ,"want" ,"way" ,"we" ,"well" ,"what" ,"when" ,"which" ,"who" ,"will" ,"with" ,"would" ,"year" ,"you" ,"your"};
		
		ArrayList<String> unique = new ArrayList<String>(); 
		
	//	String directory = "C:\\Users\\Jay\\eclipse-workspace\\Tokenizer\\src\\";
		String input = ("machine learning algorithim on the networking of modern day computer science, Super-Resolution Learning for Semantic Segmentation");
		input = input.toLowerCase(); 
   	 	input = input.replaceAll(",","");
   	 	
   	 	// the list to be returned
		ArrayList<String> list = new ArrayList<String>();
		
		StringTokenizer st = new StringTokenizer(input);
		
		// split string into individual words
	     while (st.hasMoreTokens()) {
	    	 boolean common = false;
	    	 String token = st.nextToken().toString(); 
	    	 System.out.println(token);
	    	 for (int i = 0; i < commonWord.length; i++) {
	    		 
	    		// check and remove common words
	    		if (commonWord[i].equals(token)){
	    			//System.out.println(commonWord[i]+"   "+token);
	    			common = true;
	    			break;
	    		}
	    	 }
	        // System.out.println(token);
	    	 
	    	 // make sure that word is not common and not repeated
	         if ((common == false) && (!(unique.contains(token)))){
	        	 //words.put(token,1);
	        	 unique.add(token);
	        	 //System.out.println(token);
	        	 
	        	 // reads txt file for the first letter of the word 
	        	 // -- I broken up the larger text file into A-Z sections to make search easier --
	        	 // these txt files are named from a-z
	        	 list.addAll(getWords(directory+token.charAt(0)+".txt", token));
	        	 
	         }
	         
	      
	         
	     	}
	     
	     // sorts the list in alphabetical order
	     list.sort(null);
	     
	     System.out.println(list);

		
	     } 
	
	public static ArrayList<String> getWords(String dir, String word) {
		String[] suffixes = {"s" , "es" , "en" , "ed" , "ing" , "er" , "est" , "age" , "al" , "ance" , "dom" , "ee" , "ence" , "er" , "ery" , "ication" , "ism" , "ist" , "ty" , "ment", "ness", "or", "sion", "tion" , "hood", "ship", "ify" , "ize" , "ise" ,  "able", "al" , "ful" , "ible" , "ic" , "ical" , "ish" , "less" , "ly" , "ous", "y" , "ward" , "wise" , "ways"};

		String placeHolder = word;
		
		//int key = 1;
		String str;
		ArrayList<String> list = new ArrayList<>();
		try {
	         BufferedReader in = new BufferedReader(new FileReader(dir));
	         
	         // checks if word is contained in the dictionary
	         // checks if word is contained by any word in the dictionary
	         // checks if word added the suffixes is contained in the dictionary
	         
	         while ((str = in.readLine()) != null) {
	        	 //System.out.println(str);
	        	 
	        	 boolean lock1 = false;
	        	 boolean lock2 = false;
	        	 
	        	 if (str.contains(placeHolder)  ){
	        		 for (int i = 0; i < suffixes.length; i++) {
	        			 if ((placeHolder+suffixes[i]).equals(str) )  {
	        				 list.add(str);
	        				// System.out.println(str);
	        				// System.out.println('1');
	        				 lock1 = true;
	        				 
	        			 }
	        		 } 
	        		 
	        	for (int i = 0; i < suffixes.length; i++) {
	        		String noSuf = suffixRemover(suffixes[i],placeHolder);
	        		 if (noSuf.equals(str) && (list.contains(noSuf) == false)) {
	        			 list.add(str);
	        			// System.out.println(str);
	        			 // System.out.println('2');
	        			 lock2 = true;
	        		 }
	        		
	        	}
	        	
	        	
	        	 }
	        	 
	        	 if (str.equals(placeHolder)&& (lock1 == false) && (lock2 == false)) {
	        		 list.add(str);
	        	 }

	        	 //System.out.println(list);
	         }
	         
	        
	         
	      } catch (IOException e) {
	      }
		 return list; 
		
	}

	public static String suffixRemover(String suffix, String word) {
		
		if (word.contains(suffix) && (word.length() > suffix.length())){
			if (word.substring(word.length()-suffix.length(),word.length()).equals(suffix)) {
			return word.substring(0,word.length()-suffix.length());
			}
		}
		return word;
		
	}
	 
}

