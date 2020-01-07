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

class Johnson
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
	public static void writeline(String[] writerfile){
	 try{
	           FileWriter fw = new FileWriter("output.txt");
	           for(int i = 0; i< writerfile.length;i++){
		   fw.write(writerfile[i]); 
		   fw.write("\n");           
	 }
	 fw.close(); 
	 } catch (IOException e) {
	           e.printStackTrace();
	 }

	}

	public static void negativeoutput(){
	 try{
	           FileWriter fw = new FileWriter("output.txt");
		   fw.write("Negative circle");           
	 fw.close(); 
	 System.exit(0);
	 } catch (IOException e) {
	           e.printStackTrace();
	 }

	}

	public static int[] bellmanfordmethod(int negative, int[][] matrix){
	    int V = matrix.length;
	    int[] d = new int[V];
	    for(int i = 0;i<V;i++){
	        for(int j = 0;j<V;j++){
	            for(int k = 0;k<V;k++){
	                d[k] = Math.min(d[k],d[j]+matrix[j][k]+d[k]);
	            }
	        }
	    }

//determine whether there is a negative cycle
	    for(int j = 0;j<V;j++){
	        for(int k = 0;k<V;k++){
		    if(matrix[j][k] != NOCONNECT){
	            if(d[k] > d[j]+matrix[j][k]+d[k]){
	                negativeoutput();
	            }
		    }
	        }
	    }

	    return d;
	}

public static int[] dijkstra(int source,int[][] matrix){
    
    //Initialize of the matrix
    int n = matrix.length;
    int[] distance = new int[n];
    int[] parent = new int[n];
    String[] color = new String[n];
    
    for (int i = 0; i<n; i++){
    	distance[i] = NOCONNECT;
    	parent[i] = -1;
    	color[i] = "White";
    }

    distance[source] = 0;
    
    
    for(int i = 0;i<n;i++){
        int nearest = -1;
        for(int j = 0;j<n;j++){
            if(color[j] == "White" && (nearest == -1 || distance[nearest]>distance[j])){
                nearest = j;
            }
        }
        color[nearest] = "Black";
        for(int j = 0;j<n;j++){
            if(distance[j]>distance[nearest]+matrix[nearest][j]){
                distance[j] = distance[nearest]+matrix[nearest][j];
                parent[j] = nearest;
            }
        }
    }
    return distance;

}
	    
	    

public static void main (String[] args)
{
	String s[] = readline(args[0]);
	String firstline = s[0];
	String []splitting = new String[s.length];
	int negative = 0;

	splitting = firstline.split(" "); 


	  int V = splitting.length;
	  int[][] matrix = new int[V][V];
	  
	for(int i = 0; i< V; i++){
		splitting = s[i].split(" ");
		for(int j = 0; j< V; j++){
		  matrix[i][j] = Integer.valueOf(splitting[j]);
		}
	}

	int[] h = new int[V];
	int[] distance = new int[V];

    h = bellmanfordmethod(negative,matrix);

	for(int i = 0;i<s.length;i++)
	{
//	    System.out.println(s[i]);

	}


	
String[]output = new String[matrix.length];
    for(int i = 0;i<matrix.length;i++){
        for(int j = 0;j<matrix.length;j++){
            matrix[i][j] += h[i]-h[j];
        }
    }
    for(int i = 0;i<matrix.length;i++){
    	distance= dijkstra(i,matrix);
	output[i] = "";
        for(int j = 0;j<matrix.length;j++){
	  output[i] += distance[j];
	  if (j!= matrix.length-1) output[i] += " ";
    	  //System.out.print(distance[j]+" ");

        }
		// System.out.println("");

    }
writeline(output);


}

}
