import java.util.ArrayDeque; 
import java.util.ArrayList; 
import java.util.Collections; 
import java.util.List; 
import java.util.Queue; 
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
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
//most of them are used in reading/writing txt file


public class Priority 
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
public static void writeline(String[] writerfile){
 try{
           FileWriter fw = new FileWriter("output.txt");
           for(int i = 0; i< writerfile.length;i++){
	   fw.write(writerfile[i]+ " ");            
 }
 fw.close(); 
 } catch (IOException e) {
           e.printStackTrace();
 }

}


//A node structure, contains the name and the rank of each person
private static class Node  
	{  
		private String name;  
		private int  rank;    
		private Node(String name, int rank)  
		{  
			this.name = name;  
			this.rank = rank;  
		}  
	}


//Insert a new node, simply add a new node to the list and resort the list
public static void Insert(List<Node> nodes, Node newnode){
        nodes.add(newnode); 
	heapsort(nodes);
}

//The list is sorted, so the Maximum is the 0th of the nodes
public static Node Maximum(List<Node> nodes){
	return nodes.get(0);
}

//Same like Maximum, but remove the Maximum after getting it 
public static Node Extract_Max(List<Node> nodes){
	Node returntemp = nodes.get(0);
	nodes.remove(0);
	return returntemp;
}


//Main
public static void main(String[] args){
//These are for reading txt file, splitting the text into names and ranks
	String s[] = readline(args[0]);
	String [][]splitting = new String[s.length][2];
	for(int i = 0; i< s.length; i++){
	 splitting[i] = s[i].split(" : "); 
	}
	int readrank[] = new int[s.length]; 
	for(int i = 0; i< s.length; i++){
	 readrank[i] = Integer.parseInt(splitting[i][1]); 
	}


//build a list of node, with the information got from text file
	List<Node> nodes = new ArrayList<Node>();

	Node temp = new Node("",0);

	for(int i = 0; i< s.length; i++){
	 temp = new Node(splitting[i][0],readrank[i]);  
	 Insert(nodes,temp);

}


//output the results
	Node tempout = new Node("temp",0);
	String[] output = new String[s.length];
	for(int i = 0; i< s.length; i++){
	 tempout = Extract_Max(nodes);
	 output[i] = tempout.name;
	 //System.out.println(tempout.name);

}
	writeline(output);
}


private static int size;
private static int min;

//heapsort methods here
 public static void heapsort(List<Node> nodes) {
      buildheap(nodes);
      for(int i=nodes.size() - 1; i>0; i--) {
         swap(nodes,0, i);
         size--;
         maxheap(nodes, 0);
      }
   }

   public static void buildheap(List<Node> nodes) {
      size = nodes.size() - 1;
      for(int i = size/2; i >= 0; i--){
         maxheap(nodes,i);
      }
   }

   public static void maxheap(List<Node> nodes, int index) { 
     
     int left = 2 * index;
     int right = 2 * index + 1;

      if(left <= size && nodes.get(left).rank > nodes.get(index).rank){
         min = index;
      } else if(left <= size) {
         min = left;
      }
      if(right <= size && nodes.get(right).rank < nodes.get(min).rank) {
      min = right;
      } 
      if(min!=index) {
         swap(nodes,index, min);
         maxheap(nodes, min);
      }
   }

// swap two nodes with their index 
    private static void swap(List<Node> nodes, int i, int j) { 
        Node temp; 
        temp = nodes.get(i); 
        nodes.set(i, nodes.get(j)); 
        nodes.set(j, temp); 
    } 





}



//And here is another methods to do this task(not based on heap but is a PriorityQueue)
//It is from 1913, and I have made a little bit change to it
/*
class PriorityQueue<Base>  
{  
	private class Node  
	{  
		private Base object;  
		private int  rank;  
		private Node left;  
		private Node right;  
		private Node(Base object, int rank,Node left,Node right)  
		{  
			this.object = object;  
			this.rank = rank;  
			this.left = left;  
			this.right = right;  
		}  
	}

	private Node head;

	public PriorityQueue()
	{
		head = new Node(null, 10000, null, null);
	}

	public Base dequeue()
	{

		if(isEmpty())
		{
			throw new IllegalStateException("Empty");
		}
		Node temp = head ;
		Node left = null;
		while(true){
			if(temp.left == null && temp.right != null)
			{
				Base tmp = temp.object; 
				left.left = temp.right;
				return tmp;
			}
			if(temp.left == null && temp.right == null)
			{
				Base tmp = temp.object; 
				left.left = null;
				return tmp;
			}
			left = temp;
			temp = temp.left;
		}
	}


public Base Maximum()
	{
				if(isEmpty())
		{
			throw new IllegalStateException("Empty");
		}
		Node temp = head ;
		Node left = null;
		while(true){
			if(temp.left == null && temp.right != null)
			{
				Base tmp = temp.object; 
				return tmp;
			}
			if(temp.left == null && temp.right == null)
			{
				Base tmp = temp.object; 
				return tmp;
			}
			left = temp;
			temp = temp.left;
		}
	}



	public void enqueue(Base object, int rank)
	{
		rank = - rank;
		Node temp = head;
		
		while(true)
		{
			if(rank > temp.rank)
			{
				if(temp.right == null)
				{
					temp.right = new Node(object,rank,null,null);
					break;
				}
				temp = temp.right;
			}
			if(rank <= temp.rank)
			{
				if(temp.left == null)
				{
					temp.left = new Node(object,rank,null,null);
					break;
				}
				temp = temp.left;
			}
		}
		return;
	}
	public boolean isEmpty()
	{
		return head.left == null;
	}



public static void main(String[] args)
{
PriorityQueue<String> queue = new PriorityQueue<String>();

System.out.println(queue.isEmpty());  


queue.enqueue("Lancelot",  5);
queue.enqueue("Fawlty",    7);
queue.enqueue("Elizabeth", 0);
queue.enqueue("Charles",   1);
queue.enqueue("Turing",    7);


System.out.println(queue.isEmpty());  
System.out.println(queue.Maximum());  
System.out.println(queue.dequeue());  
System.out.println(queue.Maximum());  
System.out.println(queue.dequeue());  
System.out.println(queue.dequeue());  
System.out.println(queue.dequeue()); 
System.out.println(queue.dequeue());  



System.out.println(queue.isEmpty());  
}
}


*/
