import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.Scanner;

public class Crawler {

	public static String directory;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Please enter number for which conference papers you would like to download");
		System.out.println("1 for ICML");
		System.out.println("2 for NeurIPS");
		System.out.println("3 for ICCV");
		
		Scanner scanner = new Scanner(System.in);  
		String choice = scanner.nextLine(); 
		System.out.println("Please enter the directory in which you want your files to be stored (a folder will the the best)");
		directory = scanner.nextLine(); 
		// Code for ICML website
		
	    if (choice.equals("1")) {
			String url = "http://proceedings.mlr.press/";
		
			try {
				Document document = Jsoup.connect(url).get();
				//System.out.println(document.outerHtml());
				Elements body = document.select("ul.proceedings-list");
			     // System.out.println("Relative Link: " + body);
				for (Element e : body.select("a[href]")) {
					
					//System.out.println("Relative Link: " + e.attr("href"));
					String url2 = url+e.attr("href")+"/";
					String fileName = e.attr("href");
					
					try {
						Document doc2 = Jsoup.connect(url2).get();
						Elements body2 = doc2.select("p");
						
						for (Element e3 : body2.select("a")) {
							if (e3.attr("href").contains(".bib"))
							//System.out.println(e3.absUrl("href"));
							download(e3.absUrl("href"),directory+"\\"+fileName, fileName);
							
						}
						
					}catch (Exception e2){
						e2.printStackTrace();
					}
							
				}
			
			}catch(Exception e){
				e.printStackTrace();
		
			}
		
			// Code for NeurIPS website
			
	    }else if (choice.equals("2")) {
	    	String url = "https://proceedings.neurips.cc/";
	    	
	    	
	    	
	    	try {
				Document document = Jsoup.connect(url).get();
				Elements body = document.select("ul");
				for (Element e : body.select("a[href]")) {
					
					if (e.attr("href").contains("paper")) {
						String url2 = url+e.attr("href");
						
							try {
							Document doc2 = Jsoup.connect(url2).get();
							Elements body2 = doc2.select("ul");
							
							for (Element e2 : body2.select("a[href]")) {
								if (e2.attr("href").contains("paper")) {
									
									String url3 = e2.absUrl("href");
									//System.out.println(url3);
									
									try {
									//System.out.println(e2.attr("href"));
										Document doc3 = Jsoup.connect(url3).get();
										Elements body3 = doc3.select("div.col");
										for (Element e3 : body3.select("a[href]")) {
											if (e3.attr("href").contains(".bib")) {
												
												String file = e3.attr("href");
												 int pos = (file).indexOf("/", 13);
												 String fileName = file.substring(pos+1, (file).indexOf("-"));
												//System.out.println(fileName);
												download(e3.absUrl("href"),directory+"\\"+fileName, fileName);
											}
											
										}
									}catch (Exception e3){
										e3.printStackTrace();
									}
									
								}
								
							}
							
						}catch (Exception e2){
							e2.printStackTrace();
						}
					}


							
				}
			
			}catch(Exception e){
				e.printStackTrace();
		
			}	
	    	
	    }else if (choice.equals("3")) {
	    	String url = "https://openaccess.thecvf.com/";
	    	try {
	    		Document document = Jsoup.connect(url).get();
	    		Elements body = document.select("dl");
	    		for (Element e : body.select("a[href]")) {
	    			System.out.println(e);
	    			if (e.toString().contains("Main")){
	    				String conUrl = e.absUrl("href");
	    				conUrl = conUrl.replaceAll(".py", "");
	    				//System.out.println(conUrl);
	    				if (conUrl.contains("CVPR2021") || 
	    					(conUrl.contains("CVPR2020")) ||
	    					(conUrl.contains("ICCV2019")) ||
	    					(conUrl.contains("CVPR2019")) ||
	    					(conUrl.contains("CVPR2018)"))){
	    					
	    					try {
		    					Document doc2 = Jsoup.connect(conUrl).get();
		    					Elements body2 = doc2.select("dl");
		    					for (Element e2 : body2.select("a[href]")){
		    						//System.out.println("-----------------");
		    						//System.out.println(e2);
		    						
		    						if ((conUrl.contains("CVPR2021")) && (e2.toString().contains("all"))) {
			    							String paperUrl = e2.absUrl("href").replaceAll(".py", "");
			    							try {
				    							Document docPaper = Jsoup.connect(paperUrl).get();
						    					Elements bodyPap = docPaper.select("dl");
						    					for (Element ePap : bodyPap.select("div.bibref")) {
						    						String line = ePap.toString();
//						    						
						    							line = line.replace("<div class=\"bibref pre-white-space\">","");
//						    							line = line.replaceAll(" <br /> ","");
						    							line = line.replaceAll("</div>","");
						    							line = line.substring(2,line.length());
						    							String str = line;
						    							//System.out.println(line);
						    							//System.out.println("----------------------");
						    							if ((str.contains("author"))&&(str.contains("@InProceedings"))) {
						    				            	while (str.length()!= 0) {
						    				            		int pos = str.indexOf(",");
						    				            		String info = str.substring(0,pos+1);
						    				            		writeFile(info);
						    				            		str = str.substring(pos+2,str.length());
						    				            		pos = str.indexOf("title");
						    				            		info = str.substring(0,pos);
						    				            		writeFile(info);;
						    				            		str = str.substring(pos,str.length());
						    				            		pos = str.indexOf("booktitle");
						    				            		info = str.substring(0,pos);
						    				            		writeFile(info);
						    				            		str = str.substring(pos,str.length());
						    				            		pos = str.indexOf("month");
						    				            		info = str.substring(0,pos);
						    				            		writeFile(info);
						    				            		str = str.substring(pos,str.length());
						    				            		pos = str.indexOf("year");
						    				            		info = str.substring(0,pos);
						    				            		writeFile(info);
						    				            		str = str.substring(pos,str.length());
						    				            		pos = str.indexOf("pages ");
						    				            		info = str.substring(0,pos);
						    				            		writeFile(info);
						    				            		str = str.substring(pos,str.length());
						    				            		writeFile(str);
						    				            		str = "";
						    				            		
						    				            		
						    				            	}
						    							}
						    							
						    							
						    					}
				    						
			    							}catch(Exception ex){
			    								ex.printStackTrace();
			    							}
			    					}else if (!(conUrl.contains("2021"))){	  
			    						String paperUrl = e2.absUrl("href").replaceAll(".py", "");
			    						try {
			    							Document docPaper = Jsoup.connect(paperUrl).get();
					    					Elements bodyPap = docPaper.select("dl");
					    					for (Element ePap : bodyPap.select("div.bibref")) {
					    						String line = ePap.toString();
					    						
					    							line = line.replace("<div class=\"bibref\">","");
					    							line = line.replaceAll(" <br /> ","");
					    							line = line.replaceAll("</div>","");
					    							line = line.substring(3,line.length());
					    							//System.out.println(line);
					    							//System.out.println("----------------------");
					    							writeFile(line);
					    					}
			    						
		    							}catch(Exception ex){
		    								ex.printStackTrace();
		    							}
			    					}
//		    						if (conUrl.contains("CVPR2021")) {
//		    							
//		    						}
		    						
		    					
		    					}
		    					
	    					}catch(Exception e9){
	    						e9.printStackTrace();
	    			    	}	
	    				
	    					
	    				}else if (!(conUrl.contains("WACV2021"))){
	    					try {
		    		    		Document document10 = Jsoup.connect(conUrl).get();
		    					Elements body10 = document10.select("dl");
		    					for (Element e10 : body10.select("div.bibref")) {
		    						String line = e10.toString();
		    						
		    							line = line.replace("<div class=\"bibref\">","");
		    							line = line.replaceAll(" <br /> ","");
		    							line = line.replaceAll("</div>","");
		    							line = line.substring(3,line.length());
		    							//System.out.println(line);
		    							//System.out.println("----------------------");
		    							writeFile(line);
		    					}
	    					}catch(Exception e8){
	    						e8.printStackTrace();
	    					}
	    					
	    				}else {
	    					try {
    							Document docPaper = Jsoup.connect(conUrl).get();
		    					Elements bodyPap = docPaper.select("dl");
		    					for (Element ePap : bodyPap.select("div.bibref")) {
		    						String line = ePap.toString();
//		    						
		    							line = line.replace("<div class=\"bibref pre-white-space\">","");
//		    							line = line.replaceAll(" <br /> ","");
		    							line = line.replaceAll("</div>","");
		    							line = line.substring(2,line.length());
		    							//System.out.println(line);
		    							//System.out.println("----------------------");
		    							writeFile(line);
		    					}
		    					}catch(Exception ex){
    								ex.printStackTrace();
    							}
	    					
	    				}
	    			}
	    			
	    			
	    		}
	    		
	    		
	    		
	    		
	    		
	    		
//	    		Document document = Jsoup.connect(url).get();
//				Elements body = document.select("dl");
//				for (Element e : body.select("div.bibref")) {
//					String line = e.toString();
//					
//						line = line.replace("<div class=\"bibref\">","");
//						line = line.replaceAll(" <br /> ","");
//						line = line.replaceAll("</div>","");
//						line = line.substring(3,line.length());
//						System.out.println(line);
//						System.out.println("----------------------");
//						
					


	    		
	    	}catch(Exception e){
				e.printStackTrace();
	    }
	    	
	    	
	    }
	
	
	}
	
	public static void download(String urlLink, String file, String fileName) {
		
		try {
			URL url = new URL(urlLink);
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			double fileSize = (double)http.getContentLengthLong();
			BufferedInputStream in = new BufferedInputStream(http.getInputStream());
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			byte[] buffer = new byte[1024];
			//double downloaded = 0.00;
			int read = 0;
			//double percentDownloaded = 0.00;
			while((read = in.read(buffer, 0, 1024)) >= 0) {
				bout.write(buffer, 0, read);
				//downloaded += read;
				//percentDownloaded = (downloaded*100)/fileSize;
				//String percent = String.format("%.4f", percentDownloaded);
				//System.out.println("Downloaded" + percent + "%"+fileName);
				
			}
			bout.close();
			in.close();
			System.out.println(fileName+ "   download complete");
			
		}catch (IOException ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	public static void writeFile(String line) throws IOException {
		
		//System.out.println("------------------");
		String path = directory+"\\"+"file.bib";
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
