import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.PriorityQueue;
import java.util.Comparator;


public class HW3
{
	private static String[] w = null;
	private static int[] r = null;
	public static void main(String[] args)
	{
		try{
			System.out.println("Filename,Number of word-pairs");//input display
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//imput object
			String[] inp = br.readLine().split(" ");//split input
			
			String filename = inp[0];
			int index = Integer.parseInt(inp[1]);
			
			w = new String[index];
        		r = new int[index];
        		int n = index;
			
			//read input[0] file object
			FileReader fr = new FileReader(filename);
			BufferedReader br1 = new BufferedReader(fr);
			
			FileReader sp = new FileReader("stop.txt");
			BufferedReader br2 = new BufferedReader(sp);
			
			String text = "";
			String text_sp = "";
        		String sz = null;
        		String sz_sp = null;
        	
        		
        		while((sz=br1.readLine())!=null)
        		{
        			text = text.concat(sz.toLowerCase());
        		}//while
        		
        		while((sz_sp=br2.readLine())!=null)
        		{
        			text_sp = text_sp.concat(sz_sp.toLowerCase()+" ");
        		}//while
        		String[] stopwords = text_sp.split(" ");
        		String[] words = text.split(" ");
        		String[] uniqueLabels;
        		
        		int count = 0;
        		
        		
        		String txt = "";
        		for(int jj = 0; jj<words.length; jj++)
        		{
        			for (int kk = 0; kk<stopwords.length; kk++ )
        			{
        				if(!words[jj].equals(stopwords[kk]))
        				{
        					txt = txt.concat(words[jj]+" ");
        					
        				}
        			}
        		}
        		String[] new_words = txt.split(" ");
        		uniqueLabels = getUniqLabels(new_words);
        		for(int j=0; j<n; j++){
                		r[j] = 0;
            		}//for
            		
            		for(String l: uniqueLabels)
        		{
            			if("".equals(l) || null == l)
            			{
                			break;
            			}//if        
            			for(String s : words)
            			{
                			if(l.equals(s))
                			{
                    				count++;
                			}//if             
            			}//for

            			for(int i=0; i<n; i++){
                			if(count>r[i]){
                    				r[i] = count;
                    				w[i] = l;
                   				 break;
                			}//if
            			}//for
            		count = 0;
            		}
            		display(n);
            		
            		//WORD PAIR
            		InputStream in = System.in;
            		in = getFileInputStream(filename);
            		if (in != null)
            		{
            			@SuppressWarnings("resource")
        			Scanner sc = new Scanner(in);   
        			String word;
        			List<String> inputWords = new ArrayList<String>();
        			Map<String, List<String>> result = new HashMap<String, List<String>>();
        			//Map<String, <String>> fin_result = new HashMap<String, String>();
        			Map<String, Integer> fin_result = new HashMap<String, Integer>();

        			
        			while (sc.hasNext()) 
        			{
        				word = sc.next(); 
        				if (!word.equals("---")) 
        				{
        					inputWords.add(word.toLowerCase());}
        				}
        				for(int i = 0; i < inputWords.size()-1 ; i++)
        				{
        					String thisWord = inputWords.get(i);
        					String nextWord = inputWords.get(i+1);
        					if(!result.containsKey(thisWord))
        					{
        						result.put(thisWord, new ArrayList<String>());
        					}
        					result.get(thisWord).add(nextWord);
        				} 
        			result.forEach((k, v)-> fin_result.put(k,v.size()));
        			List<Entry<String, Integer>> greatest = findGreatest(fin_result, n);
        			System.out.println("Top_"+n+" word-pairs:");
        			for (Entry<String, Integer> entry : greatest)
        			{
        				System.out.println(entry);
        			}
            		}
			
		}//try
		catch (Exception e) {
        			System.err.println("[ERR] : "+e.getMessage());
    			}//catch
	}//main
	
	
	private static <K, V extends Comparable<? super V>> List<Entry<K, V>> findGreatest(Map<K, V> map, int n)
    	{
        	Comparator<? super Entry<K, V>> comparator = new Comparator<Entry<K, V>>()
        	{
            		@Override
            		public int compare(Entry<K, V> e0, Entry<K, V> e1)
            		{
                		V v0 = e0.getValue();
                		V v1 = e1.getValue();
                		return v0.compareTo(v1);
            		}
        	};
        	PriorityQueue<Entry<K, V>> highest = 
            	new PriorityQueue<Entry<K,V>>(n, comparator);
        	for (Entry<K, V> entry : map.entrySet())
        	{
            		highest.offer(entry);
            		while (highest.size() > n)
            		{
                		highest.poll();
            		}
        	}

        	List<Entry<K, V>> result = new ArrayList<Map.Entry<K,V>>();
        	while (highest.size() > 0)
       		{
            		result.add(highest.poll());
        	}
        		return result;
    		}
	
	
	
	
	private static InputStream getFileInputStream(String fileName)
    	{
    		InputStream inputStream;

    		try {
        		inputStream = new FileInputStream(new File(fileName));
    		}
    		catch (FileNotFoundException e) {   
        		System.err.println("[ERR] :"+e.getMessage());
        		inputStream = null;
    		}
    		return inputStream;
    	}

	
	private static String[] getUniqLabels(String[] keys)
	{
    		String[] uniqueKeys = new String[keys.length];

    		uniqueKeys[0] = keys[0];
    		int uniqueKeyIndex = 1;
    		boolean keyAlreadyExists = false;

    		for(int i=1; i<keys.length ; i++)
    		{
        		for(int j=0; j<=uniqueKeyIndex; j++)
        		{
            			if(keys[i].equals(uniqueKeys[j]))
            			{
                			keyAlreadyExists = true;
            			}//if
        		}//for    

        		if(!keyAlreadyExists)
        		{
            			uniqueKeys[uniqueKeyIndex] = keys[i];
            			uniqueKeyIndex++;               
        		}//if
        		keyAlreadyExists = false;
    		}//for       
    		return uniqueKeys;
	}//getUniqlabels
	
	public static void display(int n){
		System.out.println("Top_"+n+" String");
    		for(int k=0; k<n; k++){
        		System.out.println(w[k]+" ("+r[k]+")");
    		}//for
	}//display
	
	
	
}//wordcount











