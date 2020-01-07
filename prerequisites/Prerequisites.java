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
 



@SuppressWarnings("unchecked")
class Prerequisites
{
	

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
	 try{
	           FileWriter fw = new FileWriter("output.txt");
		   fw.write(writerfile) ;       

	 fw.close(); 
	 } catch (IOException e) {
	           e.printStackTrace();
	 }

	}

	    
	    
//add an vertex to the graph    
    public static void addVertex(String[] courses,int s,String Obj){
        courses[s] = Obj;
    }
    
//add a adj edge to the list
    public static void addEdge(LinkedList[] adj, int s,int d) {
        adj[s].add(d);
    }
    
    
  
    // DFS function
    public static void DepthFirstSearch(LinkedList[] adj, String[] courses, int v, String color[], Stack stack, Stack coursestack, int time)
    {
	//marked as visited
        color[v] = "Black";

        time++;

        Iterator<Integer> vertex = adj[v].iterator();
        while (vertex.hasNext())
        {
            int vertexv = vertex.next();
            if (color[vertexv]=="White")
                DepthFirstSearch(adj, courses, vertexv, color, stack, coursestack, time);
        }

        //push the vertex into the stack after it is finished
        stack.push(v);
        coursestack.push(courses[v]);

    }
    

    public static String topologicalSort(LinkedList[] adj, String[] courses,int V)
    {
        Stack stack = new Stack();
        Stack coursestack = new Stack();
        String output = "";
        // initialize
        String color[] = new String[V];
        for (int i = 0; i < V; i++)
            color[i] = "White";
        int time = 0;
        
        //run DFS for each vertex
        for (int i = 0; i < V; i++)
            if (color[i] == "White")
                DepthFirstSearch(adj, courses, i, color, stack, coursestack, time);
        
        int index = 0;
        while (coursestack.empty()==false){
          //  System.out.print(stack.pop() + " ");
        
        output += coursestack.pop().toString();
        if (coursestack.empty() == false){
        output += " ";

        }
        }
        return output;
    }
   
    
    
    
    public static void main(String args[])
    {
           String s[] = readline(args[0]);

           //length of the matrix
    	   int V = s.length; 
   		// System.out.println(V);

//read the inputfile
    	   LinkedList adj[];
    	   String courses[];
    	   String splitbefore[];
    	   String splitafter[];
    	   String splittemp[];
    	   String splitedge[][];
    	   
         adj = new LinkedList[V];
         courses = new String[V];
         splitbefore = new String[V];
         splitafter = new String[V];
         splittemp = new String[2];
         splitedge = new String[V][V];

         
         for (int i=0; i<V; i++){
             adj[i] = new LinkedList();
             courses[i] = "";
             splittemp = s[i].split(":"); 
             splitbefore[i] = splittemp[0];
             courses[i] = splitbefore[i];
             if (splittemp.length == 2)
             splitafter[i] = splittemp[1];
         }
         
         for (int i=0; i<V; i++){
        	 if (splitafter[i] != null){
             splitedge[i] = splitafter[i].split(" "); 
       		// System.out.println(splitedge[i][0]);
       		 //System.out.println(splitedge[i].length);
        	 }
         }
   		// System.out.println(splitedge[4][0]);
   		// System.out.println(courses[3]);

//add the edges and vertex
         for (int i=0; i<V; i++){
        	 if (splitedge[i][0]!=null){
             for (int j=0; j < splitedge[i].length; j++){
                 for (int k=0; k<V; k++){
                	 if(courses[k].equals(splitedge[i][j]) && courses[k]!=null){
                   	//	 System.out.println("add" + courses[k] + "to" + courses[i]);

                		 addEdge(adj,k,i);
                	 }
               } 
              }
         	 }
         }
   
         
        
     
        String output;
        
//run the search and output
        output = topologicalSort(adj, courses, V);
        writeline(output);

    }
}
