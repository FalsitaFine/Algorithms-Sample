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

class Floyd
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
		   if(i!= writerfile.length-1) if(writerfile[i+1]!="") fw.write("\n");         
	 }
	 fw.close(); 
	 } catch (IOException e) {
	           e.printStackTrace();
	 }

	}

	  

  //Floyd Method
	    public static String[] floydmethod(int[][] matrix){
	    	int V = matrix.length;
		String[] output = new String[V];
		for(int i =0; i<V;i++) output[i] = "";
	        for(int i = 0; i < V; i++)for(int j = 0; j < V; j++){
	            if(matrix[i][j]<0){
	                output[0] = "Negative cycle";
	                return output;
	            }
	        }
	        
	        for(int k = 0;k<V;k++){
	            for(int i = 0;i<V;i++){
	                for(int j = 0;j<V;j++){
	                    matrix[i][j] = Math.min(matrix[i][j] , matrix[i][k] + matrix[k][j]);
	                }
	            }
	        }
	        for(int i = 0;i < V; i++){
	        	for(int j = 0;j < V; j++){
			  output[i] += matrix[i][j];
			  if (j!= matrix.length-1) output[i] += " ";
	        
	           // System.out.print(matrix[i][j] + " ");
	        } 	
	      	//  System.out.println("");

	    }
		return output;
	    } 

public static void dijkstra(int source,int[][] matrix){
    
    //Initialize of the matrix
    int V = matrix.length;
    int[] distance = new int[V];
    int[] parent = new int[V];
    String[] color = new String[V];
    
    for (int i = 0; i < V; i++){
    	distance[i] = NOCONNECT;
    	parent[i] = -1;
    	color[i] = "White";
    }

    distance[source] = 0;
    
 //relax  
    for(int i = 0;i < V;i++){
        int nearest = -1;
        for(int j = 0;j < V; j++){
            if(color[j] == "White" && (nearest == -1 || distance[nearest] > distance[j])) nearest = j;
        }

//marked as visited
        color[nearest] = "Black";
      
        for(int j = 0; j < V; j++){
            if(distance[j] > distance[nearest] + matrix[nearest][j]){
                distance[j] = distance[nearest] + matrix[nearest][j];
                parent[j] = nearest;
            }
        }
    }
    
    for(int i = 0;i<V;i++){
        matrix[V-1][i] = matrix[i][V-1] = distance[i];
    }
    for(int i = 0;i<V-1;i++){
        for(int j = 0;j<V-1;j++){
            matrix[i][j] = Math.min(matrix[i][j], matrix[i][V-1]+matrix[V-1][j]);
        }
    }
    

}
	    
	    


public static String[] addNewlines(int[][] matrix, String[] s){

    int negative = 0;
    int finish = 0;
    int index = matrix.length;
    int total = s.length;


    int[][] newmatrix = new int[total][total];
	String []splitting = new String[s.length];
	for (int i = 0; i<index;i++){
		for (int j = 0; j<index;j++){
			newmatrix[i][j] = matrix[i][j];
		}
	}
 //   System.out.println("new line read---");

	//   System.out.println(s[index]);
	//   System.out.println("---");


    while (index < total) {
		splitting = s[index].split(" ");
        finish = 1;
        int[] newrow = new int[total];
  //	    System.out.println(splitting[1]);

        for(int i = 0;i< total;i++){
            newrow[i] = Integer.valueOf(splitting[i]);
            newmatrix[i][index] = newrow[i];
        }
        for(int i = 0; i< total;i++){
        newmatrix[index][i] = newrow[i];
        }
        for(int i = index+1;i<total;i++){
        	for(int j = 0; j<total;j++){
        		newmatrix[i][j] = NOCONNECT;
        	}
        }
        

        for(int i = 0;i<newmatrix.length;i++){
        	for(int j = 0;j<newmatrix.length;j++){
              //     System.out.print(newmatrix[i][j]+ " ");
        }
    }
        
        renewMatrix(newmatrix);
        index++;
    }
        int n = newmatrix.length;
        String[] output = new String[n];
    if(finish == 1){

	for(int i =0; i<n;i++) output[i] = "";
        for(int i = 0;i<n;i++)for(int j = 0;j<n;j++){
            if(newmatrix[i][j]<0){
                output[0] = "Negative cycle";
		return output;
            }
        }


        for(int i = 0;i<n;i++){
        	for(int j = 0;j<n;j++){
			  output[i] += newmatrix[i][j];
			  if (j!= newmatrix.length-1) output[i] += " ";
	        
        }
         //   System.out.println("");

    }
    }
return output;
}	    


public static void renewMatrix(int[][] matrix){
    int n = matrix.length;
    for(int i = 0;i<n;i++){
	for(int j = 0;j<n;j++){
        if(matrix[i][j]<0){
            return;
        }
    }
 }
    dijkstra(matrix.length-1, matrix);
}


public static void main (String[] args)
{
	String s[] = readline(args[0]);
	String firstline = s[0];
	String []splitting = new String[s.length];
	int add = 0;

	splitting = firstline.split(" "); 

	  int V = splitting.length;
	  int[][] matrix = new int[V][V];
	  
if(s.length > V) add = 1;

	for(int i = 0; i< V; i++){
		splitting = s[i].split(" ");
		for(int j = 0; j< V; j++){
		  matrix[i][j] = Integer.valueOf(splitting[j]);
		}
	}

	for(int i = 0;i<s.length;i++)
	{
//	    System.out.println(s[i]);

	}

	String[] output,addoutput,finaloutput;
	finaloutput = new String[s.length+V];
	output = new String[V];
	addoutput = new String[s.length];

	for(int i = 0; i<s.length+V;i++){
		finaloutput[i] = "";
	}

	output = floydmethod(matrix);
	if (add == 1) addoutput = addNewlines(matrix,s);

	int negative = 0;

	if (output[0] == "Negative cycle"){
		finaloutput[0] = "Negative cycle";
		negative = 1;
	} else{
	for(int i = 0; i < V;i++){
	finaloutput[i] += output[i];
	}
	}

	if (add == 1){
		if (addoutput[0] == "Negative cycle" && negative ==1){
			finaloutput[1] = "Negative cycle";
		}
		if (addoutput[0] == "Negative cycle" && negative !=1){
		finaloutput [V] = "Negative cycle";
		}
		if (addoutput[0] != "Negative cycle" && negative ==1){
			for(int i = 1; i < V+1;i++){
				finaloutput[i] = "";
				finaloutput[i] += addoutput[i-1];
			}
		}
		if (addoutput[0] != "Negative cycle" && negative !=1){
			for(int i = V; i < s.length + V;i++){
				finaloutput[i] = "";
				finaloutput[i] += addoutput[i-V];
			}
		}
	}

writeline(finaloutput);


}

}
