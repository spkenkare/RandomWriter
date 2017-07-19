package assignment;
import java.io.*;
import java.util.*;


/*
 * CS 314H Assignment 2 - Random Writing
 *
 * Your task is to implement this RandomWriter class
 */
public class RandomWordWriter implements TextProcessor {

	private static String source;
	private static String result;
	private static int k;
    private static String text;
    private static String seed;
	private static File file;
	private static String[] wordList;
	private static ArrayList<String> words  = new ArrayList<String>();
    
	public static void main(String[] args) {
    	
    	try {
    		source = args[0];
        	result = args[1];
        	k = Integer.parseInt(args[2]);
        	int length = Integer.parseInt(args[3]);
      	
        	if(k<1||length<0)
        		System.exit(0);
        	
        	TextProcessor proc = createProcessor(k);
        	file = new File(result);
			proc.readText(source);
			proc.writeText(result, length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    		    	
    	
    }

    // Unless you need extra logic here, you might not have to touch this method
    public static TextProcessor createProcessor(int level) {
      return new RandomWordWriter(level);
    }

    private RandomWordWriter(int level) {
      // Do whatever you want here
    }


    public void readText(String inputFilename) throws IOException {
    	try 
    	{
			FileReader fr = new FileReader(inputFilename);
			BufferedReader br = new BufferedReader(fr);
			String s=new String(); 
			
			text="";
			while((s = br.readLine()) != null) 
			{	
				if(s.length()>0&&s.charAt(s.length()-1)==' ')
					s=s.substring(0, s.length());
				text+=s;
				text+=" ";
			} 
			fr.close();
			wordList=text.split(" ");
			for(int i=0; i<wordList.length; i++)
			{
				if(!wordList[i].equals(" "))
				{
					words.add(wordList[i]);
				}
			}
			
		} 
    	catch (FileNotFoundException e) 
    	{
			e.printStackTrace();
		}
   	
    }

    public void writeText(String outputFilename, int length) throws IOException {
    	
    	FileWriter writer = new FileWriter(file, false); 
    	Random rand = new Random();
    
		String [] seedWords = new String[k];
    	int index = rand.nextInt(words.size()-k);
		for(int i=index; i<index+k; i++)
		{
			seed+=words.get(i)+" ";
			seedWords[i-index]=words.get(i);
		}
		
		
		int num=0;
    	while(num<length)
    	{
    		ArrayList<String> list = new ArrayList<String>();
    		for(int i=0; i<words.size()-k-1; i++)
    		{
    			boolean b = true;
    			for(int j=0; j<k; j++)
    			{
    				if(!seedWords[j].equals(words.get(i+j)))
    				{
    					b=false;
    				}
    			}
    			if(b)
    			{
    				list.add(words.get(i+k));
    			}
    		}
    		
    		if(list.size()==0) {
    			
    			index = rand.nextInt(words.size()-k);
    			for(int i=index; i<index+k; i++)
    			{
    				seed+=words.get(i)+" ";
    				seedWords[i-index]=words.get(i);
    			}
    			seed=seed.substring(0, seed.length());
    			writer.write(seed);    			
    		}
    		
    		else {
    			int j = rand.nextInt(list.size());
    			seed+=list.get(j)+" ";
    			writer.write(list.get(j)+" ");
    			for(int i=0; i<seedWords.length-1; i++)
    			{
    				seedWords[i]=seedWords[i+1];
    			}
    			seedWords[seedWords.length-1]=list.get(j);
    			seed = seed.substring(seed.indexOf(" ")+1);
    			
    			//writer.write(seed.substring(seed.lastIndexOf(" ")+1)); 
    		}
    		num++;   		
    	}
    	writer.close();
        
    }
}
