import java.util.*;
import java.util.Random;
import java.util.ArrayDeque; 
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.List; 
import java.util.Queue; 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;  
import java.io.InputStreamReader;  
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;  
import java.io.FileWriter;  
import java.io.FileOutputStream;  
import java.io.FileReader;  
import java.io.RandomAccessFile;  
import java.io.IOException;
 

public class Automata
{
    
	//reading input data
    public static String[] readline(String file) {
    	String strtemp[] = new String[2];
    	try {
    	            FileReader fr = new FileReader(file);
    	            BufferedReader br = new BufferedReader(fr);
    		    int readcursor = 0;
    		    while (readcursor<2) {
    		    strtemp[readcursor] = br.readLine();
    	//System.out.println(strtemp[readcursor]);
    		    readcursor++;
    	 }
    	br.close();
    	return strtemp;            
    	 
    	 } catch (IOException e) {
    	    e.printStackTrace();
    	 }
    	return strtemp;
    	}

    
    	//for writing the result into output.txt
    public static void writeline(String writerfile){
    	   try {
    	        FileWriter fw = new FileWriter("output.txt");
    		    fw.write(writerfile);            
    	            fw.close(); 
    	        } catch (IOException e) {
    	           e.printStackTrace();
    	        }

    	}
 
    
    public static int computeNextState(char[] pattern, int state, int character)
    {
    	int next;
        int StateNumber = pattern.length;

    	//match the next character, goto next state
        if (state < StateNumber && character == pattern[state]){
           next = state + 1;
           return next;
        }
        else{
         for (next = state; next > 0; next--)
          {
            if (pattern[next - 1] == character)
            {
            	int index = 0;
            	while(index < next - 1 && pattern[index] == pattern[state - next + 1 + index]){
            		index++;
            	}
            	//check if all characters before "next" are matched
                if (index == next - 1)  return next;
            }
          }
        }
        //no match at all
        return 0;
    }
 

    public static int[][] computeTransitionFunction(char[] pattern)
    {
        int state;
        int StateNumber = pattern.length;
        int[][] TF = new int[StateNumber + 1][123];
        for (state = 0; state <= StateNumber; state++){
            for (int character = 96; character < 123; character++){ //all lower case letters
                TF[state][character] = computeNextState(pattern, state, character);
            }
        }
        return TF;
    }
 

    
    public static String search(char[] pattern, char[] givenstring)
    {

	//Initialize
        int[][] TF = new int[pattern.length + 1][123];
        int[] result = new int[givenstring.length + 1];
        TF = computeTransitionFunction(pattern);

        int state = 0; 
        int index = 0;
	//go through the given string
        for (int i = 0; i < givenstring.length; i++)
        {
            state = TF[state][givenstring[i]];
            if (state == pattern.length)
            {
                result[index] = i - pattern.length + 1;
                index ++;
            }
        }
        String output = "";
        
        for (int i = 0; i < index; i++){
        	int tempint = result[i];

        	output += tempint;
        	if (i!=index-1)
        	output += " ";
        }
        
            return output;
    }
    
    public static String reverse(String str)
    {
        String strinv = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            
            char ch = str.charAt(i);
            strinv += ch;
        }
        return strinv;
    }
    
    public static void main(String[] args)
    {
        String s[] = readline(args[0]);
        String pattern = s[0];
        String givenstring = s[1];
        String output;
        String reverseoutput;
        output = search(pattern.toCharArray(), givenstring.toCharArray());
        String patterninv = reverse(pattern);
        reverseoutput = search(patterninv.toCharArray(), givenstring.toCharArray());
        output += " ";
        output += reverseoutput;
        writeline(output);

    }
}
