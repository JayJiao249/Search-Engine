import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Combiner {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);  
		System.out.println("Please enter the directory in which the files are stored that needs to be combined");
		String directory = scanner.nextLine(); 
		System.out.println("Please enter name of file after combine");
		String fName = scanner.nextLine();
		File folder = new File(directory);
		File[] listOfFiles = folder.listFiles();
		int readcount = 0;
		int writecount = 0;
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        System.out.println(file.getName());
		    	try {
		    		BufferedReader in = new BufferedReader(new FileReader(directory+"\\"+file.getName()));
		    		String str;
		    		while ((str = in.readLine()) != null) {
				        
				        System.out.println(str);
				        readcount +=1;
				        String path = (directory+"\\"+fName+".bib");
						try {
				            FileWriter fw = new FileWriter(path, true);
				            fw.write(str+"\n");
				            writecount+=1;
				            fw.close();
				            //System.out.println(text);
				        }
				        catch(IOException e) {
				        	e.printStackTrace();
				        }
				        //System.out.println(data);
				      }
				     // myReader.close();
		    	} catch (IOException e) {
			    }
		    }
		}
		
		//System.out.println("read "+ readcount + " line, and write "+ writecount+ " lines");
		
		

		}
	

}
