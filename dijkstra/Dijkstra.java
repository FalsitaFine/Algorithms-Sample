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
 
 
class Dijkstra
{

    static final int NOCONNECT = 2000000;
    
  //for reading txt file
  	public static String[] readline(String file) {

  	       String strtemp[] = new String[32768];
  	try {
  	            FileReader fr = new FileReader(file);
  	            BufferedReader br = new BufferedReader(fr);
  	int readcursor = 0;        
  	    
  		while ((strtemp[readcursor] = br.readLine()) != null) {
  			readcursor++;
  	}
  	   	 br.close();
  		String str[] = new String[readcursor];
  		System.arraycopy(strtemp,0,str,0,readcursor);
  		return str;            
  	 
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


    public static int relax(int distance[], String color[])
    {
        int min = NOCONNECT, index = 0;
        int A = distance.length;
        for (int v = 0; v < A; v++)
            //renew the shortest way
        	if (color[v] == "White" && distance[v] <= min)
            {
                min = distance[v];
                index = v;
            }
        return index;
    }
 

        

 
    public static String dijkstra(int matrix[][], int source, int destination)
    {
        int A = matrix.length;

        int distance[] = new int[A]; 

        int route[][] = new int[A][A];
        
        //Initialize
        for(int count = 0; count < A; count++)
        {
            route[count][0] = source;
            route[count][A-1] = count;
            for(int i = 1; i < A-1; i++){
                route[count][i] = NOCONNECT;
            }
        }
        String color[] = new String[A];
 
        for (int i = 0; i < A; i++)
        {
            distance[i] = NOCONNECT;
            color[i] = "White";
        }
 
        distance[source] = 0;
 
//search through the matrix
        for (int count = 0; count < A-1; count++)
        {

            int u = relax(distance, color);
            int counter = 0;
            color[u] = "Black";
 
            for (int v = 0; v < A; v++){
            	//record the route
                if (color[v] == "White" && distance[u] != NOCONNECT && matrix[u][v]!=0 && distance[u]+matrix[u][v] < distance[v]){
                    distance[v] = distance[u] + matrix[u][v];
                    route[count][counter+1] = u;
                    counter++;  
            }
        }
        }
 
       	String output = "";
       	output += distance[destination];
       	output += ": ";

//give out the result from source to destination
        for (int j = 0; j<route[destination].length; j++){
            
            if(route[destination][j]!=NOCONNECT) {
            	output += route[destination][j];
            	output += " ";
            }

    }
        return output;
    }
 


    public static void main (String[] args)
    {
    	//read from the input
    	String s[] = readline(args[0]);
    	String firstline = s[0];
    	String []splitting = new String[s.length];
    	
    	splitting = firstline.split(" "); 
    	int source, destination;
    	//get source and destination
    	source = Integer.valueOf(splitting[0]);
    	destination = Integer.valueOf(splitting[1]);

    	  int[][] matrix = new int[s.length-1][s.length-1];
    	//create the matrix
    	for(int i = 0; i< s.length-1; i++){
    		splitting = s[i+1].split(" ");
    		for(int j = 0; j< s.length-1; j++){
    		  matrix[i][j] = Integer.valueOf(splitting[j]);
    		}
    	}
    	
    String output;
        output = dijkstra(matrix, source, destination);
        writeline(output);
    }
}
