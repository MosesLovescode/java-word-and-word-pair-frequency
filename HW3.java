
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class HW3 {
    
    public static HashMap<String, Integer> counts = new HashMap<>();
    public static HashMap<String, Integer> count_pair = new HashMap<>();
    public static List<String> pairs = new ArrayList<String>();
    
    private static String lowerCase;
    public static void main(String[] args) throws IOException {
        
        input();
        
        
    }

    public static void input() throws IOException {
      
        Scanner sc= new Scanner(System.in);
        System.out.println("Filename,Number of word-pairs");
        String in= sc.nextLine();
        String[] inp = in.split(" ");
        String filename = inp[0];
        int index = Integer.parseInt(inp[1]);
        sc.close();
        readfile(filename,index);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    readfele(filename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }); 
        t1.start();
        
        readfele(filename);
        
        for (String i : pairs){
            int count = Collections.frequency(pairs, i);
            count_pair.put(i,count);
        }
        System.out.println("Top "+index+" word_pairs");
        
        System.out.println(getTopKeysWithOccurences(count_pair,index));
       
    }

    public static void readfile(String filename,int index) throws IOException {
        BufferedReader obj = new BufferedReader(new FileReader(filename));
       
        String strCurrentLine;
        while ((strCurrentLine = obj.readLine()) != null) {
            String result = strCurrentLine.replaceAll("\\p{Punct}", "");
            if (!result.equals("")){
            counter(result.toLowerCase());
        }}
        System.out.println("Top "+index+" word_frequencies");
        System.out.println(getTopKeysWithOccurences(counts,index));
     
        
        obj.close();
    }

    public static int objectToInt(Object obj)
    {
        int x = (Integer)obj;
        return x;
    }

    public static Object IntToObject(int integer)
    {
        Object x = (Object)integer;
        return x;
    }

    public static void counter(String text) throws IOException{
        String[] key = text.split(" ");
        for (String a : key)
            if (!readstop(a)){
            if (!a.equals("")){
            if(counts.containsKey(a)){
                int x = objectToInt(counts.get(a));
                counts.put(a,x+1);
                
            }else{
               
                counts.put(a,1);
            }}}
    }

    public static boolean readstop(String text) throws IOException {
        ArrayList<String> stopwords = new ArrayList<String>();
        BufferedReader spobj = new BufferedReader(new FileReader("stop.txt"));
        String strCurrentLine;
        while ((strCurrentLine = spobj.readLine()) != null) {
            stopwords.add(strCurrentLine.toLowerCase());
        }
        spobj.close();
        boolean result = stopwords.contains(text);
        if(result){
            return true;
        }
        else{
            return false;
        }     
    }


    public static void readfele(String filename) throws IOException {
        FileInputStream fis=new FileInputStream(filename);
        Scanner sc=new Scanner(fis); 
        String line;
        while(sc.hasNextLine())  
        {  
            
            line = sc.nextLine();
            if(!line.equals("")){
                String result = line.replaceAll("\\p{Punct}", "");
                lowerCase = result.toLowerCase();
                pairs(lowerCase);
            }
        }  
            sc.close();
  
    }

    public static void pairs(String text) throws IOException 
    {
        String[] words = text.split(" ");
        
        for (int i = 0; i < words.length-1; ++i) {
            if(!readstop(words[i])){
                if(!words[i].equals("")){
                    pairs.add(words[i] + " " + words[i+1]);
                }
                
            }
            
        }
        
    }


    public static List<Entry<String,Integer>> getTopKeysWithOccurences(Map map,int top) {
        List<Entry<String,Integer>> results = new ArrayList<>(map.entrySet());
        Collections.sort(results, new EntryComparator());
        return results.subList(0, top);
    }
    public HW3() {
    } 
}

class EntryComparator implements Comparator<Entry<String,Integer>> {

    @Override
    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
        if (o1.getValue() < o2.getValue()) {
            return 1;
        } else if (o1.getValue() > o2.getValue()) {
            return -1;
        }
        return 0;
    }

}
