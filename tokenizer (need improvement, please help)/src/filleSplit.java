import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class filleSplit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int count = 0;

		try {
	         BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\Jay\\eclipse-workspace\\Tokenizer\\src\\words.txt"));
	         String str;
	         
	         
	         while ((str = in.readLine()) != null) {
	        	 writeFile(str.toLowerCase(), str.toLowerCase().charAt(0));
	            		
	            	//System.out.println(str);
	            //count +=1;
	          
	         }
	         System.out.println(str);
	      } catch (IOException e) {
	      }
		
		//System.out.println(count);

	}
	
	public static void writeFile(String line, char letter) throws IOException {
		
		//System.out.println("------------------");
		String path = "C:\\Users\\Jay\\eclipse-workspace\\Tokenizer\\src\\"+letter+".txt";
		// BufferedReader in = new BufferedReader(new FileReader(path));
		 String str = line;
		 
			 try {
		            FileWriter fw = new FileWriter(new File(path), true);
		            fw.append(str+"\n");
		            fw.close();
		            //System.out.println(text);
		        }
		        catch(IOException e) {
		        	e.printStackTrace();
		        }
		
	}

}
